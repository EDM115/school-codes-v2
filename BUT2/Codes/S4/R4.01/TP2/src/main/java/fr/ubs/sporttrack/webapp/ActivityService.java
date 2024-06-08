package fr.ubs.sporttrack.webapp;

import fr.ubs.sporttrack.model.Activity;
import fr.ubs.sporttrack.model.JSONFileReader;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ActivityService {

    private List<Activity> activities;

    public ActivityService() {
        try {
            File file = new File(getClass().getClassLoader().getResource("data.json").getFile());
            JSONFileReader reader = new JSONFileReader(file);
            this.activities = reader.getActivities();
        } catch (IOException e) {
            e.printStackTrace();
            this.activities = List.of();
        }
    }

    public List<Activity> findAll() {
        return this.activities;
    }

    public List<Activity> search(String keyword) {
        return this.activities.stream()
                .filter(activity -> activity.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}
