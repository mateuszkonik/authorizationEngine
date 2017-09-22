package future.util;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;
import future.database.Database;
import future.models.data.*;
import future.util.exceptions.UsersExceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.mapping.Mapper.Option.ttl;

public class TokenTools {

	private static final Logger log = LoggerFactory.getLogger(TokenTools.class);
	private static Mapper<Token> mapper = Database.getMapper().mapper(Token.class);
	private static Mapper<ActiveSession> activeSessionMapper = Database.getMapper().mapper(ActiveSession.class);
	public static Token generateNew(Users user , Config config) {
		return generateNew(user.getAppId(),user.getUserId(),config);
	}


	public static Token generateNew(UUID appId, UUID userId, Config config) {


		Token token = new Token();
		token.setAppId(appId);
		token.setUserId(userId);
		token.setToken(UUID.randomUUID());

		token.setTtl(config.getTokenValidSeconds());

		return token;

	}


	public static ActiveSession createSessionForToken(Config config, GeneratedToken token, String ip, String userAgent) {


		ActiveSession activeSession = new ActiveSession();
		activeSession.setAppId(token.getUser().getAppId());
		activeSession.setUserId(token.getUser().getUserId());
		activeSession.setSessionId(UUID.randomUUID());
		activeSession.setToken(token.getToken().getToken());
		activeSession.setIp(ip);
		activeSession.setUserAgent(userAgent);

		saveActiveSession(activeSession,config.getTokenValidSeconds());

		activeSession.setTtl(config.getTokenValidSeconds());

		Token rawToken = token.getToken();
		rawToken.setSessionId(activeSession.getSessionId());

		saveToken(rawToken,config.getTokenValidSeconds());
		rawToken.setTtl(config.getTokenValidSeconds());

		return activeSession;
	}

	public static void saveToken(Token token, Integer ttl) {
		mapper.saveAsync(token, ttl(ttl));
	}

	public static void saveActiveSession(ActiveSession activeSession,Integer ttl) {
		activeSessionMapper.saveAsync(activeSession,ttl(ttl));
	}

	public static void destroyToken(Token token) {
		if (token.getSessionId() != null) {
			ActiveSession activeSession = activeSessionMapper.get(token.getAppId(), token.getUserId(), token.getSessionId());
			activeSessionMapper.delete(activeSession);
		}
		mapper.delete(token);
	}

	public static void updateTokenTtl(Token token, Config config) {
		saveToken(token, config.getTokenValidSeconds());

		if (token.getSessionId() != null) {
			ActiveSession activeSession = activeSessionMapper.get(token.getAppId(), token.getUserId(), token.getSessionId());
			saveActiveSession(activeSession, config.getTokenValidSeconds());
		}
	}

	public static Token get(UUID appId,UUID tokenCode ) throws TokenNotFound, MultipleTokensWithSingleCode{

		Statement st = QueryBuilder.select()
				.column("app_id").column("token_code").column("user_id").column("creation_date").column("session_id").ttl("creation_date")
				.from("tokens").where(eq("app_id",appId)).and(eq("token_code",tokenCode));

		ResultSet results = Database.getSession().execute(st);
		List<Row> resultsList = results.all();
		if (resultsList.isEmpty() ) throw new TokenNotFound();
		else if (resultsList.size() > 1) throw new MultipleTokensWithSingleCode();
		else{
			Row rawToken = resultsList.get(0);
			Token token = new Token();
			token.setAppId(rawToken.getUUID("app_id"));
			token.setCreationDate(rawToken.getTimestamp("creation_date"));
			token.setToken(rawToken.getUUID("token_code"));
			token.setTtl(rawToken.getInt("ttl(creation_date)"));
			token.setUserId(rawToken.getUUID("user_id"));
			token.setSessionId(rawToken.getUUID("session_id"));

			return token;
		}


//        TokenAccessors accessor = Database.getMapper().createAccessor(TokenAccessors.class);
//        List<Token> tokens = accessor.getFromApp(appId, tokenCode).all();
//        if (tokens.isEmpty() ) throw new TokenNotFound();
//        else if (tokens.size() > 1) throw new MultipleTokensWithSingleCode();
//        else return tokens.get(0);

	}


	public static class TokenException extends Exception{};
	public static class MultipleTokensWithSingleCode extends TokenException{}
	public static class TokenNotFound extends TokenException{};

	@Accessor
	public interface TokenAccessors{
		@Query("SELECT * FROM tokens WHERE app_id = :app_id AND token_code = :token_code ")
		public Result<Token> getFromApp(@Param("app_id") UUID appId, @Param("token_code") UUID token);
	}

	public static Users getUser(Config config,String email,String login) {
		Users user = null;

		if ((config.getLoginVia() == Config.LoginVia.EMAIL || config.getLoginVia() == Config.LoginVia.BOTH) && email != null) {
			log.info("find by email");
			try {
				user = UsersTools.findByEmail(config.getAppId(), email);
			} catch (UsersExceptions.NotFound ex) {}
		}

		log.info("Login supplied email : " + email);

		if ((config.getLoginVia() == Config.LoginVia.LOGIN || config.getLoginVia() == Config.LoginVia.BOTH) && user == null && login != null) {
			log.info("Find by login");
			try {
				user = UsersTools.findByLogin(config.getAppId(), login);
			} catch (UsersExceptions.NotFound ex) {}
		}

		return user;
	}


	public static Users checkCredentials(Config config,String email,String login,String password) throws UsersExceptions.NotFound {
		Users user = getUser(config, email, login);

		if (user == null) throw new UsersExceptions.NotFound();

		return Passwords.checkHash(user.getPassword(), password) ? user : null;
	}


	public static GeneratedToken generateTokenByLoginData(Config config,String email,String login,String password) throws UsersExceptions.NotFound, UsersExceptions.InvalidPassword{

		Users user = checkCredentials(config,email,login,password);

		if (user == null) throw new UsersExceptions.InvalidPassword();

		return new GeneratedToken(user, TokenTools.generateNew(user,config));

	}


	public static class GeneratedToken{
		private Token token;
		private Users user;

		public GeneratedToken(Users user,Token token) {
			this.token = token;
			this.user = user;
		}

		public Token getToken() {
			return token;
		}

		public Users getUser() {
			return user;
		}

	}
}
