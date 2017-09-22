package future.models.data;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Table(name = "two_factor_auth_token")
public class TwoFactorAuthToken {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(TwoFactorAuthToken.class);

	@Column(name = "app_id")
	@PartitionKey(0)
	private UUID appId;

	@PartitionKey(1)
	@Column(name = "auth_token")
	private UUID token;

	@Column(name = "permission")
	private String permission;

	@Column(name = "auth_id")
	private UUID authId;

	@Column(name = "code")
	private String code;

	@Column(name = "used")
	private Boolean used;

	@Column(name = "user_id")
	private UUID userId;

	//GETTERS AND SETTERS

	public UUID getAppId() {
		return appId;
	}

	public void setAppId(UUID appId) {
		this.appId = appId;
	}

	public UUID getToken() {
		return token;
	}

	public void setToken(UUID token) {
		this.token = token;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public UUID getAuthId() {
		return authId;
	}

	public void setAuthId(UUID authId) {
		this.authId = authId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getUsed() {
		return used;
	}

	public void setUsed(Boolean used) {
		this.used = used;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}
}
