package karas.dominik.meal_planner.meal.domain;

import karas.dominik.meal_planner.meal.domain.dto.RecipeDto;
import karas.dominik.meal_planner.meal.infrastructure.externalapi.ExternalRecipeProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private final ExternalRecipeProvider recipeProvider;

    @Override
    public List<RecipeDto> getRecipes(SearchRecipeQuery query) {
        return recipeProvider.getExternalRecipes(query);
    }
}
