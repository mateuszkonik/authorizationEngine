package future.util.exceptions;

public abstract class UsersExceptions extends Exception {

	public UsersExceptions() {}

	public UsersExceptions(String message) {
		super(message);
	}

	public static class NotFound extends UsersExceptions {

		public NotFound() {}

		public NotFound(String message) {
			super(message);
		}
	}

	public static class InvalidPassword extends Exception{}
}
