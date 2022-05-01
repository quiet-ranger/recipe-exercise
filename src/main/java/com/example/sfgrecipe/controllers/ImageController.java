package com.example.sfgrecipe.controllers;

import com.example.sfgrecipe.model.Recipe;
import com.example.sfgrecipe.presentation.model.RecipeViewModel;
import com.example.sfgrecipe.services.ImageService;
import com.example.sfgrecipe.services.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Controller
public class ImageController {

    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{recipeId}/image/edit")
    public String handleImageUpdateRequest(@PathVariable String recipeId, Model model) {
        RecipeViewModel recipeViewModel = new RecipeViewModel();
        // Note that the form only needs the recipe id
        recipeViewModel.setId(Long.valueOf(recipeId));
        model.addAttribute("recipe", recipeViewModel);
        return "recipe/ImageUploadForm";
    }

    @PostMapping("/recipe/{recipeId}/image/edit")
    public String handleImagePost(@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile file) {
        imageService.saveImageFile(Long.valueOf(recipeId), file);
        return String.format( "redirect:/recipe/%s", recipeId);
    }

    @GetMapping("/recipe/{recipeId}/image")
    public void handleRetrieveImage(@PathVariable String recipeId, HttpServletResponse response) throws Exception {
        Recipe recipe = recipeService.findById(Long.valueOf(recipeId));

        byte[] image = new byte[0];
        if ( recipe.getImage() != null ) {
          image = new byte[recipe.getImage().length];
          int i = 0;
          for (Byte b : recipe.getImage() ) {
              image[i++] = b;
          }
        }
        response.setContentType("image/jpeg");
        InputStream is = new ByteArrayInputStream(image);
        IOUtils.copy(is, response.getOutputStream());
    }
}
