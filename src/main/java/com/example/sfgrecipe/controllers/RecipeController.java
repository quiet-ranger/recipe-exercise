package com.example.sfgrecipe.controllers;

import com.example.sfgrecipe.model.Recipe;
import com.example.sfgrecipe.presentation.model.RecipeViewModel;
import com.example.sfgrecipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/show/{id}")
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
        return "recipe/show";
    }

    @RequestMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeViewModel());
        return "recipe/recipeform";
    }

//    @RequestMapping(name = "recipe", method = POST) This is the older way of doing things
    @PostMapping
    @RequestMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeViewModel command) {
        Recipe savedRecipe = recipeService.upsert(RecipeViewModel.toRecipe(command));
        if (savedRecipe == null) {
            log.error("Failed to update or create new recipe");
            return "error";
        }
        return "redirect:/recipe/show/" + RecipeViewModel.fromRecipe(savedRecipe).getId();
    }
}