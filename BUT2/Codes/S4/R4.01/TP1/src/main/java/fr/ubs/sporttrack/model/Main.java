package fr.ubs.sporttrack.model;

import fr.ubs.sporttrack.model.Activity;
import fr.ubs.sporttrack.model.Data;
import fr.ubs.sporttrack.model.JSONFileReader;
import fr.ubs.sporttrack.model.JSONFileWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please specify the JSON file path as the first argument.");
            return;
        }

        String filePath = args[0];
        File jsonFile = new File(filePath);

        try {
            JSONFileReader reader = new JSONFileReader(jsonFile);
            List<Activity> activities = reader.getActivities();

            for (Activity activity : activities) {
                System.out.println(activity);
            }

            List<Data> newData = new ArrayList<>();
            newData.add(new Data("2024-04-04T12:00:00", 120, 48.8566f, 2.3522f, 35.0f));
            Activity newActivity = new Activity("2024-04-04", "Morning Run in Paris", 5000, 110, 160, newData);
            activities.add(newActivity);

            JSONFileWriter writer = new JSONFileWriter(jsonFile);
            writer.writeData(activities);
            writer.close();

            System.out.println("New data added and saved to file successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while processing the file.");
        }
    }
}
