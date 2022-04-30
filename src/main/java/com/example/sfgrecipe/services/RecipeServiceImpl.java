package com.example.sfgrecipe.services;

import com.example.sfgrecipe.exceptions.NotFoundException;
import com.example.sfgrecipe.model.Recipe;
import com.example.sfgrecipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(com.example.sfgrecipe.repositories.RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.info("Constructing getRecipes response");
        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findById(Long l) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(l);
        if ( !recipeOptional.isPresent() ) {
            throw new NotFoundException("Recipe not found.");
        }
        return recipeOptional.get();
    }

    @Override
    public Recipe upsert(Recipe entity) {
        return recipeRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }
}
