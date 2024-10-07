package karas.dominik.meal_planner.meal.infrastructure.externalapi.themealdb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import karas.dominik.meal_planner.meal.domain.SearchRecipeQuery;
import karas.dominik.meal_planner.meal.domain.dto.RecipeDto;
import karas.dominik.meal_planner.meal.infrastructure.externalapi.ExternalRecipeProvider;
import karas.dominik.meal_planner.meal.infrastructure.externalapi.themealdb.TheMealDbApiResponse.Recipe;
import karas.dominik.meal_planner.meal.infrastructure.externalapi.themealdb.exception.FetchingMealsFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class Client implements ExternalRecipeProvider {

    private static final String FETCH_MEALS_URL = "https://www.themealdb.com/api/json/v1/1/search.php?s=%s";
    private static final String FETCH_MEAL_URL = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=%s";

    private final HttpClient httpClient;
    private final ObjectMapper mapper;

    @Override
    public List<RecipeDto> getExternalRecipes(SearchRecipeQuery query) {
        return fetchMealsAsync(query).meals().stream()
                    .map(Recipe::asDto)
                    .toList();
    }

    @Override
    public RecipeDto getExternalRecipe(Long recipeId) {
        return fetchMealById(recipeId)
                .asDto();
    }

    private TheMealDbApiResponse.Recipe fetchMealById(Long recipeId) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(String.format(FETCH_MEAL_URL, recipeId)))
                .build();

        try {
            return asApiRecipeResponse(httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body());
        } catch (Exception e) {
            throw new FetchingMealsFailedException("Failed to fetch meals", e);
        }
    }

    private TheMealDbApiResponse fetchMealsAsync(SearchRecipeQuery query) {
        return sendAsyncRequest(createRequest(query.recipeName()));
    }

    private TheMealDbApiResponse sendAsyncRequest(HttpRequest request) {
        try {
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(this::asApiResponse)
                    .get();
        } catch (Exception e) {
            throw new FetchingMealsFailedException("Failed to fetch meals", e);
        }
    }

    private HttpRequest createRequest(Optional<String> recipeName) {
        return HttpRequest.newBuilder()
                .uri(URI.create(String.format(FETCH_MEALS_URL, recipeName.orElse(""))))
                .build();
    }

    private TheMealDbApiResponse asApiResponse(String body) {
        try {
            return mapper.readValue(body, TheMealDbApiResponse.class);
        } catch (JsonProcessingException e) {
            throw new FetchingMealsFailedException("Failed to map API response into TheMealDbApiResponse", e);
        }
    }

    private TheMealDbApiResponse.Recipe asApiRecipeResponse(String body) {
        try {
            return mapper.readValue(body, TheMealDbApiResponse.Recipe.class);
        } catch (JsonProcessingException e) {
            throw new FetchingMealsFailedException("Failed to map API response into TheMealDbApiResponse.Recipe", e);
        }
    }
}
