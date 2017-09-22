package future.util.two_factor_auth_methods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailTwoFactorAuth extends TwoFactorAuthMethod {

	private static final Logger log = LoggerFactory.getLogger(EmailTwoFactorAuth.class);
	private static final String code = "super_code";

	@Override
	public String start() throws Exception {
		log.debug("sending email with code");
		return code;
	}

	@Override
	public void validateCode(String code) throws Exception {

		if (!EmailTwoFactorAuth.code.equals(code)) {
			throw new Exception("Invalid code");
		}

		log.debug("validating code with email 2fa {}",code);
	}
}
