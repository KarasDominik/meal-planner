package karas.dominik.meal_planner.meal.domain;

public interface WeeklyPdfService {

    byte[] generate(GenerateWeeklyPdfQuery query);
}
