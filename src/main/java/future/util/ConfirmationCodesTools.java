package future.util;

import com.datastax.driver.mapping.Mapper;
import future.Main;
import future.database.Database;
import future.models.data.ConfirmationCode;

import java.util.UUID;

import static future.Main.APP_ID;

public class ConfirmationCodesTools {

	public static ConfirmationCode generate(UUID appId, UUID userId) {
		String code = Random.next();

		Mapper<ConfirmationCode> mapper = Database.getMapper().mapper(ConfirmationCode.class);

		ConfirmationCode confirmationCode = new ConfirmationCode();

		confirmationCode.setCode(code);
		confirmationCode.setAppId(appId);
		confirmationCode.setUsed(Boolean.FALSE);
		confirmationCode.setUserId(userId);

		mapper.save(confirmationCode);

		return confirmationCode;
	}


	public static ConfirmationCode find(String code) throws NotFoundCode, CodeAlreadyUsed {
		Mapper<ConfirmationCode> mapper = Database.getMapper().mapper(ConfirmationCode.class);
		ConfirmationCode result = mapper.get(code);

		if (result == null) {
			throw new NotFoundCode();
		}

		if (result.getUsed()) {
			throw new CodeAlreadyUsed();
		}

		return result;
	}

	public static class NotFoundCode extends Exception{}

	public static class CodeAlreadyUsed extends Exception{}

	public static void setCodeAsUsed(ConfirmationCode code) {
		code.setUsed(Boolean.TRUE);
		Mapper<ConfirmationCode> mapper = Database.getMapper().mapper(ConfirmationCode.class);
		mapper.save(code);
	}

	public static String generateConfirmationUrl(ConfirmationCode code) {
		return generateConfirmationUrl(code.getAppId(), code.getCode());
	}

	public static String generateConfirmationUrl(UUID appId,String code) {
		return Main.URL+"/"+appId+"/confirmation/"+code;
	}
}


