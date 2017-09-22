package future.api.reset_password;

import future.models.data.Config;
import future.models.data.ResetPasswordCode;
import future.models.data.TwoFactorAuth;
import future.models.data.Users;
import future.models.request.CheckCodeRequest;
import future.response.StatusResponse;
import future.util.ResetPasswordTools;
import future.util.TwoFactorAuthTools;
import future.util.UsersTools;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static future.Main.APP_ID;
import static future.response.ErrorMessage.*;
import static future.response.SuccessMessage.VALID_PASSWORD_RESET_CODE;
import static future.response.SuccessMessage.VALID_PASSWORD_RESET_CODE_REQUIRED_2FA;

public class CheckCode {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(CheckCode.class);
	private String permission;

	public CheckCode(String permission) {
		this.permission = permission;
	}

	public StatusResponse respond(CheckCodeRequest body) {
		try {
			Config config = Config.get(APP_ID);
			ResetPasswordCode resetPasswordCode = ResetPasswordTools.find(body.getCode());
			Users user = UsersTools.findById(APP_ID, resetPasswordCode.getUserId());

			// Jeśli użytkownik nie ma takich uprawnień
			if (!user.getPermissions().contains(permission)) {
				return new StatusResponse.ERROR(NO_PERMISSIONS);
			}

			// Jeśli zmiana hasła wymaga 2FA
			if (user.getTwoFactorRequiredPermissions().contains(permission)) {
				TwoFactorAuth auth = TwoFactorAuthTools.generate(APP_ID, permission,user.getUserId(), user.getTwoFactorMethod());

				return new StatusResponse.OK(VALID_PASSWORD_RESET_CODE_REQUIRED_2FA) {
					public UUID authId = auth.getId();
					public String method = auth.getMethod();
				};
			}
			return new StatusResponse.OK(VALID_PASSWORD_RESET_CODE);


		} catch (Config.NotFound ex) {
			log.warn(NOT_RECOGNIZED_APP_ID.toString());
			return new StatusResponse.ERROR(NOT_RECOGNIZED_APP_ID);
		} catch(Exception ex) {
			log.error(INTERNAL_ERROR.toString(), ex);
			return new StatusResponse.ERROR(INTERNAL_ERROR);
		}
	}
}
