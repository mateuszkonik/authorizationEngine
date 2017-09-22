package future.util;

import com.datastax.driver.mapping.Mapper;
import future.database.Database;
import future.models.data.Users;
import future.models.data.UsersByEmail;
import future.models.data.UsersByLogin;
import future.util.exceptions.UsersExceptions;

import java.util.UUID;

public class UsersTools {

	private static final Mapper<UsersByEmail> mapperByEmail = Database.getMapper().mapper(UsersByEmail.class);
	private static final Mapper<UsersByLogin> mapperByLogin = Database.getMapper().mapper(UsersByLogin.class);
	private static final Mapper<Users> mapperUsers  = Database.getMapper().mapper(Users.class);

	public static Users findById(UUID appId, UUID userId) throws UsersExceptions.NotFound{
		Users result = mapperUsers.get(appId,userId);
		if (result == null) throw new UsersExceptions.NotFound();
		return result;
	}


	public static Users findByEmail(UUID appId,String email) throws UsersExceptions.NotFound{
		UsersByEmail result = mapperByEmail.get(appId,email);
		if (result == null) throw new UsersExceptions.NotFound("Not found user by email : " + email);
		return mapperUsers.get(appId,result.getUserId());
	}

	public static Users findByLogin(UUID appId,String login) throws UsersExceptions.NotFound{
		if (login == null) throw new UsersExceptions.NotFound("Login not provided");
		UsersByLogin result = mapperByLogin.get(appId,login);
		if (result == null) throw new UsersExceptions.NotFound("Not found user by login : " + login);
		return mapperUsers.get(appId,result.getUserId());
	}

	public static void saveHandlings(Users user) {

		UsersByEmail userByEmail = new UsersByEmail();
		userByEmail.setAppId(user.getAppId());
		userByEmail.setEmail(user.getEmail());
		userByEmail.setUserId(user.getUserId());
		mapperByEmail.save(userByEmail);

		if (user.getLogin() != null) {
			UsersByLogin userByLogin = new UsersByLogin();
			userByLogin.setAppId(user.getAppId());
			userByLogin.setLogin(user.getLogin());
			userByLogin.setUserId(user.getUserId());
			mapperByLogin.save(userByLogin);
		}
	}

	public static void saveEmailHandlings(Users user) {
		UsersByEmail userByEmail = new UsersByEmail();
		userByEmail.setAppId(user.getAppId());
		userByEmail.setEmail(user.getEmail());
		userByEmail.setUserId(user.getUserId());
		mapperByEmail.save(userByEmail);
	}

	public static void deleteEmailHandlings(Users user) {
		UsersByEmail userByEmail = new UsersByEmail();
		userByEmail.setAppId(user.getAppId());
		userByEmail.setEmail(user.getEmail());
		userByEmail.setUserId(user.getUserId());
		mapperByEmail.delete(userByEmail);
	}

	public static void saveLoginHandlings(Users user) {
		UsersByLogin userByLogin = new UsersByLogin();
		userByLogin.setAppId(user.getAppId());
		userByLogin.setLogin(user.getLogin());
		userByLogin.setUserId(user.getUserId());
		mapperByLogin.save(userByLogin);
	}

	public static void deleteLoginHandlings(Users user) {
		UsersByLogin userByLogin = new UsersByLogin();
		userByLogin.setAppId(user.getAppId());
		userByLogin.setLogin(user.getLogin());
		userByLogin.setUserId(user.getUserId());
		mapperByLogin.delete(userByLogin);
	}

	public static void save(Users user) {
		mapperUsers.save(user);
	}
}
