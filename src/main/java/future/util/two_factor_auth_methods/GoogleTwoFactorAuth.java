package future.util.two_factor_auth_methods;

import future.models.data.Users;
import future.util.UsersTools;
import future.util.exceptions.TwoFactorAuthException;
import future.util.totp.TOTPTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoogleTwoFactorAuth extends TwoFactorAuthMethod {

	private static final Logger log = LoggerFactory.getLogger(GoogleTwoFactorAuth.class);

	@Override
	public String start() throws Exception {
		log.debug("No need to do anything");

		return "";
	}

	@Override
	public void validateCode(String code) throws Exception {
		Users user  = UsersTools.findById(this.appId,this.userId);

		String currentCode = TOTPTools.getTOTPCode(user.getGaSecret());

		log.debug("validating code with google 2fa provided : {} expected : {} ",code , currentCode );

		if (!code.equals(currentCode)) {
			throw new TwoFactorAuthException.CodeVerificationFailed();
		}
	}
}
