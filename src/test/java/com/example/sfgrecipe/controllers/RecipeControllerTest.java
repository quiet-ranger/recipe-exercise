package com.example.sfgrecipe.controllers;

import com.example.sfgrecipe.exceptions.NotFoundException;
import com.example.sfgrecipe.model.Recipe;
import com.example.sfgrecipe.presentation.model.RecipeViewModel;
import com.example.sfgrecipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    ProfileHelper profileHelper;

    RecipeController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new RecipeController(recipeService, profileHelper);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler(new ProfileHelper(null)))
                .build();
    }

    @Test // GET /recipe/1
    public void getRecipeWorksWithValidId() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeService.findById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"))
                ;
    }

    @Test
    public void getRecipeWorksWithInvalidNumericId() throws Exception {
        when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/recipe/1"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"))
        ;
    }

    @Test // Without a real context, the system behaves like in prod by default
    public void getRecipeWorksWithInvalidNonNumericIdInProd() throws Exception {
//        when(env.getActiveProfiles()).thenReturn(new String[]{"prod"});
        mockMvc.perform(get("/recipe/abc"))
                .andExpect(status().isBadRequest())
                .andExpect(model().attributeExists("verbose"))
                .andExpect(model().attribute("verbose", false))
                .andExpect(view().name("400error"))
        ;
    }

    @Disabled  // Profile aware test not possible this way
    public void getRecipeWorksWithInvalidNonNumericIdInLowerEnvironment() throws Exception {
        mockMvc.perform(get("/recipe/abc"))
                .andExpect(status().isBadRequest())
                .andExpect(model().attributeExists("verbose"))
                .andExpect(model().attribute("verbose", true))
                .andExpect(view().name("400error"))
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
            )
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/recipe/2"));
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

    @Test // DELETE using RESTful standard
    public void deletingByIdFromRESTclient() throws Exception {
        mockMvc.perform(delete("/recipe/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                ;
        verify(recipeService, times(1)).deleteById(anyLong());
    }

    @Test // GET workaround to achieve deletion from forms
    public void deletingByIdFromHTML5Form() throws Exception {
        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
        ;
        verify(recipeService, times(1)).deleteById(anyLong());
    }

}