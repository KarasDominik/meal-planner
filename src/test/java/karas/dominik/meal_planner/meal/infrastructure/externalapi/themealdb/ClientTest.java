package karas.dominik.meal_planner.meal.infrastructure.externalapi.themealdb;

import com.fasterxml.jackson.databind.ObjectMapper;
import karas.dominik.meal_planner.meal.domain.SearchRecipeQuery;
import karas.dominik.meal_planner.meal.domain.dto.RecipeDto;
import karas.dominik.meal_planner.meal.infrastructure.externalapi.themealdb.exception.FetchingMealsFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static karas.dominik.meal_planner.common.TestUtil.fetchJsonFrom;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ClientTest {

    @Mock
    private HttpClient httpClient;

    @InjectMocks
    private Client client;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        client = new Client(httpClient, objectMapper);
    }

    @Nested
    class FetchMealsTests {

        @Test
        void shouldReturnRecipesReceivedFromApi() throws IOException {
            // given
            var jsonApiResponse = fetchJsonFrom("src/test/resources/data/json-api-responses/exemplary-valid-response.json");
            var expectedRecipes = objectMapper.readValue(jsonApiResponse, TheMealDbApiResponse.class).meals().stream()
                    .map(TheMealDbApiResponse.Recipe::asDto)
                    .toList();

            var httpResponse = (HttpResponse<String>) mock(HttpResponse.class);
            when(httpResponse.body()).thenReturn(jsonApiResponse);

            var responseFuture = CompletableFuture.completedFuture(httpResponse);
            when(httpClient.sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(responseFuture);

            var query = new SearchRecipeQuery(Optional.empty());

            // when
            List<RecipeDto> recipes = client.getExternalRecipes(query);

            // then
            assertThat(recipes).size().isEqualTo(1);
            assertThat(recipes).isEqualTo(expectedRecipes);
        }

        @Test
        void shouldThrowCustomExceptionWhenApiRequestFails() {
            // given
            doThrow(IllegalArgumentException.class).when(httpClient).sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));

            var query = new SearchRecipeQuery(Optional.empty());

            // when - then
            assertThatThrownBy(() -> client.getExternalRecipes(query))
                    .isInstanceOf(FetchingMealsFailedException.class);
        }

        @Test
        void shouldThrowWhenInvalidResponseIsSent() {
            // given
            var jsonApiResponse = """
                    {
                        "value": "some invalid content"
                    }
                    """;

            var httpResponse = (HttpResponse<String>) mock(HttpResponse.class);
            when(httpResponse.body()).thenReturn(jsonApiResponse);

            var responseFuture = CompletableFuture.completedFuture(httpResponse);
            when(httpClient.sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(responseFuture);

            var query = new SearchRecipeQuery(Optional.empty());

            // when - then
            assertThatThrownBy(() -> client.getExternalRecipes(query))
                    .isInstanceOf(FetchingMealsFailedException.class);
        }
    }

    @Nested
    class FetchMealByIdTests {

        @Test
        void shouldReturnRecipeReceivedFromApi() throws IOException, InterruptedException {
            // given
            var jsonApiResponse = fetchJsonFrom("src/test/resources/data/json-api-responses/exemplary-valid-response.json");
            var expectedRecipe = objectMapper.readValue(jsonApiResponse, TheMealDbApiResponse.class).meals().stream()
                    .map(TheMealDbApiResponse.Recipe::asDto)
                    .findFirst()
                    .orElseThrow();

            var httpResponse = (HttpResponse<String>) mock(HttpResponse.class);
            when(httpResponse.body()).thenReturn(jsonApiResponse);

            when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

            // when
            var recipe = client.getExternalRecipe(1000L);

            // then
            assertThat(recipe).isEqualTo(expectedRecipe);
        }

        @Test
        void shouldThrowCustomExceptionWhenApiRequestFails() throws IOException, InterruptedException {
            // given
            doThrow(IllegalArgumentException.class)
                    .when(httpClient).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));

            // when - then
            assertThatThrownBy(() -> client.getExternalRecipe(100L))
                    .isInstanceOf(FetchingMealsFailedException.class);
        }

        @Test
        void shouldThrowWhenInvalidResponseIsSent() throws IOException, InterruptedException {
            // given
            var jsonApiResponse = """
                    {
                        "value": "some invalid content"
                    }
                    """;

            var httpResponse = (HttpResponse<String>) mock(HttpResponse.class);
            when(httpResponse.body()).thenReturn(jsonApiResponse);

            when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

            // when - then
            assertThatThrownBy(() -> client.getExternalRecipe(100L))
                    .isInstanceOf(FetchingMealsFailedException.class);
        }

        @Test
        void shouldThrowWhenRecipeNotFound() throws IOException, InterruptedException {
            // given
            var jsonApiResponse = """
                    {
                        "meals": null
                    }
                    """;

            var httpResponse = (HttpResponse<String>) mock(HttpResponse.class);
            when(httpResponse.body()).thenReturn(jsonApiResponse);

            when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

            // when - then
            assertThatThrownBy(() -> client.getExternalRecipe(100L))
                    .isInstanceOf(FetchingMealsFailedException.class);
        }
    }
}
