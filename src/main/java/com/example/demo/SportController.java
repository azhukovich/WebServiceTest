package com.example.demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SportController {

    private final SportRepository sportRepo;

    public SportController(SportRepository sportRepo) {
        this.sportRepo = sportRepo;
    }

    @PostMapping("/sport")
    public String receiveSportData(@RequestBody SportDto dto) {
//        MessageStorage.add(body);
//        System.out.println("Received: " + body);
        SportResult sportResult = new SportResult(dto.getName(), dto.getQuantity());
        sportRepo.save(sportResult);
        return "Saved: " + dto.getName() + " quantity: " + dto.getQuantity();
    }
}
