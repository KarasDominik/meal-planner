package karas.dominik.meal_planner.meal.application;

import karas.dominik.meal_planner.meal.domain.WeeklyPdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static karas.dominik.meal_planner.meal.application.RequestMapper.asQuery;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/weekly-pdf")
class WeeklyPdfController {

    private final WeeklyPdfService service;

    @PostMapping
    void generate(@RequestBody GenerateWeeklyPdfRequest request) {
        service.generate(asQuery(request));
    }

}
