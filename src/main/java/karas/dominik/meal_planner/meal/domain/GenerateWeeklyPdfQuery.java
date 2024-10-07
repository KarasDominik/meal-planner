package karas.dominik.meal_planner.meal.domain;

import lombok.Builder;

import java.util.Optional;

@Builder
public record GenerateWeeklyPdfQuery(
        Optional<Long> monday,
        Optional<Long> tuesday,
        Optional<Long> wednesday,
        Optional<Long> thursday,
        Optional<Long> friday,
        Optional<Long> saturday,
        Optional<Long> sunday) {
}
