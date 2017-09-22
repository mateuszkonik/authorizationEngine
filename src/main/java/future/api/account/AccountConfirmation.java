package future.api.account;

import future.Main;
import future.mock.AuthEngineResponse;
import future.models.RegistrationData;
import future.models.data.Config;
import future.models.data.Users;
import future.models.request.AccountConfirmationRequest;
import future.response.ErrorMessage;
import future.response.StatusResponse;
import future.util.RegistrationStep;
import future.util.UsersTools;
import future.util.exceptions.UsersExceptions;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

import static future.Main.APP_ID;
import static future.response.ErrorMessage.*;
import static future.response.SuccessMessage.ACCOUNT_CONFIRMED;

public class AccountConfirmation {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(AccountConfirmation.class);

	public StatusResponse respond(UUID authorization, AccountConfirmationRequest body) {
		try {
			AuthEngineResponse.User loggedUser = Main.AUTH_CONFIG
					.check(authorization)
					.verifyPermissions("EDIT_PROFILE")
					.getUserData();

			Users user = UsersTools.findById(APP_ID, loggedUser.getId());


			if (user.getAccountConfirmed()) {
				log.warn(ACCOUNT_ALREADY_CONFIRMED.toString());
				return new StatusResponse.ERROR(ACCOUNT_ALREADY_CONFIRMED);
			}

			Config config = Config.get(APP_ID);

			List<ErrorMessage> errors = config.validateRegistrationData(user, RegistrationStep.ACCOUNT_CONFIRMATION, body);

			if (errors.size() > 0) {
				return new StatusResponse.ERROR(errors);
			}

			config.setRegistrationData(user, RegistrationStep.ACCOUNT_CONFIRMATION, body);

			user.setAccountConfirmed(Boolean.TRUE);
			user.appendPermissions(config.getPermissionsAfterAccountConfirmation());
			UsersTools.save(user);

			return new StatusResponse.OK(ACCOUNT_CONFIRMED);

		} catch (Config.NotFound ex) {
			log.warn(NOT_RECOGNIZED_APP_ID.toString(),ex);
			return new StatusResponse.ERROR(NOT_RECOGNIZED_APP_ID);
		}catch (UsersExceptions.NotFound ex) {
			log.warn(USER_NOT_FOUND.toString());
			return new StatusResponse.ERROR(USER_NOT_FOUND);
		} catch(Exception ex) {
			log.error(INTERNAL_ERROR.toString(), ex);
			return new StatusResponse.ERROR(INTERNAL_ERROR);
		}
	}
}
