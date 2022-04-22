package com.example.sfgrecipe.controllers;

import com.example.sfgrecipe.model.Ingredient;
import com.example.sfgrecipe.model.Recipe;
import com.example.sfgrecipe.model.UnitOfMeasure;
import com.example.sfgrecipe.presentation.model.IngredientViewModel;
import com.example.sfgrecipe.presentation.model.RecipeViewModel;
import com.example.sfgrecipe.presentation.model.UnitOfMeasureViewModel;
import com.example.sfgrecipe.services.IngredientService;
import com.example.sfgrecipe.services.RecipeService;
import com.example.sfgrecipe.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
@Slf4j
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService,
                                IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{recipeId}/ingredient")
    public String listIngredients(@PathVariable String recipeId, Model model) {
        log.info("listIngredients end point invoked");
        model.addAttribute("recipe", recipeService.findById(Long.valueOf(recipeId)));
        return "ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeIngredientRequest(
            @PathVariable String recipeId,
            @PathVariable String ingredientId,
            Model model
    ) {
        model.addAttribute("ingredient",
                IngredientViewModel.fromIngredient(ingredientService.findById(Long.valueOf(ingredientId))));
        Set<UnitOfMeasureViewModel> uomList = new HashSet<>();
        unitOfMeasureService.listAllUnitsOfMeasure().forEach(
                (element) -> {
                    uomList.add(UnitOfMeasureViewModel.fromUnitOfMeasure(element));
                }
        );
        model.addAttribute("uomList", uomList);
        return "ingredient/ingredientform";
    }

    @PostMapping("/recipe/{recipeId}/ingredient/{ingredientId}")
    public String updateRecipeIngredient(@ModelAttribute IngredientViewModel command) {
        Recipe recipe = recipeService.findById(command.getRecipeId());
        assert( recipe != null );
        Ingredient ingredient = IngredientViewModel.toIngredient(command, recipe);
        ingredient.setUom(UnitOfMeasureViewModel.toUnitOfMeasure(command.getUnitOfMeasure()));

        Ingredient savedIngredient = ingredientService.upsert(ingredient);

        if (savedIngredient == null) {
            log.error("Failed to update or create new ingredient");
            return "error";
        }
        return "redirect:/recipe/" + command.getRecipeId();
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(
            @PathVariable String recipeId,
            @PathVariable String ingredientId,
            Model model) {
        assert(recipeId != null);
        assert(ingredientId != null);
        log.info(String.format("Deleting ingredient %s from recipe %s", ingredientId, recipeId));
        ingredientService.deleteById(Long.valueOf(ingredientId));
        model.addAttribute("recipe", RecipeViewModel.fromRecipe(recipeService.findById(Long.valueOf(recipeId))));
        return "recipe/show";
    }
}
