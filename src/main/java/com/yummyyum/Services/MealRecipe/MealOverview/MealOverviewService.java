package com.yummyyum.Services.MealRecipe.MealOverview;

import com.yummyyum.Model.Meal;
import com.yummyyum.Model.MealRecipe.MealOverview;
import com.yummyyum.Model.MealOverviewEnum.DifficultyLevelEnum;
import com.yummyyum.Model.MealOverviewEnum.SpiceLevelEnum;

import java.util.List;
import java.util.Optional;

public interface MealOverviewService {

    List<MealOverview> getAllMealOverviews();

    Optional<MealOverview> findMealOverviewByMealName(String mealName);

    MealOverview createNewMealOverview(DifficultyLevelEnum difficultyLevel, SpiceLevelEnum spiceLevel,
                                       String prepCookTime, Integer cookWithin, Meal meal);

}
