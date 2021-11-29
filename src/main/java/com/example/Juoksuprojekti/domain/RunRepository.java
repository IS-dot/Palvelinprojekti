package com.example.Juoksuprojekti.domain;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface RunRepository extends CrudRepository<Run, Long> {

	List<Run> findAllByType(String type);

	List<Run> findAllByPerfDay(LocalDate perfDay);

	List<Run> findAllByUser(User user);

	List<Run> findAllByOrderByIdAsc();

	List<Run> findAllByOrderByPerfDayDesc();
}
