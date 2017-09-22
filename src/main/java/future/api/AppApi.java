package future.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "api", description = "api")
public class AppApi {

	@ApiOperation(value = "", hidden = true)
	@RequestMapping(value = "/")
	private String home() {
		return "redirect:/swagger-ui.html";
	}
}
