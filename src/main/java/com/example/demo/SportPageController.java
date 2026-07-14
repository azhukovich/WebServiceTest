package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class SportPageController {

    private final SportRepository repo;

    public SportPageController(SportRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/sport")
    public String sportData(Model model) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        List<SportDto> formattedSportData = repo.findAll().stream()
                .map(msg -> new SportDto(
                        msg.getName(),
                        msg.getQuantity(),
                        msg.getCreatedAt().format(formatter)
                ))
                .toList();

        model.addAttribute("sportData", formattedSportData);
//        model.addAttribute("messages", MessageStorage.getAll());
        return "sportData";
    }
}
