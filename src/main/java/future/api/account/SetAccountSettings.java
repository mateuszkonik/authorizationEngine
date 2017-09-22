package future.api.account;

import future.Main;
import future.mock.AuthEngineResponse;
import future.models.RegistrationData;
import future.models.data.Config;
import future.models.data.Users;
import future.models.request.AccountSettingsRequest;
import future.models.response.AccountSettingsResponse;
import future.response.ErrorMessage;
import future.response.StatusResponse;
import future.util.RegistrationStep;
import future.util.UsersTools;
import future.util.exceptions.UsersExceptions;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

import static future.Main.APP_ID;
import static future.response.ErrorMessage.NOT_RECOGNIZED_APP_ID;
import static future.response.ErrorMessage.USER_NOT_FOUND;
import static future.response.SuccessMessage.ACCOUNT_SETTINGS_CHANGED;

public class SetAccountSettings {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(SetAccountSettings.class);

	public StatusResponse respond(UUID authorization, UUID twoFactorAuthToken, AccountSettingsRequest body) {
		try {
			// Pobranie użytkownika i sprawdzenie, czy ma w ogóle uprawnienia do edycji konta
			AuthEngineResponse.User loggedUser = Main.AUTH_CONFIG
					.check(authorization)
					.verifyPermissions("EDIT_PROFILE")
					.getUserData();

			Users user = UsersTools.findById(APP_ID, loggedUser.getId());

			Config config = Config.get(APP_ID);

			// Walidacja pól
			RegistrationData registrationData = (RegistrationData)body;
			List<ErrorMessage> errors = config.validateRegistrationData(user, RegistrationStep.ACCOUNT_SETTINGS, registrationData);

			if (errors.size()>0)
				return new StatusResponse.ERROR(errors);

			Boolean require2fa = config.checkFieldsRequire2fa(RegistrationStep.ACCOUNT_SETTINGS, registrationData);


			if (require2fa) {
				// Ponowne sprawdzenie użytkownika, czy ma w uprawnienia, tym razem do edycji pól zabezpieczonych 2fa
				loggedUser = Main.AUTH_CONFIG
						.check(authorization)
						.verifyPermissions("EDIT_PROFILE_2FA_SECURED")
						.getUserData();
			}

			config.setRegistrationData(user, RegistrationStep.ACCOUNT_SETTINGS, registrationData);

			UsersTools.save(user);

			Boolean publishGaSecret = registrationData.getTwofa_method() != null && registrationData.getTwofa_method().equals("google");

			return new StatusResponse.OK(ACCOUNT_SETTINGS_CHANGED) {
				public AccountSettingsResponse response = new AccountSettingsResponse(user, publishGaSecret);
			};
		} catch (Config.NotFound ex) {
			log.warn(NOT_RECOGNIZED_APP_ID.toString(),ex);
			return new StatusResponse.ERROR(NOT_RECOGNIZED_APP_ID);
		}catch (UsersExceptions.NotFound ex) {
			log.warn(USER_NOT_FOUND.toString());
			return new StatusResponse.ERROR(USER_NOT_FOUND);
		} /*todo: catch (AuthEngineException ex) {
			log.warn(UNAUTHORIZED.toString());
			return new StatusResponse.ERROR(ErrorMessage.UNAUTHORIZED);
		}*/ catch(Exception ex) {
			log.error(ErrorMessage.INTERNAL_ERROR.toString(),ex);
			return new StatusResponse.ERROR(ErrorMessage.INTERNAL_ERROR);
		}
	}
}
