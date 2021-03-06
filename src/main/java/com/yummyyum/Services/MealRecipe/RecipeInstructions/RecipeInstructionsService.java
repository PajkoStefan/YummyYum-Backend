package com.yummyyum.Services.MealRecipe.RecipeInstructions;

import com.yummyyum.Model.Meal;
import com.yummyyum.Model.MealRecipe.RecipeInstructions;

import java.util.List;
import java.util.Optional;

public interface RecipeInstructionsService {

    List<RecipeInstructions> getAllRecipeInstructions();

    Optional<RecipeInstructions> findRecipeInstructionsByMealName(String mealName);

    RecipeInstructions createNewRecipeInstructions(String cookSteps, String guidelines,
                                                   String customizeInstructions, Meal meal);

}
