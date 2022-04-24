package com.example.sfgrecipe.services;

import com.example.sfgrecipe.model.Recipe;
import com.example.sfgrecipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.IntStream;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
        if ( recipe != null ) {
            try {
                byte[] imageBytes = file.getBytes();
                Byte[] image = IntStream
                        .range(0, imageBytes.length)
                        .mapToObj(i -> imageBytes[i])
                        .toArray(Byte[]::new);
                recipe.setImage(image);
                recipeRepository.save(recipe);
            } catch (IOException e) {
                log.error("This condition needs to be handled more gracefully");
            }
        }
        else {
            log.error("Non-existent recipe condition needs to be dealt with");
        }
    }
}
