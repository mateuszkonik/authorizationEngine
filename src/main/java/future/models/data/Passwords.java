package future.models.data;

import org.mindrot.jbcrypt.BCrypt;

public class Passwords {

	public static String hash(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public static Boolean checkHash(String hash,String candidate) {
		return BCrypt.checkpw(candidate, hash);
	}
}