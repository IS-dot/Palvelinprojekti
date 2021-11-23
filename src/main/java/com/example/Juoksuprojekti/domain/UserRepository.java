package com.example.Juoksuprojekti.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByUsername(String username);

	User findByUserId(Long userId);

	List<Run> findAllByUserId(Long userId);

}
