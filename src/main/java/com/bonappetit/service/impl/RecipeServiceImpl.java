package com.bonappetit.service.impl;

import com.bonappetit.model.dto.binding.AddRecipeDTO;
import com.bonappetit.model.dto.view.RecipeViewModel;
import com.bonappetit.model.entity.Category;
import com.bonappetit.model.entity.Recipe;
import com.bonappetit.model.entity.User;
import com.bonappetit.model.enums.CategoryNameEnum;
import com.bonappetit.repository.CategoryRepository;
import com.bonappetit.repository.RecipeRepository;
import com.bonappetit.repository.UserRepository;
import com.bonappetit.service.RecipeService;
import com.bonappetit.util.CurrentUserSession;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;


    private final ModelMapper modelMapper;
    private final CurrentUserSession currentUserSession;

    public RecipeServiceImpl
            (RecipeRepository recipeRepository,
             UserRepository userRepository,
             CategoryRepository categoryRepository,
             ModelMapper modelMapper,
             CurrentUserSession currentUserSession) {

        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.currentUserSession = currentUserSession;
    }

    @Override
    public void addRecipe(AddRecipeDTO addRecipeDTO) {

        Recipe recipeToSave = modelMapper.map(addRecipeDTO, Recipe.class);

        User currentUser = userRepository.findById(currentUserSession.getId()).get();
        Category category = categoryRepository.findCategoryByName(addRecipeDTO.getCategory());

        recipeToSave.setCategory(category);
        recipeToSave.setAddedBy(currentUser);

        recipeRepository.save(recipeToSave);
    }

    @Override
    public List<RecipeViewModel> getRecipeByCategoryName(CategoryNameEnum category) {

        List<Recipe> recipes = this.recipeRepository.findByCategoryName(category);

        return recipes.stream().map(recipe -> modelMapper.map(recipe, RecipeViewModel.class)).toList();

    }
}
