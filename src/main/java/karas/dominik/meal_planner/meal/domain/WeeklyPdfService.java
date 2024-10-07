package karas.dominik.meal_planner.meal.domain;

public interface WeeklyPdfService {

    void generate(GenerateWeeklyPdfQuery query);
}
