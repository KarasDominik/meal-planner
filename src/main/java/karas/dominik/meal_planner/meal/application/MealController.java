package karas.dominik.meal_planner.meal.application;

import karas.dominik.meal_planner.meal.domain.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static karas.dominik.meal_planner.meal.application.RequestMapper.asQuery;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meal")
class MealController {
    
    private final RecipeService service;

    @GetMapping
    GetRecipesResponse getMeals(@RequestParam(required = false) String name) {
        return GetRecipesResponse.of(service.getRecipes(asQuery(name)));
    }
}
