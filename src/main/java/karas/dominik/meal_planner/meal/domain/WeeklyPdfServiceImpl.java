package karas.dominik.meal_planner.meal.domain;

import karas.dominik.meal_planner.meal.infrastructure.pdfgenerator.PdfGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeeklyPdfServiceImpl implements WeeklyPdfService {

    private final PdfGenerator generator;

    @Override
    public byte[] generate(GenerateWeeklyPdfQuery query) {
        return generator.generate(query);
    }
}
