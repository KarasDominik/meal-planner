package karas.dominik.meal_planner.meal.infrastructure.pdfgenerator;

import karas.dominik.meal_planner.meal.domain.GenerateWeeklyPdfQuery;
import karas.dominik.meal_planner.meal.infrastructure.externalapi.ExternalRecipeProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
class PdfGeneratorImpl implements PdfGenerator {

    private final ExternalRecipeProvider externalRecipeProvider;

    @Override
    public void generate(GenerateWeeklyPdfQuery query) {
        log.info("Starting to fetch recipes' details for weekly pdf");
        var mondayRecipe = query.monday().map(externalRecipeProvider::getExternalRecipe);
        System.out.println(mondayRecipe);
    }
}
