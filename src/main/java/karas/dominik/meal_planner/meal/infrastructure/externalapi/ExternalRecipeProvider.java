package karas.dominik.meal_planner.meal.infrastructure.externalapi;

import karas.dominik.meal_planner.meal.domain.SearchRecipeQuery;
import karas.dominik.meal_planner.meal.domain.dto.RecipeDto;

import java.util.List;

public interface ExternalRecipeProvider {

    List<RecipeDto> getExternalRecipes(SearchRecipeQuery query);

    RecipeDto getExternalRecipe(Long recipeId);
}
