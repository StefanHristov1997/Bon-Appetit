package com.bonappetit.controller;

import com.bonappetit.model.dto.binding.AddRecipeDTO;
import com.bonappetit.service.RecipeService;
import com.bonappetit.util.CurrentUserSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final CurrentUserSession currentUserSession;

    @Value("${binding-result-package}")
    private String bindingResultPackage;

    private final String attribute = "addRecipeDTO";

    public RecipeController(RecipeService recipeService, CurrentUserSession currentUserSession) {
        this.recipeService = recipeService;
        this.currentUserSession = currentUserSession;
    }


    @GetMapping("/add-recipe")
    public String viewAddRecipe(Model model) {

        if(!currentUserSession.isLogged()){
            return "redirect:/";
        }

        if(!model.containsAttribute(attribute)){
            model.addAttribute(attribute, new AddRecipeDTO());
        }

        return "recipe-add";
    }

    @PostMapping("/add-recipe")
    public String doAddRecipe(
            @Valid AddRecipeDTO addRecipeDTO,
            BindingResult bindingResult,
            RedirectAttributes rAtt)
    {

        if(!currentUserSession.isLogged()){
            return "redirect:/";
        }

        if(bindingResult.hasErrors()){
            rAtt.addFlashAttribute(attribute, addRecipeDTO);
            rAtt.addFlashAttribute(bindingResultPackage + "." + attribute, bindingResult);
            return "redirect:/recipes/add-recipe";
        }

        recipeService.addRecipe(addRecipeDTO);

        return "redirect:/home";
    }
}
