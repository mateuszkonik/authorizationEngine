package future.models.data;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import future.util.ResetPasswordTools;

import java.util.UUID;

@Table(name="reset_password_codes")
public class ResetPasswordCode {

	@PartitionKey
	private String code;

	@Column(name="app_id")
	private UUID appId;

	@Column(name="user_id")
	private UUID userId;

	private Boolean used;

	@Override
	public String toString() {
		return "ResetPasswordCode[ code:"+this.getCode()+" appId : "+this.getAppId()+" userId: "+this.getUserId()+" used : "+this.getUsed()+" ]";
	}

	public Template toTemplate() {
		return new Template(this);
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

	public static class Template{
		private final String code;
		private final String link;
		public Template(ResetPasswordCode code) {
			this.code = code.getCode();
			this.link = ResetPasswordTools.generateConfirmationUrl(code);
		}

		public String getCode() {
			return code;
		}

		public String getLink() {
			return link;
		}

	}
}
