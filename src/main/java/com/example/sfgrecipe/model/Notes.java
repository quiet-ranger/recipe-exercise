package com.example.sfgrecipe.model;

import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @OneToOne
    private Recipe recipe;

    // This is needed because this field will likely store more than 256 characters
    @Lob
    private String recipeNotes;

    public Notes() {
    }

}
