package karas.dominik.meal_planner.meal.infrastructure.externalapi.themealdb.exception;

public class FetchingMealsFailedException extends RuntimeException {

    public FetchingMealsFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
