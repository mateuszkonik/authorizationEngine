package future.mock;

import future.models.data.Config;
import future.models.data.Users;

import java.util.UUID;

import static future.Main.APP_ID;

public class AuthEngineConfig {

	public AuthEngineConfig(String address, UUID appId, String name) {
	}

	public AuthEngineConfig check(UUID authorization) {
		return this;
	}

	public AuthEngineConfig verifyPermissions(String permission) {
		return this;
	}

	public AuthEngineResponse.User getUserData() {
		try {
			return new AuthEngineResponse.User(Users.createNew(Config.get(APP_ID)));
		} catch (Config.NotFound e) {
			System.err.println(e.getMessage());

			return null;
		}
	}
}
