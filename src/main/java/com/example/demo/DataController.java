package com.example.demo;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DataController {

    @PostMapping("/data")
    public String receiveData(@RequestBody String body) {
        MessageStorage.add(body);
        System.out.println("Received: " + body);
        return "OK: " + body;
    }
}
