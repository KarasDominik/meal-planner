package karas.dominik.meal_planner.meal.infrastructure.externalapi.themealdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.micrometer.common.util.StringUtils;
import karas.dominik.meal_planner.meal.domain.dto.RecipeDto;
import karas.dominik.meal_planner.meal.domain.dto.RecipeDto.Ingredient;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

record TheMealDbApiResponse(List<Recipe> meals) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    record Recipe(Long idMeal, String strMeal, String strCategory, String strInstructions,
                  String strIngredient1, String strIngredient2, String strIngredient3, String strIngredient4,
                  String strIngredient5, String strIngredient6, String strIngredient7, String strIngredient8,
                  String strIngredient9, String strIngredient10, String strIngredient11, String strIngredient12,
                  String strIngredient13, String strIngredient14, String strIngredient15, String strIngredient16,
                  String strIngredient17, String strIngredient18, String strIngredient19, String strIngredient20,
                  String strMeasure1, String strMeasure2, String strMeasure3, String strMeasure4,
                  String strMeasure5, String strMeasure6, String strMeasure7, String strMeasure8,
                  String strMeasure9, String strMeasure10, String strMeasure11, String strMeasure12,
                  String strMeasure13, String strMeasure14, String strMeasure15, String strMeasure16,
                  String strMeasure17, String strMeasure18, String strMeasure19, String strMeasure20) {

        RecipeDto asDto() {
            return RecipeDto.builder()
                    .id(idMeal())
                    .name(strMeal())
                    .description(strInstructions())
                    .ingredients(ingredients().entrySet().stream()
                            .map(entry -> new Ingredient(entry.getKey(), entry.getValue()))
                            .toList())
                    .build();
        }

        Map<String, String> ingredients() {
            var ingredients = new String[]
                    {
                            strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5, strIngredient6,
                            strIngredient7, strIngredient8, strIngredient9, strIngredient10, strIngredient11, strIngredient12,
                            strIngredient13, strIngredient14, strIngredient15, strIngredient16, strIngredient17, strIngredient18,
                            strIngredient19, strIngredient20
                    };

            var measures = new String[]
                    {
                            strMeasure1, strMeasure2, strMeasure3, strMeasure4, strMeasure5, strMeasure6,
                            strMeasure7, strMeasure8, strMeasure9, strMeasure10, strMeasure11, strMeasure12,
                            strMeasure13, strMeasure14, strMeasure15, strMeasure16, strMeasure17, strMeasure18,
                            strMeasure19, strMeasure20
                    };

            return IntStream.range(0, ingredients.length)
                    .filter(i -> StringUtils.isNotBlank(ingredients[i]) && StringUtils.isNotBlank(measures[i]))
                    .boxed()
                    .collect(toMap(i -> ingredients[i], i -> measures[i], (existing, replacement) -> existing));
        }
    }
}
