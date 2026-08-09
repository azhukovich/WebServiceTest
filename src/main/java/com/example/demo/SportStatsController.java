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
        List<String> exerciseNames = List.of("Отжимания", "Приседания");

        model.addAttribute("exerciseNames", exerciseNames);
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
                .toList();

        Map<LocalDate, DayResults> grouped = new HashMap<>();

        for (String ex : exerciseNames) {
            sportRepo.findAll().stream()
                    .map(r -> r.setCreatedAt(r.getCreatedAt().minusHours(3)))
                    .filter(r -> {
                        LocalDate d = r.getCreatedAt().toLocalDate();
                        return !d.isBefore(fromDate) && !d.isAfter(today);
                    })
                    .filter(r -> r.getName().equals(ex))
                    .forEach(r -> {
                        LocalDate d = r.getCreatedAt().toLocalDate();
                        grouped.computeIfAbsent(d, k -> new DayResults())
                                .add(ex, r.getQuantity(), r.getComment());
                    });
        }

        // формируем финальную карту с пустыми днями
        Map<LocalDate, DayResults> finalMap = new TreeMap<>(Comparator.reverseOrder());

        for (LocalDate day : days) {
            finalMap.put(day, grouped.getOrDefault(day, new DayResults()));
        }

        model.addAttribute("resultsByDay", finalMap);

        return "sportstats";
    }
}
