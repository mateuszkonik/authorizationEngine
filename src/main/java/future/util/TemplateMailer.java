package future.util;

import future.models.data.Template;
import org.iceo.mailsender.EmailAddress;
import org.iceo.mailsender.Mail;
import org.iceo.mailsender.MailSender;
import org.iceo.mailsender.sendgrid.SendGridMailSender;

public class TemplateMailer {

	static MailSender mailsender;
	private final static EmailAddress MAIL_SENDER = new EmailAddress("");

	static {
		mailsender = new SendGridMailSender("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");
	}

	public static void send( EmailAddress to, Template template) throws Template.BuildingTemplateException{
		Template.Built built = template.build();
		mailsender.asyncSend(new Mail(MAIL_SENDER,to, built.getSubject(), built.getContent()));
	}
}
