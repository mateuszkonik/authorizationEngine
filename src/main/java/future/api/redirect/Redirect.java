package future.api.redirect;


import future.response.StatusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class Redirect {

	private DoRedirect doRedirect = new DoRedirect();

	@RequestMapping(value = "/redirect/{code}", method = GET)
	public StatusResponse redirect(
			@PathVariable String code,
			HttpServletResponse response) {
		return doRedirect.respond(code, response);
	}
}
