package future.api.two_factor_authentication;

import future.models.data.TwoFactorAuthToken;
import future.models.request.TwoFactorAuthenticationRequest;
import future.response.StatusResponse;
import future.util.TwoFactorAuthTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static future.Main.APP_ID;
import static future.response.ErrorMessage.FAILED_TO_GENERATE_2FA_TOKEN;

public class GenerateToken {

	private static final Logger log = LoggerFactory.getLogger(GenerateToken.class);

	public StatusResponse respond(UUID auth_id, TwoFactorAuthenticationRequest body) {
		try {
			TwoFactorAuthToken auth = TwoFactorAuthTools.generateToken(APP_ID,auth_id,body.getCode());

			return new StatusResponse.OK() {
				public UUID twoFactorToken = auth.getToken();
			};
		} catch (Exception ex) {
			log.error("Failed to generate token ",ex);
			return new StatusResponse.ERROR(FAILED_TO_GENERATE_2FA_TOKEN);
		}
	}
}
