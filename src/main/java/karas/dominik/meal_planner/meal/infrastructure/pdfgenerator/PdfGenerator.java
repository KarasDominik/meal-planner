package karas.dominik.meal_planner.meal.infrastructure.pdfgenerator;

public interface PdfGenerator {

    byte[] generate(GeneratePdfQuery query);
}
