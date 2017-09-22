package future.models.data;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Table(name="api_keys")
public class ApiKeys {

	@PartitionKey
	@Column(name="public_key")
	private UUID publicKey;

	@Column(name="private_key")
	private UUID privateKey;

	@Column(name="user_id")
	private UUID userId;

	private Date created = new Date();

	@JsonIgnore
	private Boolean deleted = false;

	private Set<String> permissions;

	public ApiKeys() {}

	public ApiKeys(UUID userId, Set<String> permissions) {
		this.publicKey = UUID.randomUUID();
		this.privateKey = UUID.randomUUID();
		this.userId = userId;
		this.deleted = false;
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return "ApiKeys{" +
				"publicKey=" + publicKey +
				", privateKey=" + privateKey +
				", userId=" + userId +
				", created=" + created +
				", deleted=" + deleted +
				", permissions=" + permissions +
				'}';
	}

	//GETTERS AND SETTERS

	public UUID getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(UUID publicKey) {
		this.publicKey = publicKey;
	}

	public UUID getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(UUID privateKey) {
		this.privateKey = privateKey;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Set<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}
}
