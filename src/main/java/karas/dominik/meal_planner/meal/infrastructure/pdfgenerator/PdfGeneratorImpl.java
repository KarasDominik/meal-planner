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
import karas.dominik.meal_planner.meal.domain.dto.RecipeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

import static com.itextpdf.text.Font.FontFamily.HELVETICA;

@Service
@RequiredArgsConstructor
@Slf4j
class PdfGeneratorImpl implements PdfGenerator {

    @Override
    public byte[] generate(GeneratePdfQuery query) {
        try {
            log.info("Starting to fetch recipes' details for weekly pdf");

            var byteArrayOutputStream = new ByteArrayOutputStream();

            var document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            var table = getPdfPTable(query.recipesPerDay());

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
}
