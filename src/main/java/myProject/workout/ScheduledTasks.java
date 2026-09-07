package myProject.workout;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;

@Component
public class ScheduledTasks {

    private int counter = 0;
    private final int limit = 6;
    private final int RATE = 10;
    private final int HOURS = 1; // сколько часов ждать

    private final RestTemplate restTemplate = new RestTemplate();
    private final VisitTracker visitTracker;

    public ScheduledTasks(VisitTracker visitTracker) {
        this.visitTracker = visitTracker;
    }

    @Scheduled(fixedRate = RATE*60*1000)
    public void taskOne() {

        Instant lastVisit = visitTracker.getLastVisit();
        long minutesPassed = Duration.between(lastVisit, Instant.now()).toMinutes();

//        if (counter >= limit) {
        if (minutesPassed >= HOURS * 60) {
            return; // просто перестаём выполнять
        }

        counter++;

//        System.out.println("Task is executed each "+RATE+" minutes "+ limit + " times. This is run: "+counter);
        System.out.println("Task is executed each "+RATE+" minutes within 1 hour from start or last visit. This is run: "+counter);
        String response = restTemplate.getForObject(
                "https://webservicetest-y5wj.onrender.com/sportform",
                String.class
        );
//        System.out.println(response);
    }
}
