package future.mock;

import future.models.data.Users;

public class AuthEngineResponse {

	public static class User extends future.models.User {

		public User(Users user) {
			super(user);
		}
	}
}
