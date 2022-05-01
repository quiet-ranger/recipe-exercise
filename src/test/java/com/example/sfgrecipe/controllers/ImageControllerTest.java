package com.example.sfgrecipe.controllers;

import com.example.sfgrecipe.model.Recipe;
import com.example.sfgrecipe.services.ImageService;
import com.example.sfgrecipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ImageControllerTest {

    @Mock
    ImageService imageService;

    @Mock
    RecipeService recipeService;

    ImageController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new ImageController(imageService, recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler(new ProfileHelper(null)))
                .build();
    }

    @Test
    void handleImagePost() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile(
                "imagefile",
                "testing.txt",
                "text/plain",
                "En un lugar de La Mancha de cuyo nombre no que quiero acordar".getBytes(StandardCharsets.UTF_8)
        );

       // when(ImageService).
        mockMvc.perform(multipart("/recipe/1/image/edit").file(multipartFile))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/recipe/1"))
                .andExpect(view().name("redirect:/recipe/1"))
                ;

        verify(imageService, times(1)).saveImageFile(anyLong(),any());
    }

    @Test
    public void handleImageRetrieval() throws Exception {

        String s = "Simulated image content";
        Byte[] simulatedImage = new Byte[s.getBytes(StandardCharsets.UTF_8).length];
        int index = 0;
        for (byte b : s.getBytes(StandardCharsets.UTF_8)) {
            simulatedImage[index++] = b;
        }

        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setImage( simulatedImage );

        when(recipeService.findById(anyLong())).thenReturn(recipe);

        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                ;
        byte[] responseBytes = response.getContentAsByteArray();

        assertEquals(s.getBytes(StandardCharsets.UTF_8).length, responseBytes.length);
    }

    @Test
    public void getRecipeImageWorksWithInvalidNonNumericIdInProd() throws Exception {
        mockMvc.perform(get("/recipe/abc/image"))
                .andExpect(status().isBadRequest())
                .andExpect(model().attributeExists("verbose"))
                .andExpect(model().attribute("verbose", false))
                .andExpect(view().name("400error"))
        ;
    }


}