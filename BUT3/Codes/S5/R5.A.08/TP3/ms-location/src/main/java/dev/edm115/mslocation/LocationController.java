package dev.edm115.mslocation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    @PostMapping
    public Location addLocation(@RequestBody Location location) {
        location.setTimestamp(LocalDateTime.now());
        return locationRepository.save(location);
    }

    @GetMapping("/{userId}")
    public List<Location> getLocationsByUserId(@PathVariable String userId) {
        return locationRepository.findByUserId(userId);
    }

    @GetMapping
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }
}
