package future.util;

import com.datastax.driver.mapping.Mapper;
import future.Main;
import future.database.Database;
import future.models.data.ResetPasswordCode;

import java.util.UUID;

public class ResetPasswordTools {

	public static ResetPasswordCode generate(UUID appId, UUID userId) {
		String code = Random.next(30);

		Mapper<ResetPasswordCode> mapper = Database.getMapper().mapper(ResetPasswordCode.class);

		ResetPasswordCode resetPasswordCode = new ResetPasswordCode();

		resetPasswordCode.setCode(code);
		resetPasswordCode.setAppId(appId);
		resetPasswordCode.setUsed(Boolean.FALSE);
		resetPasswordCode.setUserId(userId);

		mapper.save(resetPasswordCode);

		return resetPasswordCode;
	}


	public static ResetPasswordCode find(String code) throws NotFoundCode, CodeAlreadyUsed {
		Mapper<ResetPasswordCode> mapper = Database.getMapper().mapper(ResetPasswordCode.class);
		ResetPasswordCode result = mapper.get(code);

		if (result == null) {
			throw new NotFoundCode();
		}

		if (result.getUsed()) {
			throw new CodeAlreadyUsed();
		}

		return result;
	}

	public static class NotFoundCode extends Exception {}

	public static class CodeAlreadyUsed extends Exception {}

	public static void setCodeAsUsed(ResetPasswordCode code) {
		code.setUsed(Boolean.TRUE);
		Mapper<ResetPasswordCode> mapper = Database.getMapper().mapper(ResetPasswordCode.class);
		mapper.save(code);
	}

	public static String generateConfirmationUrl(ResetPasswordCode code) {
		return generateConfirmationUrl(code.getAppId(), code.getCode());
	}

	public static String generateConfirmationUrl(UUID appId,String code) {
		return Main.URL+"/"+appId+"/reset-password/"+code;
	}
}
