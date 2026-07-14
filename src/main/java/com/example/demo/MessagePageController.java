package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MessagePageController {

    private final MessageRepository repo;

    public MessagePageController(MessageRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/messages")
    public String messages(Model model) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        List<MessageDto> formattedMessages = repo.findAll().stream()
                .map(msg -> new MessageDto(
                        msg.getText(),
                        msg.getCreatedAt().format(formatter)
                ))
                .toList();

        model.addAttribute("messages", formattedMessages);
//        model.addAttribute("messages", MessageStorage.getAll());
        return "messages";
    }
}
