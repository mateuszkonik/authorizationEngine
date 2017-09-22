package future.api.confirmation;

import future.models.data.Config;
import future.models.data.ConfirmationCode;
import future.response.StatusResponse;
import future.util.ConfirmationCodesTools;
import org.slf4j.LoggerFactory;

import static future.Main.APP_ID;
import static future.response.ErrorMessage.*;

public class Check {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(Check.class);

	public StatusResponse respond(String code) {
		try {
			Config config = Config.get(APP_ID);

			ConfirmationCode confirmationCode = ConfirmationCodesTools.find(code);

			return new StatusResponse.OK();
		} catch (Config.NotFound ex) {
			log.warn(NOT_RECOGNIZED_APP_ID.toString(),ex);
			return new StatusResponse.ERROR(NOT_RECOGNIZED_APP_ID);
		} catch (ConfirmationCodesTools.NotFoundCode ex) {
			log.warn(CONFIRMATION_CODE_NOT_FOUND.toString());
			return new StatusResponse.ERROR(CONFIRMATION_CODE_NOT_FOUND);
		} catch (ConfirmationCodesTools.CodeAlreadyUsed ex) {
			log.warn(CONFIRMATION_CODE_ALREADY_USED.toString());
			return new StatusResponse.ERROR(CONFIRMATION_CODE_ALREADY_USED);
		} catch(Exception ex) {
			log.error(INTERNAL_ERROR.toString(),ex);
			return new StatusResponse.ERROR(INTERNAL_ERROR);
		}
	}
}
