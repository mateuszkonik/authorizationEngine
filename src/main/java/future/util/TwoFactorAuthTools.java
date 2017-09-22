package future.util;

import com.datastax.driver.mapping.Mapper;
import future.database.Database;
import future.models.data.TwoFactorAuth;
import future.models.data.TwoFactorAuthToken;
import future.util.exceptions.TwoFactorAuthException;
import future.util.two_factor_auth_methods.EmailTwoFactorAuth;
import future.util.two_factor_auth_methods.GoogleTwoFactorAuth;
import future.util.two_factor_auth_methods.TwoFactorAuthMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class TwoFactorAuthTools {

	private static Mapper<TwoFactorAuth> mapper = Database.getMapper().mapper(TwoFactorAuth.class);
	private static Mapper<TwoFactorAuthToken> mapperToken = Database.getMapper().mapper(TwoFactorAuthToken.class);
	public static final Logger log = LoggerFactory.getLogger(TwoFactorAuthTools.class);

	public static Set<String> availableMethods = Collections.unmodifiableSet(new HashSet<String>() {{
		add("none");
		add("email");
		add("google");
	}});


	public static TwoFactorAuth generate(UUID appId, String permission, UUID userId,String method) {
		TwoFactorAuth result = new TwoFactorAuth();

		result.setAppId(appId);
		result.setId(UUID.randomUUID());
		result.setPermission(permission);
		result.setUsed(false);
		result.setMethod(method);
		result.setUserId(userId);

		mapper.save(result);

		return result;
	}

	private static TwoFactorAuthMethod getTwoFactorClass(String method) throws Exception {
		TwoFactorAuthMethod result;

		switch(method) {

			case "email" :
			case "none" :
				result = new EmailTwoFactorAuth();
				break;
			case "google":
				result = new GoogleTwoFactorAuth();
				break;
			default:
				throw new Exception("2FA method not recognized");
		}

		return result;
	}

	public static String startProcess(TwoFactorAuth auth) throws Exception{
		TwoFactorAuthMethod process = getTwoFactorClass(auth.getMethod());

		process.setAppId(auth.getAppId());
		process.setAuthId(auth.getId());
		process.setUserId(auth.getUserId());

		return process.start();
	}


	public static TwoFactorAuthToken generateToken(UUID appId,UUID authId,String code) throws Exception{
		TwoFactorAuth auth = mapper.get(appId, authId);

		if (auth == null) throw new TwoFactorAuthException.AuthIdNotRecognized();
		if (auth.getUsed()) throw new TwoFactorAuthException.AuthIdAlreadyUsed();



		TwoFactorAuthMethod process = getTwoFactorClass(auth.getMethod());
		process.setAppId(auth.getAppId());
		process.setAuthId(auth.getId());
		process.setUserId(auth.getUserId());

		process.validateCode(code);

		auth.setUsed(true);
		mapper.save(auth);

		TwoFactorAuthToken result = new TwoFactorAuthToken();
		result.setAppId(auth.getAppId());
		result.setUsed(false);
		result.setPermission(auth.getPermission());
		result.setAuthId(auth.getId());
		result.setCode(auth.getCode());
		result.setToken(UUID.randomUUID());
		result.setUserId(auth.getUserId());

		mapperToken.save(result);

		return result;
	}


	public static void validateTwoFactorToken(UUID appId, UUID token, String requiredPermission, UUID userId) throws Exception{
		if (token == null) throw new TwoFactorAuthException.TwoFactorTokenInvalid();

		TwoFactorAuthToken twoFactorToken = mapperToken.get(appId,token);
		if (twoFactorToken == null) throw new TwoFactorAuthException.TwoFactorTokenInvalid();

		if (!twoFactorToken.getPermission().equals(requiredPermission)) {
			log.debug("Requested token permission is not sufficient");
			throw new TwoFactorAuthException.TwoFactorTokenInvalid();
		}


		if (twoFactorToken.getUsed()) {
			log.debug("This two factor token has already been used");
			throw new TwoFactorAuthException.TwoFactorTokenInvalid();

		}

		if (!userId.equals(twoFactorToken.getUserId())) {
			log.debug("Token's user not matching");
			throw new TwoFactorAuthException.TwoFactorTokenInvalid();
		}

		log.debug("TwoFactorToken successfully validated");

		twoFactorToken.setUsed(true);
		mapperToken.save(twoFactorToken);
	}
}

