package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SportFormController {

    @GetMapping("/sportform")
    public String sportForm(Model model) {

        // список названий
        List<String> names = List.of("Отжимания", "Приседания");

        model.addAttribute("names", names);
        return "sportform";
    }
}
