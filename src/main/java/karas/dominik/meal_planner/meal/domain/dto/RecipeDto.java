package karas.dominik.meal_planner.meal.domain.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record RecipeDto(Long id, String name, String description, List<Ingredient> ingredients) {

    public record Ingredient(String name, String measurement) {}
}
