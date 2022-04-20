package com.example.sfgrecipe.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
//@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@EqualsAndHashCode.Include
    private Long id;

    private String description;
    private BigDecimal amount;

    @OneToOne(fetch = FetchType.EAGER)
    private UnitOfMeasure uom;

    @ManyToOne
    private Recipe recipe;

    public Ingredient() {
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom, Recipe recipe) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
        this.recipe = recipe;
    }

}
