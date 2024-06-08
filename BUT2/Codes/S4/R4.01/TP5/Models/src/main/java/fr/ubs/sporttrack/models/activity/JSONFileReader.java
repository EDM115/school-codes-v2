package fr.ubs.sporttrack.models.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONFileReader {
    private List<Activity> activities;

    /**
     * Parses the JSON content of the file and stores the objects
     * in a list of objects of type <code>Activity</code>.
     * @param jsonData The JSON content of the file.
     */
    private void parseActivities(String jsonData) {
        JSONArray activitiesArray = new JSONArray(jsonData);
        for (int i = 0; i < activitiesArray.length(); i++) {
            JSONObject activityObject = activitiesArray.getJSONObject(i);
            Activity activity = Activity.fromJSON(activityObject);
            this.activities.add(activity);
        }
    }

    /**
     * Creates a JSONFileReader object that opens and reads the file
     * specified as parameter, and stores the content in a list of
     * objects of type <code>Activity</code>.
     * @param f The file that must be read.
     * @exception IOException if the file does not exist or cannot be
     * read.
     */
    public JSONFileReader(File f) throws IOException {
        this.activities = new ArrayList<>();
        StringBuilder jsonContent = new StringBuilder();
        try (Scanner scanner = new Scanner(f)) {
            while (scanner.hasNextLine()) {
                jsonContent.append(scanner.nextLine());
            }
            parseActivities(jsonContent.toString());
        } catch (FileNotFoundException e) {
            throw new IOException("File not found: " + f.getAbsolutePath(), e);
        }
    }

    /**
     * Returns a list of objects of type <code>JSONObject</code> that have
     * been read from the file.
     * @return a list of objects of type <code>JSONObject</code>.
     */
    public List<Activity> getActivities() {
        return this.activities;
    }
}
