package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Controller
public class SportDetailsController {

    private static final Logger log = LoggerFactory.getLogger(SportDetailsController.class);
    private final SportRepository sportRepo;

    public SportDetailsController(SportRepository sportRepo) {
        this.sportRepo = sportRepo;
    }

    @GetMapping("/sportdetails")
    public String sportStats(@RequestParam(defaultValue = "Отжимания") String exercise,
                             @RequestParam(required = true) String day,
                             Model model) {


        LocalDate selectedDay = LocalDate.parse(day);
        log.info("Зырим лог 1");
        record SportData(Integer quant, String comment) {};
        Map<LocalDateTime, List<SportData>> grouped = sportRepo.findAll().stream()
                .peek(r->r.setCreatedAt(r.getCreatedAt().minusHours(3)))
                .filter(r -> {
                    LocalDate d = r.getCreatedAt().toLocalDate();
                    return d.isEqual(selectedDay);
                })
                .filter(r -> r.getName().equals(exercise))
                .collect(Collectors.groupingBy(
                        SportResult::getCreatedAt,
                        Collectors.mapping(
                                r -> new SportData(r.getQuantity(), r.getComment()),
                                Collectors.toList()
                        )
                ));

        log.info("Зырим лог 2");
        record SportData2(String quantities, String comments) {}
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        TreeMap<String, SportData2> finalMap = grouped.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().plusHours(3).format(formatter), // ключ
                        e -> {                                           // значение
                            SportData d = e.getValue().get(0);
                            return new SportData2(d.quant().toString(), d.comment());
                        },
                        (a, b) -> a,                                     // merge function
                        TreeMap::new
                ))
                ;
        log.info("Зырим лог 3");
        finalMap.forEach((date, data) -> {
            log.info(date + " -> " + data.quantities() + " | " + data.comments());
        });

        model.addAttribute("resultsByDay", finalMap);

         return "sportdetails";

    }
}
