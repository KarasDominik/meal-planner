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
        var tuesdayRecipe = query.tuesday().map(externalRecipeProvider::getExternalRecipe);
        var wednesdayRecipe = query.wednesday().map(externalRecipeProvider::getExternalRecipe);
        var thursdayRecipe = query.thursday().map(externalRecipeProvider::getExternalRecipe);
        var fridayRecipe = query.friday().map(externalRecipeProvider::getExternalRecipe);
        var saturdayRecipe = query.saturday().map(externalRecipeProvider::getExternalRecipe);
        var sundayRecipe = query.sunday().map(externalRecipeProvider::getExternalRecipe);
        log.info("Finished");

    }
}
