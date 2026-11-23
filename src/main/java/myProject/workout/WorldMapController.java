package myProject.workout;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WorldMapController {

    @GetMapping("/worldmap")
    public String worldmap() {
        return "worldmap";
    }
}

