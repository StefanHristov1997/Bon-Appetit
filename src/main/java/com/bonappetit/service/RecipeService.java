package com.bonappetit.service;

import com.bonappetit.model.dto.binding.AddRecipeDTO;
import com.bonappetit.model.dto.view.RecipeViewModel;
import com.bonappetit.model.enums.CategoryNameEnum;

import java.util.List;

public interface RecipeService {

    void addRecipe(AddRecipeDTO addRecipeDTO);

    List<RecipeViewModel> getRecipeByCategoryName(CategoryNameEnum category);
}
