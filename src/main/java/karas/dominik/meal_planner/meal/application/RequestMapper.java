package karas.dominik.meal_planner.meal.application;

import karas.dominik.meal_planner.meal.domain.CreateWeeklyEatingPlanQuery;
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

    static CreateWeeklyEatingPlanQuery asQuery(CreateWeeklyEatingPlanRequest request) {
        return CreateWeeklyEatingPlanQuery.builder()
                .monday(request.monday())
                .tuesday(request.tuesday())
                .wednesday(request.wednesday())
                .thursday(request.thursday())
                .friday(request.friday())
                .saturday(request.saturday())
                .sunday(request.sunday())
                .build();
    }
}
