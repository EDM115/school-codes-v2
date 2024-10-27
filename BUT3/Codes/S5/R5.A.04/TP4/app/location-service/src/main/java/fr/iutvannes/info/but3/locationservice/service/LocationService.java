package fr.iutvannes.info.but3.locationservice.service;

import fr.iutvannes.info.but3.locationservice.entity.Location;
import fr.iutvannes.info.but3.locationservice.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    private void simulateSlowService() {
        if (Math.random() < 0.5) { // 50% des requêtes seront ralenties
            try {
                Thread.sleep(61000); // Pause de 61 secondes pour forcer le timeout
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
        simulateSlowService();
    }

    // Create or update a location
    public Location saveLocation(Location location) {
        simulateSlowService();
        return locationRepository.save(location);
    }

    // Get all locations
    public List<Location> getAllLocations() {
        simulateSlowService();
        return locationRepository.findAll();
    }

    // Get a location by ID
    public Optional<Location> getLocationById(Long id) {
        simulateSlowService();
        return locationRepository.findById(id);
    }

    // Delete a location by ID
    public void deleteLocationById(Long id) {
        simulateSlowService();
        locationRepository.deleteById(id);
    }
}
