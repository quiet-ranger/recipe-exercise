package com.example.sfgrecipe.controllers;

import com.example.sfgrecipe.model.Recipe;
import com.example.sfgrecipe.presentation.model.RecipeViewModel;
import com.example.sfgrecipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    RecipeController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test // GET /recipe/1
    void getRecipeWorksWithValidId() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeService.findById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"))
                ;
    }

    @Test // GET /recipe/new
    public void recipeFormIsCorrectlyPresentedWhenRequested() throws Exception {
        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test // POST /recipe
    public void recipeFormPostLeadsToCorrectRedirection() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(2L);

        when(recipeService.upsert(any())).thenReturn(recipe);

        mockMvc.perform(post("/recipe")
                  .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                  .param("id", "")
//                  .param("description", "some string")
            )
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/recipe/show/2"));
    }

    @Test // GET /recipe/2/update
    public void recipeViewAfterUpdateIsCorrect() throws Exception {
        RecipeViewModel recipe = new RecipeViewModel();
        recipe.setId(2L);

        when(recipeService.findById(any())).thenReturn(RecipeViewModel.toRecipe(recipe));

        mockMvc.perform(get("/recipe/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }
}