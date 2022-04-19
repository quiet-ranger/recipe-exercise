package com.example.sfgrecipe.controllers;

import com.example.sfgrecipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IndexControllerTest {

    @Mock RecipeService recipeService;
    @Mock Model model;

    IndexController indexController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        indexController = new IndexController(recipeService);
    }

    @Test
    void getIndexPage() {
        String viewName = indexController.getIndexPage(model);
        assertEquals("index", viewName, "Page name mismatch");

        // check that recipeService.getRecipes() was called just once
        verify(recipeService, times(1)).getRecipes();

        // check that model.addAttribute( "recipes", <any_Set_instance> ) was called just once
        verify(model, times(1)).addAttribute( eq("recipes"), anySet());
    }
}