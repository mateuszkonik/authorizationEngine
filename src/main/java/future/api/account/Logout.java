package future.api.account;

import future.models.data.Config;
import future.models.data.Token;
import future.response.StatusResponse;
import future.util.TokenTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static future.Main.APP_ID;
import static future.response.ErrorMessage.*;
import static future.response.SuccessMessage.TOKEN_DESTROYED;

public class Logout {

	private static final Logger log = LoggerFactory.getLogger(Logout.class);

	public StatusResponse respond(UUID authorization) {
		try {
			Config config = Config.get(APP_ID);
			Token token = TokenTools.get(APP_ID, authorization);

			TokenTools.destroyToken(token);


			return new StatusResponse.OK(TOKEN_DESTROYED);

		} catch (Config.NotFound ex) {
			log.warn(NOT_RECOGNIZED_APP_ID.toString());
			return new StatusResponse.ERROR(NOT_RECOGNIZED_APP_ID);
		} catch (TokenTools.TokenNotFound ex) {
			log.warn(TOKEN_NOT_FOUND.toString());
			return new StatusResponse.ERROR(TOKEN_NOT_FOUND);
		} catch (TokenTools.MultipleTokensWithSingleCode ex) {
			log.warn(MULTIPLE_TOKENS_WITH_THE_SAME_CODE.toString());
			return new StatusResponse.ERROR(MULTIPLE_TOKENS_WITH_THE_SAME_CODE);
		}
	}
}
