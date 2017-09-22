package future.models.data;

public enum Language {

	PL, EN;

	public static Language recognize(String lang) throws NotRecognizedException{
		if (lang == null) throw new NotRecognizedException();

		lang = lang.toUpperCase();

		for(Language val : Language.values())
			if (val.name().equals(lang))
				return val;

		throw new NotRecognizedException();
	}

	public static class NotRecognizedException extends Exception{};
}
