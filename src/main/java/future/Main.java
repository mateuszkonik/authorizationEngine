package future;

import future.mock.AuthEngineConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.UUID;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(value = {"future"})
public class Main implements CommandLineRunner {

	public static final UUID APP_ID = UUID.fromString("88b3b3e8-3434-4b20-ba0c-95f1f41d8215");

	//todo: pusty url
	public static final String URL = "";

	public static final AuthEngineConfig AUTH_CONFIG = new AuthEngineConfig(
			"example url",
			UUID.fromString("88b3b3e8-3434-4b20-ba0c-95f1f41d8215"),
			"auth-back"
	);

	@Override
	public void run(String... arg) throws Exception {
		if (arg.length > 0 && arg[0].equals("exitcode")) {
			throw new ExitException();
		}
	}

	public static void main(String[] args) throws Exception {
		new SpringApplication(Main.class).run(args);
	}

	class ExitException extends RuntimeException implements ExitCodeGenerator {
		private static final long serialVersionUID = 1L;

		@Override
		public int getExitCode() {
			return 10;
		}
	}
}
