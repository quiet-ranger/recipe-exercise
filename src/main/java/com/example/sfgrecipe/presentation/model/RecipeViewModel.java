package com.example.sfgrecipe.presentation.model;

import com.example.sfgrecipe.model.Difficulty;
import com.example.sfgrecipe.model.Recipe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeViewModel {
    private Long id;

    @NotBlank @Size(min = 3, max = 255)
    private String description;

    @Min(1) @Max(999)
    private Integer prepTime;

    @Min(1) @Max(999)
    private Integer cookTime;

    @Min(1) @Max(100)
    private Integer servings;

    private String source;

    @URL
    private String url;

    @NotBlank
    private String directions;

    private Set<IngredientViewModel> ingredients = new HashSet<>();
    private Difficulty difficulty;
    private NotesViewModel notes;
    private Set<CategoryViewModel> categories = new HashSet<>();

    public static Recipe toRecipe(RecipeViewModel source) {
        if (source == null) return null;
        Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setDescription(source.getDescription());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setCookTime(source.getCookTime());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        recipe.setDirections(source.getDirections());

        recipe.setIngredients(
                source.getIngredients().stream()
                        .map(ingredient -> IngredientViewModel.toIngredient(ingredient, recipe))
                        .collect(Collectors.toSet())
        );

        recipe.setDifficulty(source.getDifficulty());
        recipe.setNotes(NotesViewModel.toNotes(source.getNotes()));

        recipe.setCategories(
                source.getCategories().stream()
                        .map(category -> CategoryViewModel.toCategory(category))
                        .collect(Collectors.toSet())
        );

        return recipe;
    }

    public static RecipeViewModel fromRecipe(Recipe source) {
        if (source == null) return null;
        return new RecipeViewModel(
                source.getId(),
                source.getDescription(),
                source.getPrepTime(),
                source.getCookTime(),
                source.getServings(),
                source.getSource(),
                source.getUrl(),
                source.getDirections(),
                source.getIngredients().stream()
                        .map(ingredient -> IngredientViewModel.fromIngredient(ingredient))
                        .collect(Collectors.toSet()),
                source.getDifficulty(),
                NotesViewModel.fromNotes(source.getNotes()),
                source.getCategories().stream()
                        .map(category -> CategoryViewModel.fromCategory(category))
                        .collect(Collectors.toSet())
        );
    }
}
