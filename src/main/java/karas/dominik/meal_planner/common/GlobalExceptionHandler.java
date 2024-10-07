package karas.dominik.meal_planner.common;

import karas.dominik.meal_planner.meal.infrastructure.externalapi.themealdb.exception.FetchingMealsFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FetchingMealsFailedException.class)
    public void handle() {}
}
