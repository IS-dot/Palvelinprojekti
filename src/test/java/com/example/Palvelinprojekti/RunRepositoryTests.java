package com.example.Palvelinprojekti;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.Juoksuprojekti.JuoksuprojektiApplication;
import com.example.Juoksuprojekti.domain.Run;
import com.example.Juoksuprojekti.domain.RunRepository;
import com.example.Juoksuprojekti.domain.User;
import com.example.Juoksuprojekti.domain.UserRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = JuoksuprojektiApplication.class)
public class RunRepositoryTests {

	@Autowired
	private RunRepository repository;

	@Autowired
	UserRepository urepository;

	@Test
	public void findByTypeShouldReturnRun() {
		List<Run> runs = repository.findAllByType("Trail Run");

		assertThat(runs).hasSize(1);
		assertThat(runs.get(0).getUser().getUsername()).isEqualTo("testi");

	}

	@Test
	public void findByUsernameShouldReturnRun() {
		User user = urepository.findByUsername("testi");
		List<Run> runs = repository.findAllByUser(user);

		assertThat(runs).hasSize(1);
		assertThat(runs.get(0).getUser().getUsername()).isEqualTo("testi");

	}

}
