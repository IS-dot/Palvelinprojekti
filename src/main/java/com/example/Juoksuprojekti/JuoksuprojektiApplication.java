package com.example.Juoksuprojekti;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.Juoksuprojekti.domain.Run;
import com.example.Juoksuprojekti.domain.RunRepository;
import com.example.Juoksuprojekti.domain.User;
import com.example.Juoksuprojekti.domain.UserRepository;

@SpringBootApplication
public class JuoksuprojektiApplication {
	private static final Logger log = LoggerFactory.getLogger(JuoksuprojektiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(JuoksuprojektiApplication.class, args);
	}

	@Bean
	public CommandLineRunner runDemo(RunRepository rrepository, UserRepository urepository) {
		return (args) -> {
			log.info("save a couple of runs");

			User user1 = new User("user", "$2a$10$1814xC2KWOz2qpyKvUpuR.Nab1qt/Ew28q.2nHhfagDxAyVcW4ciq", "USER",
					"user@email.com", 0.0);
			User user2 = new User("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN",
					"admin@email.com", 0.0);
			User user3 = new User("testi", "$2a$10$1814xC2KWOz2qpyKvUpuR.Nab1qt/Ew28q.2nHhfagDxAyVcW4ciq", "USER",
					"testi@email.com", 0.0);
			urepository.save(user1);
			urepository.save(user2);
			urepository.save(user3);

			// huom! restin ansiosta käyttäjätunnuksen ja salasanan jne saa selaimeen
			// näkyviin, ei hyvä

			// luodaan joitain testisuorituksia
			Run run1 = new Run("Polkujuoksu", 5.8, "1120", "1230", urepository.findByUsername("user"));
			Run run2 = new Run("Vauhtikestävyys", 4.0, "1530", "1600", urepository.findByUsername("testi"));
			Run run3 = new Run("Peruskunto", 12.5, "1700", "1810", urepository.findByUsername("testi"));
			// tallennetaan
			rrepository.save(run1);
			rrepository.save(run2);
			rrepository.save(run3);

			// Create users: admin/admin user/user

			log.info("fetch all runs");
			for (Run run : rrepository.findAll()) {
				log.info(run.toString());
			}

		};
	}
}
