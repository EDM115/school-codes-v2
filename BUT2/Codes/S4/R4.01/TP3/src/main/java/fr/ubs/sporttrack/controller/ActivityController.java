package fr.ubs.sporttrack.controller;

import fr.ubs.sporttrack.model.Activity;
import fr.ubs.sporttrack.model.JSONFileReader;
import fr.ubs.sporttrack.model.JSONFileWriter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    private List<Activity> activities;
    private File file = new File(getClass().getClassLoader().getResource("data.json").getFile());
    private JSONFileWriter writer;

    public ActivityController() {
        try {
            JSONFileReader reader = new JSONFileReader(file);
            this.activities = reader.getActivities();
        } catch (IOException e) {
            e.printStackTrace();
            this.activities = List.of();
        }

    }
    
    @GetMapping("/")
    public List<Activity> findAll() {
        return this.activities;
    }
    
    @GetMapping("/{keyword}")
    public List<Activity> findByKeyword(@PathVariable("keyword") String keyword) {
        return this.activities.stream()
        .filter(activity -> activity.getDescription().toLowerCase().contains(keyword.toLowerCase()))
        .collect(Collectors.toList());
    }

    @PostMapping(path="/", consumes="application/json", produces="application/json")
    public Activity addActivity(@RequestBody Activity activity) throws IOException {
        this.activities.add(activity);
        this.writer = new JSONFileWriter(file);
        this.writer.writeData(this.activities);
        this.writer.close();
        return activity;
    }
}
