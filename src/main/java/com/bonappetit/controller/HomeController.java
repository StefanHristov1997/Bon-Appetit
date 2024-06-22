package com.bonappetit.controller;

import com.bonappetit.model.enums.CategoryNameEnum;
import com.bonappetit.service.RecipeService;
import com.bonappetit.util.CurrentUserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final RecipeService recipeService;
    private final CurrentUserSession currentUserSession;

    @Autowired
    public HomeController(RecipeService recipeService, CurrentUserSession currentUserSession) {
        this.recipeService = recipeService;
        this.currentUserSession = currentUserSession;
    }

    @GetMapping("/")
    public String viewIndex() {

        if (currentUserSession.isLogged()) {
            return "redirect:/home";
        }

        return "index";
    }

    @GetMapping("/home")
    public String viewHome(Model model) {

        if (!currentUserSession.isLogged()) {
            return "redirect:/";
        }

        model.addAttribute("desserts", recipeService.getRecipeByCategoryName(CategoryNameEnum.DESSERT));
        model.addAttribute("dishes", recipeService.getRecipeByCategoryName(CategoryNameEnum.MAIN_DISH));
        model.addAttribute("cocktails", recipeService.getRecipeByCategoryName(CategoryNameEnum.COCKTAIL));

        return "home";
    }
}
