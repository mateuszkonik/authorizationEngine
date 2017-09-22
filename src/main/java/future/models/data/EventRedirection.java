package future.models.data;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.UUID;

@Table(name="event_redirection")
public class EventRedirection {

	@PartitionKey
	private String code;

	private String event;

	private String url;

	@Column(name="user_id")
	private UUID userId;

	@Column(name="app_id")
	private UUID appId;

	//GETTERS AND SETTERS

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public UUID getAppId() {
		return appId;
	}

	public void setAppId(UUID appId) {
		this.appId = appId;
	}
}
