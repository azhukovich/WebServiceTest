package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class SportStatsController {

    private final SportRepository sportRepo;

    private static final Logger log = LoggerFactory.getLogger(SportDetailsController.class);
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

        record SportData(Integer quant, String comment) {};
        Map<LocalDate, List<SportData>> grouped = sportRepo.findAll().stream()
                .map(r->r.setCreatedAt(r.getCreatedAt().minusHours(3)))
                .filter(r -> {
                    LocalDate d = r.getCreatedAt().toLocalDate();
                    return !d.isBefore(fromDate) && !d.isAfter(today);
                })
                .filter(r -> r.getName().equals(exercise))
                .collect(Collectors.groupingBy(
                        r -> r.getCreatedAt().toLocalDate(),
                        Collectors.mapping(
                                r -> new SportData(r.getQuantity(), r.getComment()),
                                Collectors.toList()
                        )
                ));

//        grouped.forEach((date, data) -> {
//            log.info("grouped" + date + " -> " + data.get(0).quant + " | " + data.get(0).comment());
//        });

        record SportData2(String quantities, String comments) {}
        Map<LocalDate, SportData2> resultStrs = grouped.entrySet().stream()
                        .collect(Collectors.toMap(
                                e -> e.getKey(),
        e->{
            List<SportData> values = e.getValue();

            String left = values.stream()
                    .map(f->f.quant().toString())
                    .collect(Collectors.joining("+"));

            int sum = values.stream()
                    .mapToInt(d -> d.quant)
                    .sum();

            String comments = values.stream()
                    .map(v -> v.comment())
                    .filter(c -> c != null && !c.isBlank())
                    .collect(Collectors.joining("; "));

            return new SportData2(left + "=" + sum, comments);
        }));

//        resultStrs.forEach((date, data) -> {
//            log.info("resultStrs" + date + " -> " + data.quantities() + " | " + data.comments());
//        });

        TreeMap<LocalDate, SportData2> finalMap = days.stream()
                .collect(Collectors.toMap(
                        d -> d,
                        d -> resultStrs.getOrDefault(
                                d,
                                new SportData2("Нет результатов", "")
                        ),
                        (oldValue, newValue) -> oldValue, // Слияние при совпадении ключей
                        () -> new TreeMap<LocalDate, SportData2>(Comparator.reverseOrder())
                ));

//        finalMap.forEach((date, data) -> {
//            log.info("finalMap" + date + " -> " + data.quantities() + " | " + data.comments());
//        });

        model.addAttribute("resultsByDay", finalMap);


        //To color results in table
        Map<LocalDate, String> colors = new HashMap<>();
        for (Map.Entry<LocalDate, SportData2> entry : finalMap.entrySet()) {
            String result = entry.getValue().quantities;

            String colorClass;

            if (result.equals("Нет результатов")) {
                colorClass = "red";
            } else {
                // результат вида "1+10+15+13+15+20+13+13=100"
                String[] parts = result.split("=");
                int sum = Integer.parseInt(parts[1]);

                if (sum > 95) {
                    colorClass = "green";
                } else if (sum >= 50) {
                    colorClass = "yellow";
                } else if (sum >= 10) {
                    colorClass = "blue";
                }
                else {
                    colorClass = "red";
                }
            }

            colors.put(entry.getKey(), colorClass);
        }

        model.addAttribute("colors", colors);

        return "sportstats";
    }
}
