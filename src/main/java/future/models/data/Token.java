package future.models.data;

import com.datastax.driver.mapping.annotations.*;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.UUID;

@Table(name = "tokens")
public class Token {

	@PartitionKey(0)
	@Column(name = "app_id")
	private UUID appId;

	@PartitionKey(1)
	@Column(name = "token_code")
	private UUID token;

	@ClusteringColumn
	@Column(name = "user_id")
	private UUID userId;

	@Column(name = "creation_date")
	private Date creationDate = new Date();

	@Column(name = "session_id")
	private UUID sessionId;

	@Computed("ttl(creation_date)")
	private Integer ttl;

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

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}


	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getTtl() {
		return ttl;
	}

	public void setTtl(Integer ttl) {
		this.ttl = ttl;
	}

	public DateTime getExpirationDate() {
		return (new DateTime() ).plusSeconds(this.getTtl());
	}

	public UUID getSessionId() {
		return sessionId;
	}

	public void setSessionId(UUID sessionId) {
		this.sessionId = sessionId;
	}

	@Override
	public String toString() {
		return "Token[ userId: "+this.getUserId()+" appId: "+this.getAppId()+" token: "+this.getToken()+" ]";
	}


}