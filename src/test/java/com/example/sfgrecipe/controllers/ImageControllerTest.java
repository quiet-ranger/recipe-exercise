package com.example.sfgrecipe.controllers;

import com.example.sfgrecipe.services.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ImageControllerTest {

    @Mock
    ImageService imageService;

    ImageController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new ImageController(imageService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
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
        mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/recipe/1"))
                .andExpect(view().name("redirect:/recipe/1"))
                ;

        verify(imageService, times(1)).saveImageFile(anyLong(),any());
    }
}