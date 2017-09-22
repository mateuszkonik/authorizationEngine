package future.models.data;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.UUID;

@Table(name = "users_by_login")
public class UsersByLogin {

	@PartitionKey(0)
	@Column(name = "app_id")
	private UUID appId;

	@PartitionKey(1)
	private String login;

	@Column(name = "user_id")
	private UUID userId;

	//GETTERS AND SETTERS

	public UUID getAppId() {
		return appId;
	}

	public void setAppId(UUID appId) {
		this.appId = appId;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}
}
