package wcs.cerebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import wcs.cerebook.entity.CerebookPost;
import wcs.cerebook.repository.PostRepository;

import java.util.Date;

@SpringBootApplication
public class CerebookApplication {
	public static void main(String[] args) {
		SpringApplication.run(CerebookApplication.class, args);

	}


}
