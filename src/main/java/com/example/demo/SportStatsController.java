package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Controller
public class SportStatsController {

    private final SportRepository sportRepo;

    public SportStatsController(SportRepository sportRepo) {
        this.sportRepo = sportRepo;
    }

    @GetMapping("/sportstats")
    public String sportStats(@RequestParam(defaultValue = "Отжимания") String exercise,
                             @RequestParam(defaultValue = "week") String period,
                             Model model) {

        // список периодов
        List<String> excerciseNames = List.of("Отжимания", "Приседания");

        model.addAttribute("exerciseNames", excerciseNames);
        model.addAttribute("selectedExercise", exercise);

        //Результаты
        LocalDate today = LocalDate.now().plusDays(1);
        LocalDate fromDate;

        if (period.equals("month")) {
            fromDate = LocalDate.now().minusDays(30);
        } else {
            fromDate = LocalDate.now().minusDays(7);
        }

        List<LocalDate> days = fromDate
                .datesUntil(today)
                .collect(Collectors.toList());

        Map<LocalDate, List<Integer>> grouped = sportRepo.findAll().stream()
                .filter(r -> {
                    LocalDate d = r.getCreatedAt().toLocalDate();
                    return !d.isBefore(fromDate) && !d.isAfter(today);
                })
                .filter(r -> r.getName().equals(exercise))
                .collect(Collectors.groupingBy(
                        r -> r.getCreatedAt().toLocalDate(),
                        Collectors.mapping(
                                r-> r.getQuantity(),
                                Collectors.toList()
                        )
                ));

        Map<LocalDate, String> resultStrs = grouped.entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
        e->{
            List<Integer> values = e.getValue();

            String left = values.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining("+"));

            int sum = values.stream()
                    .mapToInt(Integer::intValue)
                    .sum();

            return left + "=" + sum;
        }));

        TreeMap<LocalDate, String> finalMap = days.stream()
                .collect(Collectors.toMap(
                        d -> d,
                        d -> resultStrs.getOrDefault(d, "Нет результатов"),
                        (oldValue, newValue) -> oldValue, // Слияние при совпадении ключей
                        TreeMap::new
                ))
                ;

        model.addAttribute("resultsByDay", finalMap);

        return "sportstats";
    }
}
