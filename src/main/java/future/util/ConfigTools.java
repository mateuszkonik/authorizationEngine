package future.util;

import future.models.data.Language;
import future.models.data.Template;

import java.util.Map;

public class ConfigTools {

	public static Template getTemplateForLanguage(Map<Language,Template> map, Language lang) throws Template.NotFoundTemplateForLanguage{
		if(map.containsKey(lang))
			return map.get(lang);

		throw new Template.NotFoundTemplateForLanguage();
	}
}
