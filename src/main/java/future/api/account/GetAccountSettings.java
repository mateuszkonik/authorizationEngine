package future.api.account;


import future.Main;
import future.mock.AuthEngineResponse;
import future.models.data.Users;
import future.models.response.AccountSettingsResponse;
import future.response.StatusResponse;
import future.util.UsersTools;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static future.Main.APP_ID;
import static future.response.ErrorMessage.INTERNAL_ERROR;
import static future.response.SuccessMessage.RETURNING_REQUESTED_DATA;

public class GetAccountSettings {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(GetAccountSettings.class);

	public StatusResponse respond(UUID authorization) {
		try {
			AuthEngineResponse.User loggedUser = Main.AUTH_CONFIG
					.check(authorization)
					.verifyPermissions("EDIT_PROFILE")
					.getUserData();

			Users user = UsersTools.findById(APP_ID, loggedUser.getId());

			return new StatusResponse.OK(RETURNING_REQUESTED_DATA) {
				public AccountSettingsResponse response = new AccountSettingsResponse(user, false);
			};

		} /*todo: catch (AuthEngineException ex) {
			log.warn(UNAUTHORIZED.toString());
			return new StatusResponse.ERROR(UNAUTHORIZED);
		}*/ catch(Exception ex) {
			log.error(INTERNAL_ERROR.toString(), ex);
			return new StatusResponse.ERROR(INTERNAL_ERROR);
		}
	}
}
