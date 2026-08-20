package myProject.workout;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DataController {

    private final MessageRepository repo;

    public DataController(MessageRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/data")
    public String receiveData(@RequestBody String body) {
//        MessageStorage.add(body);
//        System.out.println("Received: " + body);
        repo.save(new Message(body));
        return "Saved: " + body;
    }
}
