package com.example.Juoksuprojekti;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
			// luodaan kaksi testikäyttäjää ja yksi admin
			User user1 = new User("user", "$2a$10$1814xC2KWOz2qpyKvUpuR.Nab1qt/Ew28q.2nHhfagDxAyVcW4ciq", "USER",
					"user@email.com", 0.0);
			User user2 = new User("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN",
					"admin@email.com", 0.0);
			User user3 = new User("testi", "$2a$10$1814xC2KWOz2qpyKvUpuR.Nab1qt/Ew28q.2nHhfagDxAyVcW4ciq", "USER",
					"testi@email.com", 0.0);
			urepository.save(user1);
			urepository.save(user2);
			urepository.save(user3);

			// huom! restin takia käyttäjätiedot saa lopulta myös selaimeen
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			// luodaan joitain testisuorituksia
			Run run1 = new Run("Base Run", 8.5, LocalDate.parse("05/05/2021", formatter),
					urepository.findByUsername("user"));
			Run run2 = new Run("Speed Workout", 4.0, LocalDate.parse("10/11/2021", formatter),
					urepository.findByUsername("testi"));
			Run run3 = new Run("Trail Run", 12.5, LocalDate.parse("18/11/2021", formatter),
					urepository.findByUsername("testi"));
			Run run4 = new Run("Tempo Run", 6.5, LocalDate.parse("15/11/2021", formatter),
					urepository.findByUsername("admin"));
			// tallennetaan
			rrepository.save(run1);
			rrepository.save(run2);
			rrepository.save(run3);
			rrepository.save(run4);

			// päivitetään vielä matkat testikäyttäjille
			user1.laskeMatka(8.5);
			user2.laskeMatka(6.5);
			user3.laskeMatka(16.5);

			urepository.save(user1);
			urepository.save(user2);
			urepository.save(user3);

			log.info("fetch all runs");
			for (Run run : rrepository.findAll()) {
				log.info(run.toString());
			}

		};
	}
}
