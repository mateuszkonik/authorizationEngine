package future.models.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Set;

@ApiModel("Api Key")
public class ApiKeyCreationRequest {

	@ApiModelProperty(notes = "list of permitted actions")
	private Set<String> permissions;

	//GETTERS AND SETTERS

	public Set<String> getPermissions() {
		return permissions;
	}
}
