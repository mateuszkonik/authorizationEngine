package future.util;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Random {

	private static final SecureRandom random = new SecureRandom();

	public static String next() {
		return next(30);
	}

	public static String next(int length) {
		return new BigInteger(130, random).toString(length);
	}
}
