package com.example.sfgrecipe.services;

import com.example.sfgrecipe.exceptions.NotFoundException;
import com.example.sfgrecipe.model.Recipe;
import com.example.sfgrecipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeService = new RecipeServiceImpl((recipeRepository));
    }

    @Test
    public void findByIdWorksWithValidId() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById(1L);

        assertNotNull(recipeReturned, "Null recipe returned");
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void findByIdThrowsExceptionWithInvalidId() throws Exception {
        Optional<Recipe> recipeResult = Optional.empty();
        when(recipeRepository.findById(anyLong())).thenReturn(recipeResult);

        Exception exception = assertThrows(
                NotFoundException.class,
                () -> recipeService.findById(1L)
        );
        // assertTrue(exception.getMessage().contains("not found"));
    }

    @Test
    void findAllWorks() {
        Recipe recipe = new Recipe();
        HashSet<Recipe> result = new HashSet<>();
        result.add(recipe);

        when(recipeRepository.findAll()).thenReturn(result);

        Set<Recipe> recipes = recipeService.getRecipes();
        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void deletingNonExistingIdHasNoSideEffect() {
        Long idToDelete = Long.MAX_VALUE;

        recipeService.deleteById(idToDelete);

        verify(recipeRepository, times(1)).deleteById(anyLong());
    }

}