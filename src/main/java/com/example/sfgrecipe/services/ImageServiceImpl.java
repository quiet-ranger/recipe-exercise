package com.example.sfgrecipe.services;

import com.example.sfgrecipe.model.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.IntStream;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeService recipeService;

    public ImageServiceImpl(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
        Recipe recipe = recipeService.findById(recipeId);
        try {
            byte[] image = file.getBytes();
            recipe.setImage(IntStream.range(0, image.length)
                    .mapToObj(i -> image[i])
                    .toArray(Byte[]::new));
        } catch (IOException e) {
            log.error("This condition needs to be handled more gracefully");
        }
        recipeService.upsert(recipe);
        return;
    }
}
