package com.example.sfgrecipe.controllers;

import com.example.sfgrecipe.model.Recipe;
import com.example.sfgrecipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    public void testMockMVC() throws Exception {

        // The second option, MockMvcBuilders.webAppContext(), creates a much heavier object that is more
        // suitable for integration tests
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
//                .andExpect(header().stringValues("Content-Type","text/html"))
//                .andExpect(header().stringValues("Content-Type", "charset=UTF-8"))
                ;

//        MvcResult mvcResult = mockMvc.perform(get("/")).andReturn();
//        String headerValue = mvcResult.getResponse().getHeader("Content-Type");
//        assertEquals("text/html;charset=UTF-8", headerValue);
    }

    @Test
    void getIndexPage() {

        // Setting up a recipeService mock to return something we control
        Set<Recipe> testRecipes = new HashSet<>();
        Recipe a = new Recipe();
        a.setId(1L);
        Recipe b = new Recipe();
        b.setId(2L);
        testRecipes.add(a);
        testRecipes.add(b);

        when(recipeService.getRecipes()).thenReturn(testRecipes);

        // Because recipeService is mocked, anything that matches Set<Recipe> will become recoverable
        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        String viewName = indexController.getIndexPage(model);
        assertEquals("index", viewName, "Page name mismatch");

        // check that recipeService.getRecipes() was called just once
        verify(recipeService, times(1)).getRecipes();

        // Now we capture the argument stored in Model
        verify(model, times(1)).addAttribute( eq("recipes"), argumentCaptor.capture());

        // Time to recover and check part of the internal state of recipeService
        Set<Recipe> controllerInnerSet = argumentCaptor.getValue();
        assertEquals(2, controllerInnerSet.size(),"Size mismatch");
    }
}