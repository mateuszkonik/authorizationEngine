package future.models.response;

import future.models.User;
import future.models.data.Token;
import future.models.data.Users;
import future.response.ErrorMessage;
import future.util.DateTools;
import io.swagger.annotations.ApiModelProperty;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LoginResponse {

	@ApiModelProperty(required = true, notes = "date the token expires")
	public String expirationDate;

	@ApiModelProperty(required = true, notes = "time in seconds until the token expires", position = 1)
	public Integer expirationInSeconds;

	@ApiModelProperty(required = true, notes = "user's email", position = 2)
	public Boolean valid = false;

	@ApiModelProperty(required = true, notes = "user's data", position = 3)
	public Object user;

	@ApiModelProperty(required = true, notes = "authentication token", position = 4)
	public UUID token;

	@ApiModelProperty(required = true, notes = "list of errors", position = 5)
	public List<ErrorMessage> errors = new ArrayList<>();

	public LoginResponse() {}

	public LoginResponse(ErrorMessage error) {
		this.errors.add(error);
	}

	public LoginResponse(Token token, Users user) {
		this.expirationDate = token.getExpirationDate().toString(DateTools.STANDARD_FORMAT);
		this.valid = token.getExpirationDate().isAfter(new DateTime());
		this.user = new User(user);
		this.expirationInSeconds = token.getTtl();
		this.token = token.getToken();
	}
}
