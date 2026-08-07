package com.example.demo;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ScheduledTasks {

    private int counter = 0;
    private final int limit = 4;

    private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 14*60*1)
    public void taskOne() {

        if (counter >= limit) {
            return; // просто перестаём выполнять
        }

        counter++;

        System.out.println("Task is executed each 14 minutes "+ limit + " times");
        String response = restTemplate.getForObject(
                "https://webservicetest-y5wj.onrender.com/sportform",
                String.class
        );
//        System.out.println(response);
    }
}
