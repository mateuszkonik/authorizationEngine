package future.util;

import com.datastax.driver.mapping.Mapper;
import future.Main;
import future.database.Database;
import future.models.data.EventRedirection;

import java.util.UUID;

public class RedirectionTools {

	public static EventRedirection registerNew(String eventName, String url, UUID userId, UUID appId) {
		String code = Random.next();

		Mapper<EventRedirection> mapper = Database.getMapper().mapper(EventRedirection.class);

		EventRedirection eventRedirection = new EventRedirection();
		eventRedirection.setCode(code);
		eventRedirection.setEvent(eventName);
		eventRedirection.setUrl(url);
		eventRedirection.setUserId(userId);
		eventRedirection.setAppId(appId);

		mapper.save(eventRedirection);

		return eventRedirection;
	}

	public static String buildBaseUrl(UUID appId) {
		return Main.URL + "/" + appId;
	}

	public static String buildRedirectUrl(EventRedirection eventRedirection) {
		return buildRedirectUrl(eventRedirection.getAppId(),eventRedirection.getCode());
	}

	public static String buildRedirectUrl(UUID appId,String code) {
		return buildBaseUrl(appId) + "/redirect/"+code;
	}


	public static EventRedirection find(String code) throws NotFoundCodeException{
		Mapper<EventRedirection> mapper = Database.getMapper().mapper(EventRedirection.class);
		EventRedirection result = mapper.get(code);

		if (result == null) {
			throw new NotFoundCodeException();
		}

		return result;
	}

	public static class NotFoundCodeException extends Exception{}
}
