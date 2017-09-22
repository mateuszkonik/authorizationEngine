package future.api.redirect;

import future.models.data.Config;
import future.models.data.EventRedirection;
import future.response.StatusResponse;
import future.util.RedirectionTools;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static future.Main.APP_ID;
import static future.response.ErrorMessage.*;
import static future.response.SuccessMessage.REDIRECTING;

public class DoRedirect {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(DoRedirect.class);

	public StatusResponse respond(String code, HttpServletResponse response) {
		try {
			Config config = Config.get(APP_ID);
			EventRedirection eventRedirection = RedirectionTools.find(code);

			//TODO: Event logging - async preferably

			response.sendRedirect(eventRedirection.getUrl());

			return new StatusResponse.OK(REDIRECTING);
		} catch (Config.NotFound ex) {
			log.error(NOT_RECOGNIZED_APP_ID.toString(),ex);
			return new StatusResponse.ERROR(NOT_RECOGNIZED_APP_ID);
		} catch (RedirectionTools.NotFoundCodeException ex) {
			log.error(REDIRECTION_CODE_NOT_FOUND.toString(),ex);
			return new StatusResponse.ERROR(REDIRECTION_CODE_NOT_FOUND);
		} catch (IOException ex) {
			log.error(REDIRECTION_FAILED.toString(),ex);
			return new StatusResponse.ERROR(REDIRECTION_FAILED);
		} catch(Exception ex) {
			log.error(INTERNAL_ERROR.toString(),ex);
			return new StatusResponse.ERROR(INTERNAL_ERROR);
		}
	}
}
