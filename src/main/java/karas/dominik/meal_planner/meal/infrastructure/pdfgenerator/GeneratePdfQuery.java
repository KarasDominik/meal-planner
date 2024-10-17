package karas.dominik.meal_planner.meal.infrastructure.pdfgenerator;

import karas.dominik.meal_planner.common.DayOfTheWeek;
import karas.dominik.meal_planner.meal.domain.dto.RecipeDto;

import java.util.Map;
import java.util.Optional;

public record GeneratePdfQuery(Map<DayOfTheWeek, Optional<RecipeDto>> recipesPerDay) {}
