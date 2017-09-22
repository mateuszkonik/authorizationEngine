package future.models.request;

import future.models.RegistrationData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

@ApiModel(value = "Register")
public class RegisterRequest extends RegistrationData {

	@ApiParam(hidden = true)
	private String confirmation_url;

	public String getConfirmation_url() {
		return confirmation_url;
	}

	public void setConfirmation_url(String confirmation_url) {
		this.confirmation_url = confirmation_url;
	}
}
