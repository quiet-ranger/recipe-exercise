package com.example.sfgrecipe.controllers;

import com.example.sfgrecipe.model.Recipe;
import com.example.sfgrecipe.services.IngredientService;
import com.example.sfgrecipe.services.RecipeService;
import com.example.sfgrecipe.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class IngredientControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    IngredientController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void listIngredientsSucceeds() throws Exception {
        // given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        when(recipeService.findById(anyLong())).thenReturn(recipe);

        // when
        mockMvc.perform(get("/recipe/1/ingredient"))
                .andExpect(status().isOk())
                .andExpect(view().name("ingredient/list"))
                .andExpect(model().attributeExists("recipe"))
                ;

        // then
        verify(recipeService, times(1)).findById(anyLong());

    }
}