package wcs.cerebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class CerebookApplication {

	public static void main(String[] args) {
		SpringApplication.run(CerebookApplication.class, args);
	}

}
