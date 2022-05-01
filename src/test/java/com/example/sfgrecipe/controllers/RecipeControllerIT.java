package com.example.sfgrecipe.controllers;

import com.example.sfgrecipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles({"prod"})
public class RecipeControllerIT {

//    @Mock
//    Environment env;

//    @Autowired
//    private ApplicationContext context;

    @Autowired
    RecipeController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
//        RecipeController controller = context.getBean(RecipeController.class);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Disabled
    public void getRecipeWorksWithInvalidNonNumericIdInProd() throws Exception {
//        when(env.getActiveProfiles()).thenReturn(new String[]{"prod"});
        mockMvc.perform(get("/recipe/abc"))
                .andExpect(status().isBadRequest())
                .andExpect(model().attributeExists("verbose"))
                .andExpect(model().attribute("verbose", false))
                .andExpect(view().name("400error"))
        ;
    }

}
