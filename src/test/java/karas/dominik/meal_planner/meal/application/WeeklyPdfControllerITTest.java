package karas.dominik.meal_planner.meal.application;

import io.restassured.RestAssured;
import karas.dominik.meal_planner.meal.infrastructure.externalapi.ExternalRecipeProvider;
import karas.dominik.meal_planner.meal.infrastructure.externalapi.themealdb.FakeClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static karas.dominik.meal_planner.common.PdfUtils.extractTextFromPdf;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WeeklyPdfControllerITTest {

    private static final String PATH = "/api/v1/weekly-pdf";

    private static final String PDF = "src/test/resources/data/pdf/test-pdf.pdf";

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    void shouldGeneratePdf() throws IOException {
        // given
        var expectedPdf = extractTextFromPdf(PDF);

        // when
        var response =
                given()
                        .contentType(JSON)
                        .body("""
                            {
                                "monday": 1500
                            }
                            """)
                .when()
                        .post(PATH)
                .then()
                        .statusCode(200)
                        .extract()
                        .asByteArray();

        // then
        assertThat(extractTextFromPdf(response)).isEqualTo(expectedPdf);
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        @Primary
        public ExternalRecipeProvider fakeExternalRecipeProvider() {
            return new FakeClient();
        }
    }
}
