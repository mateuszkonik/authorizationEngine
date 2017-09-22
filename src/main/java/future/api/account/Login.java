package future.api.account;

import future.api.redirect.Redirect;
import future.models.data.Config;
import future.models.data.TwoFactorAuth;
import future.models.data.TwoFactorAuthToken;
import future.models.data.Users;
import future.models.request.LoginRequest;
import future.models.response.LoginResponse;
import future.response.StatusResponse;
import future.util.TokenTools;
import future.util.TwoFactorAuthTools;
import future.util.exceptions.UsersExceptions;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

import static future.Main.APP_ID;
import static future.response.ErrorMessage.*;
import static future.response.SuccessMessage.SUCCESSFULLY_CREATED_TOKEN;
import static future.response.SuccessMessage.VALID_CREDENTIALS_REQUIRED_2FA;

public class Login {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(Login.class);
	private final String permission = "LOGIN";

	public StatusResponse respond(UUID authId,
	                              String userAgent,
	                              String remoteIP,
	                              LoginRequest body,
	                              HttpServletRequest request) {
		try {
			Config config = Config.get(APP_ID);
			Users user = TokenTools.checkCredentials(config, body.email, body.login, body.password);

			if (user.getTwoFactorRequiredPermissions().contains(permission)) {

				if (authId != null) {
					TwoFactorAuthToken token = null;
					try {
						token = TwoFactorAuthTools.generateToken(APP_ID, authId, body.getTwoFactorCode());
					} catch (Exception ex) {
						return new StatusResponse.ERROR(FAILED_TO_GENERATE_2FA_TOKEN);
					}

					TwoFactorAuthTools.validateTwoFactorToken(APP_ID, token.getToken(), permission, user.getUserId());

				} else {

					TwoFactorAuth auth = TwoFactorAuthTools.generate(APP_ID, permission, user.getUserId(), user.getTwoFactorMethod());

					return new StatusResponse.OK(VALID_CREDENTIALS_REQUIRED_2FA) {
						public UUID authId = auth.getId();
						public String method = auth.getMethod();
					};
				}
			}

			String ip = request.getRemoteAddr();

			if (remoteIP != null) {
				ip = remoteIP;
			}

			final TokenTools.GeneratedToken generatedToken = TokenTools.generateTokenByLoginData(config, body.email, body.login, body.password);
			TokenTools.createSessionForToken(config, generatedToken, ip, userAgent);

			return new StatusResponse.OK(SUCCESSFULLY_CREATED_TOKEN) {
				public LoginResponse token = new LoginResponse(generatedToken.getToken(),generatedToken.getUser());
			};
		} catch (Config.NotFound ex) {
			log.warn(NOT_RECOGNIZED_APP_ID.toString());
			return new StatusResponse.ERROR(NOT_RECOGNIZED_APP_ID);
		} catch (UsersExceptions.NotFound ex) {
			log.warn(USER_NOT_FOUND.toString());
			return new StatusResponse.ERROR(USER_NOT_FOUND);
		} catch (UsersExceptions.InvalidPassword ex) {
			log.warn("Invalid user password");
			return new StatusResponse.ERROR(INVALID_USER_PASSWORD);
		} catch(Exception ex) {
			log.error(INTERNAL_ERROR.toString(), ex);
			return new StatusResponse.ERROR(INTERNAL_ERROR);
		}
	}
}
