package karas.dominik.meal_planner.meal.domain;

import karas.dominik.meal_planner.common.DayOfTheWeek;
import karas.dominik.meal_planner.meal.domain.dto.RecipeDto;
import karas.dominik.meal_planner.meal.infrastructure.externalapi.ExternalRecipeProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static karas.dominik.meal_planner.common.DayOfTheWeek.FRIDAY;
import static karas.dominik.meal_planner.common.DayOfTheWeek.MONDAY;
import static karas.dominik.meal_planner.common.DayOfTheWeek.SATURDAY;
import static karas.dominik.meal_planner.common.DayOfTheWeek.SUNDAY;
import static karas.dominik.meal_planner.common.DayOfTheWeek.THURSDAY;
import static karas.dominik.meal_planner.common.DayOfTheWeek.TUESDAY;
import static karas.dominik.meal_planner.common.DayOfTheWeek.WEDNESDAY;

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
