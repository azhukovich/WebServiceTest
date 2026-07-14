package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.stream.Collectors;

@Controller
public class MessagePageController {

    private final MessageRepository repo;

    public MessagePageController(MessageRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/messages")
    public String messages(org.springframework.ui.Model model) {
        model.addAttribute("messages", repo.findAll());
//        model.addAttribute("messages", MessageStorage.getAll());
        return "messages";
    }
}
