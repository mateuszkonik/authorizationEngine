package future.util;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;
import future.database.Database;
import future.models.data.ApiKeys;
import future.models.data.ApiKeysByUsers;

import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;

public class ApiKeysTools {

	private static final ApiKeysByUsersAccessor keysByUsersAccessor =
			Database.getMapper().createAccessor(ApiKeysByUsersAccessor.class);


	private static final ApiKeysAccessor keysAccessor =
			Database.getMapper().createAccessor(ApiKeysAccessor.class);

	@Accessor
	public interface ApiKeysAccessor{
		@Query("SELECT * FROM api_keys WHERE public_key = :public_key ")
		Result<ApiKeys> getApiKeys(@Param("public_key") UUID publicKey);
	}

	@Accessor
	public interface ApiKeysByUsersAccessor{
		@Query("SELECT * FROM api_keys_by_users WHERE user_id = :user_id ")
		ResultSet getApiKeysByUsers(@Param("user_id") UUID userId);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Pobiera listę kluczy publicznych użytkownika do API
	 * @param userId
	 * @return
	 */
	public static LinkedList<ApiKeys> getUserApiKeys(UUID userId) {

		LinkedList<ApiKeys> result = new LinkedList<>();
		ApiKeys keysPair;
		UUID publicKey;

		ResultSet userKeys = keysByUsersAccessor.getApiKeysByUsers(userId);
		for(Row keys : userKeys.all()) {
			publicKey = keys.getUUID("public_key");
			keysPair = keysAccessor.getApiKeys(publicKey).one();

			if(keysPair != null && !keysPair.getDeleted()) {
				keysPair.setPrivateKey(null);
				result.add(keysPair);
			}
		}

		return result;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Tworzy nowy klucz do API
	 * @param userId
	 * @param permissions
	 * @return
	 */
	public static ApiKeys createNewApiKeys(UUID userId, Set<String> permissions) {
		ApiKeys apiKeysModel = new ApiKeys(userId, permissions);

		Mapper<ApiKeys> mapper = Database.getMapper().mapper(ApiKeys.class);
		mapper.save(apiKeysModel);

		ApiKeysByUsers apiKeysByUsersModel = new ApiKeysByUsers(userId, apiKeysModel.getPublicKey());
		Mapper<ApiKeysByUsers> mapperKeysByUsers = Database.getMapper().mapper(ApiKeysByUsers.class);
		mapperKeysByUsers.save(apiKeysByUsersModel);

		return apiKeysModel;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Usuwa klucz do API (ustawia flagę deleted)
	 * @param userId
	 * @param publicKey
	 * @return
	 */
	public static boolean deleteApiKeys(UUID userId, UUID publicKey) {

		ApiKeys keysPair = keysAccessor.getApiKeys(publicKey).one();

		if( keysPair == null || keysPair.getDeleted() || !keysPair.getUserId().equals(userId) ) {
			return false;
		} else {
			Mapper<ApiKeys> mapper = Database.getMapper().mapper(ApiKeys.class);
			keysPair.setDeleted(true);
			mapper.save(keysPair);

			return true;
		}
	}
}
