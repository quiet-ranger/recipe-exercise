package com.example.sfgrecipe.presentation.model;

import com.example.sfgrecipe.model.UnitOfMeasure;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UnitOfMeasureViewModel {
    private Long id;
    private String description;

    public static UnitOfMeasure toUnitOfMeasure(UnitOfMeasureViewModel source) {
        if (source == null) return null;
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(source.getId());
        unitOfMeasure.setDescription(source.getDescription());
        return unitOfMeasure;
    }

    public static UnitOfMeasureViewModel fromUnitOfMeasure(UnitOfMeasure source) {
        if (source == null) return null;
        return new UnitOfMeasureViewModel(source.getId(), source.getDescription());
    }
}
