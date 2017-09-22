package future.models.data;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.UUID;

@Table(name="api_keys_by_users")
public class ApiKeysByUsers {

	@PartitionKey(0)
	@Column(name="user_id")
	private UUID userId;

	@PartitionKey(1)
	@Column(name="public_key")
	private UUID publicKey;

	public ApiKeysByUsers(UUID userId, UUID publicKey) {
		this.userId = userId;
		this.publicKey = publicKey;
	}

	@Override
	public String toString() {
		return "ApiKeysByUsers{" +
				"userId=" + userId +
				", publicKey=" + publicKey +
				'}';
	}

	//GETTERS AND SETTERS

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public UUID getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(UUID publicKey) {
		this.publicKey = publicKey;
	}
}
