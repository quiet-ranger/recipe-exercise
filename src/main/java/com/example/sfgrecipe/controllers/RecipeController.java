package com.example.sfgrecipe.controllers;

import com.example.sfgrecipe.exceptions.NotFoundException;
import com.example.sfgrecipe.model.Recipe;
import com.example.sfgrecipe.presentation.model.RecipeViewModel;
import com.example.sfgrecipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@Slf4j
public class RecipeController {

    private boolean verboseEnabled = false;

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService, Environment env) {
        this.recipeService = recipeService;

        String[] profiles = env.getActiveProfiles();
        for ( int i = 0; i < profiles.length; i++ ) {
            verboseEnabled |= (profiles[i].equalsIgnoreCase("DEV") || profiles[i].equalsIgnoreCase("QA"));
        }

    }

    @GetMapping("/recipe/{id}")
    public String showById(@PathVariable String id, Model model) {
        model.addAttribute("recipe", RecipeViewModel.fromRecipe(recipeService.findById(Long.valueOf(id))));
        return "recipe/show";
    }

    @GetMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeViewModel());
        return "recipe/recipeform";
    }

    @GetMapping("recipe/{id}/update")
    public String handleRecipeUpdateRequest(@PathVariable String id, Model model) {
        assert(id != null);
        model.addAttribute("recipe", RecipeViewModel.fromRecipe(recipeService.findById(Long.valueOf(id))));
        return "recipe/recipeform";
    }

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

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNotFoundException(Exception exception) {
        NotFoundException nfe = (NotFoundException)exception;
        log.error(String.format("Handling NotFoundException: %s %d", nfe.getMessage(), nfe.getId()));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);
        modelAndView.addObject( "verbose", verboseEnabled);
        return modelAndView;
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleNumberFormatException(Exception exception) {
        log.error("Conversion to number failed");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("400error");
        modelAndView.addObject("exception", exception);
        modelAndView.addObject( "verbose", verboseEnabled);
        return modelAndView;
    }
}