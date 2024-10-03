package karas.dominik.meal_planner.meal.application;

import karas.dominik.meal_planner.meal.domain.SearchRecipeQuery;
import lombok.NoArgsConstructor;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
class RequestMapper {

    static SearchRecipeQuery asQuery(String recipeName) {
        return SearchRecipeQuery.builder()
                .recipeName(Optional.ofNullable(recipeName))
                .build();
    }
}
