package karas.dominik.meal_planner.meal.infrastructure.externalapi.themealdb;

import karas.dominik.meal_planner.meal.domain.SearchRecipeQuery;
import karas.dominik.meal_planner.meal.domain.dto.RecipeDto;
import karas.dominik.meal_planner.meal.infrastructure.externalapi.ExternalRecipeProvider;
import karas.dominik.meal_planner.meal.infrastructure.externalapi.themealdb.RecipesForTests.CREAMY_MUSHROOM_PASTA;
import karas.dominik.meal_planner.meal.infrastructure.externalapi.themealdb.RecipesForTests.SPICY_ARABIATTA_PASTA;

import java.util.List;

public class FakeClient implements ExternalRecipeProvider {

    @Override
    public List<RecipeDto> getExternalRecipes(SearchRecipeQuery query) {
        return List.of(
                RecipeDto.builder()
                        .id(SPICY_ARABIATTA_PASTA.ID)
                        .name(SPICY_ARABIATTA_PASTA.NAME)
                        .description(SPICY_ARABIATTA_PASTA.DESCRIPTION)
                        .ingredients(SPICY_ARABIATTA_PASTA.INGREDIENTS)
                        .build(),
                RecipeDto.builder()
                        .id(CREAMY_MUSHROOM_PASTA.ID)
                        .name(CREAMY_MUSHROOM_PASTA.NAME)
                        .description(CREAMY_MUSHROOM_PASTA.DESCRIPTION)
                        .ingredients(CREAMY_MUSHROOM_PASTA.INGREDIENTS)
                        .build()
        );
    }

    @Override
    public RecipeDto getExternalRecipe(Long recipeId) {
        return RecipeDto.builder()
                .id(SPICY_ARABIATTA_PASTA.ID)
                .name(SPICY_ARABIATTA_PASTA.NAME)
                .description(SPICY_ARABIATTA_PASTA.DESCRIPTION)
                .ingredients(SPICY_ARABIATTA_PASTA.INGREDIENTS)
                .build();
    }
}
