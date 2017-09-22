package future.api.register;

import com.datastax.driver.mapping.Mapper;
import future.database.Database;
import future.models.data.*;
import future.models.request.RegisterRequest;
import future.models.response.LoginResponse;
import future.response.ErrorMessage;
import future.response.StatusResponse;
import future.util.*;
import org.iceo.mailsender.EmailAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static future.Main.APP_ID;
import static future.response.ErrorMessage.*;
import static future.response.SuccessMessage.CHECK_YOUR_EMAIL_TO_CONFIRM_ACCOUNT;

public class DoRegister {

	private static final Logger log = LoggerFactory.getLogger(DoRegister.class);

	public StatusResponse respond(RegisterRequest body) {
		try {
			Config config = Config.get(APP_ID);
			log.info("config " + config);

			if (body.getPassword() == null) {
				body.setPassword(Random.next());
			}

			List<ErrorMessage> errors = config.validateRegistrationData(null, RegistrationStep.REGISTRATION, body);

			if (body.getConfirmation_url() == null) {
				log.info("Redirection schema not provided, redirecting to confirmation directly");
				body.setConfirmation_url(ConfirmationCodesTools.generateConfirmationUrl(APP_ID, "{code}"));
			}

			if (errors.size() > 0) {
				return new StatusResponse.ERROR(errors);
			}

			/// proper registration process
			final Users user = Users.createNew(config);
			Mapper<Users> mapper = Database.getMapper().mapper(Users.class);

			//UsersTools.saveHandlings(user);
			config.setRegistrationData(user, RegistrationStep.REGISTRATION, body);
			mapper.save(user);

			ConfirmationCode confirmationCode = ConfirmationCodesTools.generate(APP_ID, user.getUserId());

			log.info("generated confirmation code : " + confirmationCode);

			Template template = ConfigTools.getTemplateForLanguage(config.getRegistrationEmailTemplates(), user.getLanguage());
			log.info("got required template : " + template.getSubject());

			String redirectUrl = body.getConfirmation_url().replace("{code}", confirmationCode.getCode());
			log.info("after click in mail link user shall be redirected to url " + redirectUrl);
			EventRedirection eventRedirection = RedirectionTools.registerNew("CONFIRMATION_LINK_CLICK", redirectUrl , user.getUserId(),user.getAppId() );
			String confirmationLink = RedirectionTools.buildRedirectUrl(eventRedirection) ;
			log.info("generated confirmation link " +  confirmationLink);
			template.pushVariable("confirmationLink", confirmationLink );
			template.pushVariable("password", body.getPassword());


			TemplateMailer.send(new EmailAddress(body.getEmail()), template);

			if (!config.getAutoLoginAfterRegistration()) {
				return new StatusResponse.OK(CHECK_YOUR_EMAIL_TO_CONFIRM_ACCOUNT);
			}

			final Token generatedToken = TokenTools.generateNew(user, config);

			return new StatusResponse.OK(CHECK_YOUR_EMAIL_TO_CONFIRM_ACCOUNT) {
				public LoginResponse token = new LoginResponse(generatedToken, user);
			};
		} catch (Config.NotFound ex) {
			log.warn(NOT_RECOGNIZED_APP_ID.toString(),ex);
			return new StatusResponse.ERROR(NOT_RECOGNIZED_APP_ID);
		} catch (Template.NotFoundTemplateForLanguage ex) {
			log.error(CANNOT_GET_REQUIRED_TEMPLATE.toString(),ex);
			return new StatusResponse.ERROR(INTERNAL_ERROR);
		} catch(Exception ex) {
			log.error(INTERNAL_ERROR.toString(),ex);
			return new StatusResponse.ERROR(INTERNAL_ERROR);
		}

	}
}
