package com.example.Juoksuprojekti.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface RunRepository extends CrudRepository<Run, Long> {

	List<Run> findAllByType(String type);

	List<Run> findAllByOrderByIdAsc();

	List<Run> findAllByOrderByPerfDayDesc();
}
