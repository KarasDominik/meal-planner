package karas.dominik.meal_planner.meal.infrastructure.pdfgenerator;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import karas.dominik.meal_planner.common.DayOfTheWeek;
import karas.dominik.meal_planner.meal.domain.GenerateWeeklyPdfQuery;
import karas.dominik.meal_planner.meal.domain.dto.RecipeDto;
import karas.dominik.meal_planner.meal.infrastructure.externalapi.ExternalRecipeProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

import static com.itextpdf.text.Font.FontFamily.HELVETICA;
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

            var document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            var table = getPdfPTable(map);

            document.add(table);
            document.close();

            return byteArrayOutputStream.toByteArray();
        } catch (DocumentException e) {
            log.error("Failed to generate PDF");
            throw new RuntimeException(e);
        }
    }

    private PdfPTable getPdfPTable(Map<DayOfTheWeek, Optional<RecipeDto>> map) throws DocumentException {
        var table = new PdfPTable(3);

        table.setWidthPercentage(100);
        table.setWidths(new float[]{0.1F, 0.7F, 4.3F});

        var font = new Font(HELVETICA, 7);

        map.entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> entry.getKey().ordinal()))
                .forEach(entry -> {
                    var dayCell = new PdfPCell(new Phrase(entry.getKey().name(), font));
                    dayCell.setRotation(90);
                    dayCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    dayCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                    var nameCell = new PdfPCell(new Phrase(entry.getValue().map(RecipeDto::name).orElse("-"), font));
                    var descriptionCell = new PdfPCell(new Phrase(entry.getValue().map(RecipeDto::description).orElse("-"), font));

                    table.addCell(dayCell);
                    table.addCell(nameCell);
                    table.addCell(descriptionCell);
        });

        return table;
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
