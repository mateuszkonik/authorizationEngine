package future.util.two_factor_auth_methods;

import java.util.UUID;

public abstract class TwoFactorAuthMethod {

	protected UUID appId;
	protected UUID authId;
	protected UUID userId;

	/// generates code
	public abstract String start() throws Exception;

	public abstract void validateCode(String code) throws Exception;

	//GETTERS AND SETTERS

	public void setAppId(UUID appId) {
		this.appId = appId;
	}

	public void setAuthId(UUID authId) {
		this.authId = authId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}
}
