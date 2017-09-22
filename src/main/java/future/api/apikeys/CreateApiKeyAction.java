package future.api.apikeys;

import future.Main;
import future.mock.AuthEngineResponse;
import future.models.data.ApiKeys;
import future.models.request.ApiKeyCreationRequest;
import future.response.StatusResponse;
import future.util.ApiKeysTools;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

import static future.response.ErrorMessage.INTERNAL_ERROR;
import static future.response.SuccessMessage.RETURNING_REQUESTED_DATA;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class CreateApiKeyAction {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(CreateApiKeyAction.class);

	public StatusResponse respond(UUID authorization, ApiKeyCreationRequest body) {
		try {

			AuthEngineResponse.User loggedUser = Main.AUTH_CONFIG
					.check(authorization)
					.verifyPermissions("API_KEY_ADD")
					.getUserData();

			return new StatusResponse.OK(RETURNING_REQUESTED_DATA) {
				public ApiKeys userApiKeys =
						ApiKeysTools.createNewApiKeys(loggedUser.getId(), body.getPermissions());
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
