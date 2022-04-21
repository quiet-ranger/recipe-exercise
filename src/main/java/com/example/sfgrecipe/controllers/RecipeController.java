package com.example.sfgrecipe.controllers;

import com.example.sfgrecipe.model.Recipe;
import com.example.sfgrecipe.presentation.model.RecipeViewModel;
import com.example.sfgrecipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}")
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
        return "recipe/show";
    }

    @GetMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeViewModel());
        return "recipe/recipeform";
    }

    @GetMapping("recipe/{id}/update")
    public String requestRecipeUpdate(@PathVariable String id, Model model) {
        assert(id != null);
        model.addAttribute("recipe", RecipeViewModel.fromRecipe(recipeService.findById(Long.valueOf(id))));
        return "recipe/recipeform";
    }

//    @RequestMapping(name = "recipe", method = POST) This is the older way of doing things
    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeViewModel command) {
        Recipe savedRecipe = recipeService.upsert(RecipeViewModel.toRecipe(command));
        if (savedRecipe == null) {
            log.error("Failed to update or create new recipe");
            return "error";
        }
        return "redirect:/recipe/" + RecipeViewModel.fromRecipe(savedRecipe).getId();
    }

    @DeleteMapping("recipe/{id}")
    public String deleteRecipe(@PathVariable String id, Model model) {
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

    // Unfortunately, HTML5 forms support only two methods, GET and POST, therefore
    // a deletion needs to be provided via this non RESTful compliant mechanism
    @GetMapping("recipe/{id}/delete")
    public String deleteRecipeFromForm(@PathVariable String id, Model model) {
        return this.deleteRecipe(id, model);
    }
}