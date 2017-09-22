package future.models.data;

import com.datastax.driver.mapping.annotations.Transient;
import com.datastax.driver.mapping.annotations.UDT;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@UDT(name="template")
public class Template {

	private String subject;

	private String content;

	@Transient
	private Map variables = new HashMap();

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void pushVariable(String name,Object element) {
		this.variables.put(name, element);
	}


	private String buildViaFreeMarker(String template) throws BuildingTemplateException{
		try {
			StringTemplateLoader stringLoader = new StringTemplateLoader();
			String firstTemplate = "template_id";
			stringLoader.putTemplate(firstTemplate, template);

			Configuration cfg = new Configuration();
			cfg.setTemplateLoader(stringLoader);
			freemarker.template.Template templateB = cfg.getTemplate(firstTemplate);
			Writer out = new StringWriter();
			templateB.process(this.variables, out);
			String stringOut = out.toString();

			return stringOut;
		} catch (Exception ex) {
			throw new BuildingTemplateException(ex);
		}
	}

	public Built build() throws BuildingTemplateException{
		return new Built(
				buildViaFreeMarker(subject),
				buildViaFreeMarker(content)
		);
	}


	public static class NotFoundTemplateForLanguage extends Exception {}

	public static class BuildingTemplateException extends Exception {
		private Exception subException;

		public BuildingTemplateException(Exception exception) {
			this.subException = exception;
		}
	}

	public static class Built {
		private final String subject;
		private final String content;

		public Built(String subject, String content) {
			this.subject = subject;
			this.content = content;
		}

		public String getSubject() {
			return subject;
		}

		public String getContent() {
			return content;
		}
	}
}
