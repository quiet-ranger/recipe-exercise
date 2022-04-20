package com.example.sfgrecipe.presentation.model;

import com.example.sfgrecipe.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.convert.converter.Converter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryViewModel {
    private Long id;
    private String description;

    public static Category toCategory(CategoryViewModel source) {
        if (source == null) return null;
        Category category = new Category();
        category.setId(source.getId());
        category.setDescription(source.getDescription());
        return category;
    }

    public static CategoryViewModel fromCategory(Category source) {
        if (source == null) return null;
        return new CategoryViewModel(source.getId(), source.getDescription());
    }
}
