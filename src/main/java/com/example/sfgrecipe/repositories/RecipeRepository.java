package com.example.sfgrecipe.repositories;

import com.example.sfgrecipe.model.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
