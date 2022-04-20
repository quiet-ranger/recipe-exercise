package com.example.sfgrecipe.presentation.model;

import com.example.sfgrecipe.model.Notes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotesViewModel {
    private Long id;
    private String description;

    public static Notes toNotes(NotesViewModel source) {
        if (source == null) return null;
        Notes notes = new Notes();
        notes.setId(source.getId());
        notes.setRecipeNotes(source.getDescription());
        return notes;
    }

    public static NotesViewModel fromNotes(Notes source) {
        if (source == null) return null;
        return new NotesViewModel(source.getId(), source.getRecipeNotes());
    }
}
