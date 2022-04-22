package com.example.sfgrecipe.services;

import com.example.sfgrecipe.model.Ingredient;

public interface IngredientService {

    Ingredient findById(Long id);

    Ingredient upsert(Ingredient ingredient);

    void deleteById(Long id);

}
