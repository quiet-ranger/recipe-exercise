package com.example.sfgrecipe.controllers;

import com.example.sfgrecipe.presentation.model.RecipeViewModel;
import com.example.sfgrecipe.services.ImageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/recipe/{recipeId}/image")
    public String handleImageUpdateRequest(@PathVariable String recipeId, Model model) {
        RecipeViewModel recipeViewModel = new RecipeViewModel();
        // Note that the form only needs the recipe id
        recipeViewModel.setId(Long.valueOf(recipeId));
        model.addAttribute("recipe", recipeViewModel);
        return "recipe/ImageUploadForm";
    }

    @PostMapping("recipe/{recipeId}/image")
    public String handleImagePost(@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile file) {
        imageService.saveImageFile(Long.valueOf(recipeId), file);
        return String.format( "redirect:/recipe/%s", recipeId);
    }
}
