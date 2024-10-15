package karas.dominik.meal_planner.meal.infrastructure.pdfgenerator;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfWriter;
import karas.dominik.meal_planner.common.DayOfTheWeek;
import karas.dominik.meal_planner.meal.domain.GenerateWeeklyPdfQuery;
import karas.dominik.meal_planner.meal.domain.dto.RecipeDto;
import karas.dominik.meal_planner.meal.infrastructure.externalapi.ExternalRecipeProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.Optional;

import static karas.dominik.meal_planner.common.DayOfTheWeek.FRIDAY;
import static karas.dominik.meal_planner.common.DayOfTheWeek.MONDAY;
import static karas.dominik.meal_planner.common.DayOfTheWeek.SATURDAY;
import static karas.dominik.meal_planner.common.DayOfTheWeek.SUNDAY;
import static karas.dominik.meal_planner.common.DayOfTheWeek.THURSDAY;
import static karas.dominik.meal_planner.common.DayOfTheWeek.TUESDAY;
import static karas.dominik.meal_planner.common.DayOfTheWeek.WEDNESDAY;

@Service
@RequiredArgsConstructor
@Slf4j
class PdfGeneratorImpl implements PdfGenerator {

    private final ExternalRecipeProvider externalRecipeProvider;

    @Override
    public byte[] generate(GenerateWeeklyPdfQuery query) {
        try {
            log.info("Starting to fetch recipes' details for weekly pdf");
            var map = fetchRecipes(query);

            var byteArrayOutputStream = new ByteArrayOutputStream();

            var document = new Document();
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            var font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            var chunk = new Chunk("Hello World", font);

            document.add(chunk);
            document.close();

            return byteArrayOutputStream.toByteArray();
        } catch (DocumentException e) {
            log.error("Failed to generate PDF");
            throw new RuntimeException(e);
        }
    }

    private Map<DayOfTheWeek, Optional<RecipeDto>> fetchRecipes(GenerateWeeklyPdfQuery query) {
        var mondayRecipe = query.monday().map(externalRecipeProvider::getExternalRecipe);
        var tuesdayRecipe = query.tuesday().map(externalRecipeProvider::getExternalRecipe);
        var wednesdayRecipe = query.wednesday().map(externalRecipeProvider::getExternalRecipe);
        var thursdayRecipe = query.thursday().map(externalRecipeProvider::getExternalRecipe);
        var fridayRecipe = query.friday().map(externalRecipeProvider::getExternalRecipe);
        var saturdayRecipe = query.saturday().map(externalRecipeProvider::getExternalRecipe);
        var sundayRecipe = query.sunday().map(externalRecipeProvider::getExternalRecipe);
        return Map.of(
                MONDAY, mondayRecipe,
                TUESDAY, tuesdayRecipe,
                WEDNESDAY, wednesdayRecipe,
                THURSDAY, thursdayRecipe,
                FRIDAY, fridayRecipe,
                SATURDAY, saturdayRecipe,
                SUNDAY, sundayRecipe
        );
    }
}
