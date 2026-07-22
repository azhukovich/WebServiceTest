package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Controller
public class SportDetailsController {

    private final SportRepository sportRepo;

    public SportDetailsController(SportRepository sportRepo) {
        this.sportRepo = sportRepo;
    }

    @GetMapping("/sportdetails")
    public String sportStats(@RequestParam(defaultValue = "Отжимания") String exercise,
                             @RequestParam(required = true) String day,
                             Model model) {


        LocalDate selectedDay = LocalDate.parse(day);

        Map<LocalDateTime, List<Integer>> grouped = sportRepo.findAll().stream()
                .filter(r -> {
                    LocalDate d = r.getCreatedAt().toLocalDate();
                    return d.isEqual(selectedDay);
                })
                .filter(r -> r.getName().equals(exercise))
                .collect(Collectors.groupingBy(
                        r -> r.getCreatedAt(),
                        Collectors.mapping(
                                r-> r.getQuantity(),
                                Collectors.toList()
                        )
                ));


        TreeMap<LocalDateTime, String> finalMap = grouped.entrySet().stream()
                .collect(Collectors.toMap(
                        d -> d.getKey(),
                        d -> d.getValue().toString(),
                        (oldValue, newValue) -> oldValue, // Слияние при совпадении ключей
                        TreeMap::new
                ))
                ;

        model.addAttribute("resultsByDay", finalMap);

        return "sportdetails";
    }
}
