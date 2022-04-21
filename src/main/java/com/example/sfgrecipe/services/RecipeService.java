package com.example.sfgrecipe.services;

import com.example.sfgrecipe.model.Recipe;

import java.util.Set;

public interface RecipeService {

    public Set<Recipe> getRecipes();

    public Recipe findById(Long l);

    public Recipe upsert(Recipe entity);
}