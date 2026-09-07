package myProject.workout;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class VisitTracker {
    private Instant lastVisit = Instant.now();

    public void updateVisit() {
        lastVisit = Instant.now();
    }

    public Instant getLastVisit() {
        return lastVisit;
    }
}
