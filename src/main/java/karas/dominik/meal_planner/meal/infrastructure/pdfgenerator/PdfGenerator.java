package karas.dominik.meal_planner.meal.infrastructure.pdfgenerator;

import karas.dominik.meal_planner.meal.domain.GenerateWeeklyPdfQuery;

public interface PdfGenerator {

    void generate(GenerateWeeklyPdfQuery query);
}
