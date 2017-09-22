package future.models.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Start")
public class StartRequest {

	@ApiModelProperty(required = true, notes = "user's email")
	private String email;

	@ApiModelProperty(required = true, notes = "user's login", position = 1)
	private String login;

	@ApiModelProperty(required = true, notes = "confirmation url", position = 2)
	private String confirmation_url;

	//GETTERS AND SETTERS

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getConfirmation_url() {
		return confirmation_url;
	}

	public void setConfirmation_url(String confirmation_url) {
		this.confirmation_url = confirmation_url;
	}
}
