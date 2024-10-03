package karas.dominik.meal_planner.meal.domain;

import io.micrometer.common.util.StringUtils;
import lombok.Builder;

import java.util.Optional;

@Builder
public record SearchRecipeQuery(Optional<String> recipeName) {

    public SearchRecipeQuery {
        recipeName = Optional.ofNullable(recipeName)
                .flatMap(t -> t)
                .filter(StringUtils::isNotBlank);
    }
}
