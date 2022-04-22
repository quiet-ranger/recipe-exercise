package com.example.sfgrecipe.repositories;

import com.example.sfgrecipe.model.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
}
