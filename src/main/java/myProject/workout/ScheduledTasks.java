package myProject.workout;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ScheduledTasks {

    private int counter = 0;
    private final int limit = 6;
    private final int RATE = 10;

    private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = RATE*60*1000)
    public void taskOne() {

        if (counter >= limit) {
            return; // просто перестаём выполнять
        }

        counter++;

        System.out.println("Task is executed each "+RATE+" minutes "+ limit + " times. This is run: "+counter);
        String response = restTemplate.getForObject(
                "https://webservicetest-y5wj.onrender.com/sportform",
                String.class
        );
//        System.out.println(response);
    }
}
