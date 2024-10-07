package karas.dominik.meal_planner.meal.application;

import karas.dominik.meal_planner.meal.domain.RecipeService;
import karas.dominik.meal_planner.meal.domain.SearchRecipeQuery;
import karas.dominik.meal_planner.meal.domain.dto.RecipeDto;
import karas.dominik.meal_planner.meal.infrastructure.externalapi.themealdb.exception.FetchingMealsFailedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MealController.class)
class MealControllerWebTest {

    private static final String MEAL_API_PATH = "/api/v1/meal";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService service;

    @Test
    void shouldReturnFoundRecipes() throws Exception {
        // given
        var query = SearchRecipeQuery.builder()
                        .build();
        var recipes = List.of(RecipeDto.builder()
                .id(5L)
                .name("Test recipe")
                .description("Test description")
                .ingredients(List.of(
                        new RecipeDto.Ingredient("Test ingredient", "50g"),
                        new RecipeDto.Ingredient("Test ingredient 2", "50g"),
                        new RecipeDto.Ingredient("Test ingredient 3", "50g")
                ))
                .build());
        when(service.getRecipes(query)).thenReturn(recipes);

        // when - then
        mockMvc.perform(get(MEAL_API_PATH))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.recipes.length()").value(recipes.size()))

                .andExpect(jsonPath("$.recipes[0].id").value(5L))
                .andExpect(jsonPath("$.recipes[0].name").value("Test recipe"))
                .andExpect(jsonPath("$.recipes[0].description").value("Test description"))

                .andExpect(jsonPath("$.recipes[0].ingredients.length()").value(3))

                .andExpect(jsonPath("$.recipes[0].ingredients[0].name").value("Test ingredient"))
                .andExpect(jsonPath("$.recipes[0].ingredients[0].measurement").value("50g"))

                .andExpect(jsonPath("$.recipes[0].ingredients[1].name").value("Test ingredient 2"))
                .andExpect(jsonPath("$.recipes[0].ingredients[1].measurement").value("50g"))

                .andExpect(jsonPath("$.recipes[0].ingredients[2].name").value("Test ingredient 3"))
                .andExpect(jsonPath("$.recipes[0].ingredients[2].measurement").value("50g"));
    }

    @Test
    void shouldReturn500WhenClientThrowsException() throws Exception {
        // given
        when(service.getRecipes(any())).thenThrow(new FetchingMealsFailedException("Fetching meals failed", new RuntimeException()));


        mockMvc.perform(get(MEAL_API_PATH))
                .andExpect(status().isInternalServerError());
    }
}
