package karas.dominik.meal_planner.meal.domain;

import karas.dominik.meal_planner.common.DayOfTheWeek;
import karas.dominik.meal_planner.meal.domain.dto.RecipeDto;
import karas.dominik.meal_planner.meal.infrastructure.externalapi.ExternalRecipeProvider;
import karas.dominik.meal_planner.meal.infrastructure.pdfgenerator.GeneratePdfQuery;
import karas.dominik.meal_planner.meal.infrastructure.pdfgenerator.PdfGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
public class WeeklyPdfServiceImpl implements WeeklyPdfService {

    private final PdfGenerator generator;
    private final ExternalRecipeProvider recipeProvider;

    @Override
    public byte[] generate(CreateWeeklyEatingPlanQuery query) {
        return generator.generate(new GeneratePdfQuery(fetchRecipes(query)));
    }

    private Map<DayOfTheWeek, Optional<RecipeDto>> fetchRecipes(CreateWeeklyEatingPlanQuery query) {
        var mondayRecipe = query.monday().map(recipeProvider::getExternalRecipe);
        var tuesdayRecipe = query.tuesday().map(recipeProvider::getExternalRecipe);
        var wednesdayRecipe = query.wednesday().map(recipeProvider::getExternalRecipe);
        var thursdayRecipe = query.thursday().map(recipeProvider::getExternalRecipe);
        var fridayRecipe = query.friday().map(recipeProvider::getExternalRecipe);
        var saturdayRecipe = query.saturday().map(recipeProvider::getExternalRecipe);
        var sundayRecipe = query.sunday().map(recipeProvider::getExternalRecipe);
        return Map.of(
                MONDAY, mondayRecipe,
                TUESDAY, tuesdayRecipe,
                WEDNESDAY, wednesdayRecipe,
                THURSDAY, thursdayRecipe,
                FRIDAY, fridayRecipe,
                SATURDAY, saturdayRecipe,
                SUNDAY, sundayRecipe
        );
    }
}
