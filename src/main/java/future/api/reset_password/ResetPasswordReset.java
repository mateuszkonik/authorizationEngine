package future.api.reset_password;

import future.models.data.*;
import future.models.request.ResetRequest;
import future.response.StatusResponse;
import future.util.ResetPasswordTools;
import future.util.TwoFactorAuthTools;
import future.util.UsersTools;
import future.util.exceptions.TwoFactorAuthException;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static future.Main.APP_ID;
import static future.response.ErrorMessage.*;
import static future.response.SuccessMessage.VALID_PASSWORD_RESET_CODE;

public class ResetPasswordReset {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(ResetPasswordReset.class);
	private final String permission;

	public ResetPasswordReset(String permission) {
		this.permission = permission;
	}

	public StatusResponse respond(UUID authId, ResetRequest body) {
		try {
			Config config = Config.get(APP_ID);

			ResetPasswordCode resetPasswordCode = ResetPasswordTools.find(body.getCode());

			Users user = UsersTools.findById(APP_ID, resetPasswordCode.getUserId());

			// Jeśli użytkownik nie ma takich uprawnień
			if (!user.getPermissions().contains(permission)) {
				return new StatusResponse.ERROR(NO_PERMISSIONS);
			}

			// Walidacja hasła
			config.validatePassword(body.getPassword());


			// Jeśli zmiana hasła wymaga 2FA
			if (user.getTwoFactorRequiredPermissions().contains(permission)) {
				TwoFactorAuthToken token = null;
				try {
					token = TwoFactorAuthTools.generateToken(APP_ID, authId, body.getTwoFactorCode());
				} catch(Exception ex ) {
					return new StatusResponse.ERROR(FAILED_TO_GENERATE_2FA_TOKEN);
				}

				TwoFactorAuthTools.validateTwoFactorToken(APP_ID,token.getToken(),permission,user.getUserId());
			}

			ResetPasswordTools.setCodeAsUsed(resetPasswordCode);

			// Ustawienie nowego hasła
			user.setPassword(Passwords.hash(body.getPassword()));
			UsersTools.save(user);

			return new StatusResponse.OK(VALID_PASSWORD_RESET_CODE);
		} catch (Config.NotFound ex) {
			log.warn(NOT_RECOGNIZED_APP_ID.toString());
			return new StatusResponse.ERROR(NOT_RECOGNIZED_APP_ID);
		} catch (TwoFactorAuthException ex) {
			log.warn(TWO_FACTOR_TOKEN_ERROR.toString());
			return new StatusResponse.ERROR(TWO_FACTOR_TOKEN_ERROR);
		} catch (Config.PasswordIsTooShort ex) {
			log.warn(PASSWORD_IS_TOO_SHORT.toString());
			return new StatusResponse.ERROR(PASSWORD_IS_TOO_SHORT);
		} catch(Exception ex) {
			log.error(INTERNAL_ERROR.toString(),ex);
			return new StatusResponse.ERROR(INTERNAL_ERROR);
		}
	}
}
