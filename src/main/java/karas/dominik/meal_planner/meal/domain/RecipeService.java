package karas.dominik.meal_planner.meal.domain;

import karas.dominik.meal_planner.meal.domain.dto.RecipeDto;

import java.util.List;

public interface RecipeService {

    List<RecipeDto> getRecipes(SearchRecipeQuery query);
}
