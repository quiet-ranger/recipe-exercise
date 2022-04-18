package com.example.sfgrecipe.controllers;

import com.example.sfgrecipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class IndexController {

    static {
        log.info("Web page controller class loaded...");
    }

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/","/index","/index.html","/index.htm"})
    public String getIndexPage(Model model) {
        model.addAttribute("recipes", recipeService.getRecipes() );
        return "index";
    }

}
