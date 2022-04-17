package com.example.sfgrecipe.repositories;

import com.example.sfgrecipe.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
