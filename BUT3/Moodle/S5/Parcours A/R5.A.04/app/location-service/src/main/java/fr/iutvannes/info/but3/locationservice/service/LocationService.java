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

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    // Create or update a location
    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    // Get all locations
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    // Get a location by ID
    public Optional<Location> getLocationById(Long id) {
        return locationRepository.findById(id);
    }

    // Delete a location by ID
    public void deleteLocationById(Long id) {
        locationRepository.deleteById(id);
    }
}