package future.models.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Check Code")
public class CheckCodeRequest {

	@ApiModelProperty(required = true, notes = "code")
	private String code;

	//GETTERS AND SETTERS

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
