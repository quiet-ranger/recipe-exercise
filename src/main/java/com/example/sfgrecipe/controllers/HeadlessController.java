package com.example.sfgrecipe.controllers;

import com.example.sfgrecipe.model.Category;
import com.example.sfgrecipe.model.UnitOfMeasure;
import com.example.sfgrecipe.repositories.CategoryRepository;
import com.example.sfgrecipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class HeadlessController {

    static {
        log.info("RESTful controller class loaded...");
    }

    private CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public HeadlessController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @GetMapping({"/category"})
    public Category getCategory(@RequestParam(value="category", defaultValue="American") String category) {
        return categoryRepository.findByDescription(category).get();
    }

    @GetMapping({"/uom"})
    public UnitOfMeasure getUnitOfMeasure(@RequestParam(value="unit", defaultValue="Pinch") String unit) {
        return unitOfMeasureRepository.findByDescription(unit).get();
    }

}
