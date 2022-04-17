package com.example.sfgrecipe.services;

import com.example.sfgrecipe.model.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();
}