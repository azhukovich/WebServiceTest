package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Controller
public class SportFormController {

    private final SportRepository sportRepo;

    public SportFormController(SportRepository sportRepo) {
        this.sportRepo = sportRepo;
    }

    @GetMapping("/sportform")
    public String sportForm(Model model) {

        // список названий
        List<String> names = List.of("Отжимания", "Приседания");

        model.addAttribute("names", names);

        //Результаты за неделю
        LocalDate weekAgo = LocalDate.now().minusDays(7);
        LocalDate today = LocalDate.now().plusDays(1);

        List<LocalDate> days = weekAgo
                .datesUntil(today.plusDays(1))
                .collect(Collectors.toList());

        //Получить последний результат для каждого упражнения
        Map<String, LocalDateTime> exerciseTimes = sportRepo.findAll().stream()
                .collect(Collectors.toMap(
                        SportResult::getName,
                        SportResult::getCreatedAt,
                        (time1,time2) -> time1.isAfter(time2) ? time1:time2
                        ));


        Map<LocalDate, List<Integer>> grouped = sportRepo.findAll().stream()
                .map(r->r.setCreatedAt(r.getCreatedAt().minusHours(3)))
                .filter(r -> {
                    LocalDate d = r.getCreatedAt().toLocalDate();
                    return !d.isBefore(weekAgo) && !d.isAfter(today);
                })
                .collect(Collectors.groupingBy(
                        r -> r.getCreatedAt().toLocalDate(),
                        Collectors.mapping(
                                SportResult::getQuantity,
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
        model.addAttribute("exerciseTimes", exerciseTimes);

        return "sportform";
    }
}
