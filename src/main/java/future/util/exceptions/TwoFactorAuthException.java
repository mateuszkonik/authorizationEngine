package future.util.exceptions;

public class TwoFactorAuthException extends Exception{

	public static class AuthIdAlreadyUsed extends TwoFactorAuthException {}
	public static class AuthIdNotRecognized extends TwoFactorAuthException {}
	public static class CodeVerificationFailed extends TwoFactorAuthException {}
	public static class TwoFactorTokenInvalid extends TwoFactorAuthException {}
	public static class AuthRequiresTwoPermissions extends TwoFactorAuthException {}
}
