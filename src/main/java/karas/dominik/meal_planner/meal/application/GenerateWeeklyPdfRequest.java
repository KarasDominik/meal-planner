package karas.dominik.meal_planner.meal.application;

import java.util.Optional;

record GenerateWeeklyPdfRequest(
        Optional<Long> monday,
        Optional<Long> tuesday,
        Optional<Long> wednesday,
        Optional<Long> thursday,
        Optional<Long> friday,
        Optional<Long> saturday,
        Optional<Long> sunday) {}
