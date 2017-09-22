package future.models.data;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Table(name = "two_factor_auth")
public class TwoFactorAuth {

	private static final Logger log = LoggerFactory.getLogger(TwoFactorAuth.class);

	@Column(name = "app_id")
	@PartitionKey(0)
	private UUID appId;

	@PartitionKey(1)
	@Column(name = "id")
	private UUID id;

	@Column(name = "permission")
	private String permission;

	@Column(name = "used")
	private Boolean used = false;

	@Column(name = "user_id")
	private UUID userId;

	@Column(name = "method")
	private String method;

	@Column(name = "code")
	private String code;

	//GETTERS AND SETTERS

	public UUID getAppId() {
		return appId;
	}

	public void setAppId(UUID appId) {
		this.appId = appId;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
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

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
