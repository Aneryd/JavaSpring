package com.example.test_spring.repo;

import com.example.test_spring.models.Film;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface FilmRepository extends CrudRepository<Film, Long>{
}
