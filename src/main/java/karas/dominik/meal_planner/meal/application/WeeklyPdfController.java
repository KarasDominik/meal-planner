package karas.dominik.meal_planner.meal.application;

import karas.dominik.meal_planner.meal.domain.WeeklyPdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static karas.dominik.meal_planner.meal.application.RequestMapper.asQuery;
import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/weekly-pdf")
class WeeklyPdfController {

    private final WeeklyPdfService service;

    @PostMapping(produces = APPLICATION_PDF_VALUE)
    byte[] generate(@RequestBody CreateWeeklyEatingPlanRequest request) {
        return service.generate(asQuery(request));
    }

}
