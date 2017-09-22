package future.models.data;

import com.datastax.driver.mapping.annotations.*;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.UUID;

@Table(name = "active_sessions")
public class ActiveSession {

	@PartitionKey(0)
	@Column(name = "app_id")
	private UUID appId;

	@PartitionKey(1)
	@Column(name = "user_id")
	private UUID userId;

	@ClusteringColumn
	@Column(name = "session_id")
	private UUID sessionId;

	@Column(name = "token_code")
	private UUID token;

	@Column(name = "creation_date")
	private Date creationDate = new Date();

	@Column(name = "ip")
	private String ip;

	@Column(name = "user_agent")
	private String userAgent;

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
		return (new DateTime()).plusSeconds(this.getTtl());
	}

	public UUID getSessionId() {
		return sessionId;
	}

	public void setSessionId(UUID sessionId) {
		this.sessionId = sessionId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}


	@Override
	public String toString() {
		return "ActiveSession{" +
				"appId=" + appId +
				", userId=" + userId +
				", sessionId=" + sessionId +
				", token=" + token +
				", creationDate=" + creationDate +
				", ip='" + ip + '\'' +
				", userAgent='" + userAgent + '\'' +
				", ttl=" + ttl +
				'}';
	}
}
