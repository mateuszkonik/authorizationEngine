package future.util;

import future.models.data.Language;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Currency;

public class Validator {

	//private final Pattern emailPattern;

	public Validator() {
		//this.emailPattern = Pattern.compile(EMAIL_PATTERN);

	}

	public Boolean validateEmail(String email) {
		return EmailValidator.getInstance().isValid(email);
		///Matcher matcher = emailPattern.matcher(email);
		///return matcher.matches();
	}

	public Boolean validateCurrency(String currency) {
		try {
			Currency.getInstance(currency);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public Boolean validateLanguage(String lang) {
		Language language = null;

		try {
			language = Language.recognize(lang);
		} catch (Language.NotRecognizedException ex) {
			return false;
		}

		return true;
	}

	public Boolean validateNIP(String nip) {
		int nsize = nip.length();
		if (nsize != 10) {
			return false;
		}
		int[] weights = { 6, 5, 7, 2, 3, 4, 5, 6, 7 };
		int j = 0, sum = 0, control = 0;
		int csum = Integer.valueOf(nip.substring(nsize - 1));
		for (int i = 0; i < nsize - 1; i++) {
			char c = nip.charAt(i);
			j = Integer.valueOf(String.valueOf(c));
			sum += j * weights[i];
		}
		control = sum % 11;
		return (control == csum);
	}

//    private static final String EMAIL_PATTERN =
//		"/^[_a-z0-9-]+(\\.[_a-z0-9-]+)*(\\+[a-z0-9-]+)?@[a-z0-9-]+(\\.[a-z0-9-]+)*$/i";
//


	public static Validator get() {
		return new Validator();
	}

}
