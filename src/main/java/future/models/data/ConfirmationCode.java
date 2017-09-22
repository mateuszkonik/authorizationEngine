package future.models.data;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import future.util.ConfirmationCodesTools;

import java.util.UUID;

@Table(name="confirmation_codes")
public class ConfirmationCode {

	@PartitionKey
	private String code;

	@Column(name="app_id")
	private UUID appId;


	@Column(name="user_id")
	private UUID userId;

	private Boolean used;

	@Override
	public String toString() {
		return "ConfirmationCode[ code:"+this.getCode()+" appId : "+this.getAppId()+" userId: "+this.getUserId()+" used : "+this.getUsed()+" ]";
	}

	public Template toTemplate() {
		return new Template(this);
	}

	public static class Template {

		private final String code;
		private final String link;

		public Template(ConfirmationCode code) {
			this.code = code.getCode();
			this.link = ConfirmationCodesTools.generateConfirmationUrl(code);
		}

		public String getCode() {
			return code;
		}

		public String getLink() {
			return link;
		}
	}

	//GETTERS AND SETTERS

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public UUID getAppId() {
		return appId;
	}

	public void setAppId(UUID appId) {
		this.appId = appId;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public Boolean getUsed() {
		return used;
	}

	public void setUsed(Boolean used) {
		this.used = used;
	}
}
