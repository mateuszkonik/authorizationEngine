package future.api.reset_password;

import future.models.data.*;
import future.models.request.StartRequest;
import future.response.StatusResponse;
import future.util.*;
import org.iceo.mailsender.EmailAddress;
import org.slf4j.LoggerFactory;

import static future.Main.APP_ID;
import static future.response.ErrorMessage.*;
import static future.response.SuccessMessage.CHECK_YOUR_EMAIL_TO_RESET_PASSWORD;

public class ResetPasswordStart {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(ResetPasswordStart.class);
	private String permission;

	public ResetPasswordStart(String permission) {
		this.permission = permission;
	}

	public StatusResponse respond(StartRequest body) {
		try {
			Config config = Config.get(APP_ID);
			Users user = TokenTools.getUser(config, body.getEmail(), body.getLogin());

			if (user != null) {
				// Jeśli użytkownik nie ma takich uprawnień
				if (!user.getPermissions().contains(permission)) {
					return new StatusResponse.ERROR(NO_PERMISSIONS);
				}

				ResetPasswordCode resetPasswordCode = ResetPasswordTools.generate(APP_ID, user.getUserId());

				if(body.getConfirmation_url() == null) {
					log.info("Redirection schema not provided, redirecting to confirmation directly");
					body.setConfirmation_url(ResetPasswordTools.generateConfirmationUrl(APP_ID, "{code}"));
				}

				String redirectUrl = body.getConfirmation_url().replace("{code}", resetPasswordCode.getCode());
				EventRedirection eventRedirection = RedirectionTools.registerNew("RESET_PASSWORD_LINK_CLICK", redirectUrl , user.getUserId(),user.getAppId() );
				String confirmationLink = RedirectionTools.buildRedirectUrl(eventRedirection);

				Template template = ConfigTools.getTemplateForLanguage(config.getResetPasswordEmailTemplates(), user.getLanguage());
				template.pushVariable("resetPasswordLink", confirmationLink );

				TemplateMailer.send(new EmailAddress(body.getEmail()), template);
			}

			return new StatusResponse.OK(CHECK_YOUR_EMAIL_TO_RESET_PASSWORD);
		} catch (Config.NotFound ex) {
			log.warn(NOT_RECOGNIZED_APP_ID.toString());
			return new StatusResponse.ERROR(NOT_RECOGNIZED_APP_ID);
		} catch(Exception ex) {
			log.error(INTERNAL_ERROR.toString(),ex);
			return new StatusResponse.ERROR(INTERNAL_ERROR);
		}
	}
}
