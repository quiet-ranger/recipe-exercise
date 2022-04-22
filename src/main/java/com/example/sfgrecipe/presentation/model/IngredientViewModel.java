package com.example.sfgrecipe.presentation.model;

import com.example.sfgrecipe.model.Ingredient;
import com.example.sfgrecipe.model.Recipe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class IngredientViewModel {
    private Long id;
    private Long recipeId;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureViewModel unitOfMeasure;

    public static Ingredient toIngredient(IngredientViewModel source, Recipe recipe) {
        if (source == null) return null;
        Ingredient ingredient = new Ingredient();
        ingredient.setId(source.getId());
        ingredient.setRecipe(recipe);
        ingredient.setDescription(source.getDescription());
        ingredient.setAmount(source.getAmount());
        ingredient.setUom(UnitOfMeasureViewModel.toUnitOfMeasure(source.getUnitOfMeasure()));
        return ingredient;
    }

    public static IngredientViewModel fromIngredient(Ingredient source) {
        if (source == null) return null;
        IngredientViewModel ingredient = new IngredientViewModel();
        ingredient.setId(source.getId());
        ingredient.setRecipeId(source.getRecipe().getId());
        ingredient.setDescription(source.getDescription());
        ingredient.setAmount(source.getAmount());
        ingredient.setUnitOfMeasure(UnitOfMeasureViewModel.fromUnitOfMeasure(source.getUom()));
        return ingredient;
    }
}
