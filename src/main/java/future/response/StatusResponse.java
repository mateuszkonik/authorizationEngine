package future.response;

import java.util.ArrayList;
import java.util.List;

public class StatusResponse {

	public final static String OK_STATUS = "Ok";
	public final static String ERROR_STATUS = "Fail";

	protected String status;

	public static class ERROR extends StatusResponse {

		private List<ErrorMessage> messages = new ArrayList<>();

		public ERROR() {
			status = ERROR_STATUS;
		}

		public ERROR(ErrorMessage message) {
			this();
			this.messages.add(message);
		}

		public ERROR(List<ErrorMessage> messages) {
			this();
			this.messages = messages;
		}

		public List<ErrorMessage> getMessages() {
			return messages;
		}
	}

	public  static class OK extends StatusResponse {

		private SuccessMessage message;

		public OK() {
			status = OK_STATUS;
		}

		public OK(SuccessMessage message) {
			this();
			this.message = message;
		}

		public SuccessMessage getMessage() {
			return message;
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
