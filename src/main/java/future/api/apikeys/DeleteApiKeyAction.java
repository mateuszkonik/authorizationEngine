package future.api.apikeys;

import future.Main;
import future.mock.AuthEngineResponse;
import future.response.StatusResponse;
import future.util.ApiKeysTools;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static future.response.ErrorMessage.INTERNAL_ERROR;
import static future.response.SuccessMessage.RETURNING_REQUESTED_DATA;

public class DeleteApiKeyAction {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(DeleteApiKeyAction.class);

	public StatusResponse respond(UUID public_key, UUID authorization) {
		try {

			AuthEngineResponse.User loggedUser = Main.AUTH_CONFIG
					.check(authorization)
					.verifyPermissions("API_KEY_DELETE")
					.getUserData();

			return new StatusResponse.OK(RETURNING_REQUESTED_DATA) {
				public boolean deleted = ApiKeysTools.deleteApiKeys(loggedUser.getId(), public_key);
			};

		} /*todo: catch (AuthEngineException ex) {
			log.warn(UNAUTHORIZED.toString());
			return new StatusResponse.ERROR(StatusMessages.ERROR.UNAUTHORIZED);
		}*/ catch(Exception ex) {
			log.error(INTERNAL_ERROR.toString(),ex);
			return new StatusResponse.ERROR(INTERNAL_ERROR);
		}
	}
}
