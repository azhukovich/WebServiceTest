package myProject.workout;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

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
        SportResult sportResult = new SportResult(dto.getName(), dto.getQuantity(), dto.getComment());
        sportRepo.save(sportResult);
        return "Saved: " + dto.getName() + " quantity: " + dto.getQuantity() + "comment" + dto.getComment();
    }

    @GetMapping("/sport/stats")
    public Map<String, Object> getStats(@RequestParam String name,
                                        @RequestParam String from,
                                        @RequestParam String to,
                                        @RequestParam Integer shiftHours) {

        LocalDateTime fromDate = LocalDate.parse(from).atStartOfDay();
        LocalDateTime toDate = LocalDate.parse(to).plusDays(1).atStartOfDay();

        int total = sportRepo.findAll().stream()
                .filter(s -> s.getName().equals(name))
                .map(s->s.setCreatedAt(s.getCreatedAt().plusHours(shiftHours)))
                .filter(s -> !s.getCreatedAt().isBefore(fromDate))
                .filter(s -> s.getCreatedAt().isBefore(toDate))
                .mapToInt(SportResult::getQuantity)
                .sum();

        return Map.of(
                "name", name,
                "total", total,
                "from", from,
                "to", to
        );
    }
}
