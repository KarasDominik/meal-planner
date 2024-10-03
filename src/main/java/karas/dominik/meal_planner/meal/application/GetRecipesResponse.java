package karas.dominik.meal_planner.meal.application;

import karas.dominik.meal_planner.meal.domain.dto.RecipeDto;
import lombok.Builder;

import java.util.List;

record GetRecipesResponse(List<Recipe> recipes) {

    public static GetRecipesResponse of(List<RecipeDto> recipes) {
        return new GetRecipesResponse(recipes.stream()
                .map(Recipe::of)
                .toList());
    }

    @Builder
    record Recipe(Long id, String name, String description, List<Ingredient> ingredients) {

        record Ingredient(String name, String measurement) {

            static Ingredient of(RecipeDto.Ingredient dto) {
                return new Ingredient(dto.name(), dto.measurement());
            }
        }

        static Recipe of(RecipeDto dto) {
            return Recipe.builder()
                    .id(dto.id())
                    .name(dto.name())
                    .description(dto.description())
                    .ingredients(dto.ingredients().stream()
                            .map(Ingredient::of)
                            .toList())
                    .build();
        }
    }
}
