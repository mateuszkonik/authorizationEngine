package future.api.confirmation;

import future.models.request.ConfirmRegistrationRequest;
import future.models.data.Config;
import future.models.data.ConfirmationCode;
import future.models.data.Users;
import future.response.ErrorMessage;
import future.response.StatusResponse;
import future.util.ConfirmationCodesTools;
import future.util.RegistrationStep;
import future.util.UsersTools;
import future.util.exceptions.UsersExceptions;
import org.slf4j.LoggerFactory;

import java.util.List;

import static future.Main.APP_ID;
import static future.response.ErrorMessage.*;
import static future.response.SuccessMessage.ACCOUNT_EMAIL_CONFIRMED;

public class ConfirmRegistration {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(ConfirmRegistration.class);

	public StatusResponse respond(String code, ConfirmRegistrationRequest body) {
		try {
			Config config = Config.get(APP_ID);
			ConfirmationCode confirmationCode = ConfirmationCodesTools.find(code);
			Users user = UsersTools.findById(APP_ID, confirmationCode.getUserId());
			List<ErrorMessage> errors = config.validateRegistrationData(user, RegistrationStep.EMAIL_VERIFICATION, body);

			if (errors.size() > 0) {
				return new StatusResponse.ERROR(errors);
			}

			ConfirmationCodesTools.setCodeAsUsed(confirmationCode);

			config.setRegistrationData(user, RegistrationStep.EMAIL_VERIFICATION, body);

			user.setEmailVerified(Boolean.TRUE);
			user.appendPermissions(config.getPermissionsAfterEmailConfirmation());
			UsersTools.save(user);

			return new StatusResponse.OK(ACCOUNT_EMAIL_CONFIRMED);
		} catch (Config.NotFound ex) {
			log.warn(NOT_RECOGNIZED_APP_ID.toString(),ex);
			return new StatusResponse.ERROR(NOT_RECOGNIZED_APP_ID);
		} catch (ConfirmationCodesTools.NotFoundCode ex) {
			log.warn(CONFIRMATION_CODE_NOT_FOUND.toString());
			return new StatusResponse.ERROR(CONFIRMATION_CODE_NOT_FOUND);
		} catch (ConfirmationCodesTools.CodeAlreadyUsed ex) {
			log.warn(CONFIRMATION_CODE_ALREADY_USED.toString());
			return new StatusResponse.ERROR(CONFIRMATION_CODE_ALREADY_USED);
		} catch (UsersExceptions.NotFound ex) {
			log.warn(USER_NOT_FOUND.toString());
			return new StatusResponse.ERROR(USER_NOT_FOUND);
		} catch(Exception ex) {
			log.error(INTERNAL_ERROR.toString(),ex);
			return new StatusResponse.ERROR(INTERNAL_ERROR);
		}
	}
}
