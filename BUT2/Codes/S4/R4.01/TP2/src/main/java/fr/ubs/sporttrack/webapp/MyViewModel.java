package fr.ubs.sporttrack.webapp;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import java.util.List;

import fr.ubs.sporttrack.model.Activity;

public class MyViewModel {

    private String keyword;
    private Activity selectedActivity;
    private List<Activity> activities;
    private ActivityService activityService = new ActivityService();

    @Init
    public void init() {
        this.activities = activityService.findAll();
    }

    @Command
    @NotifyChange("activities")
    public void search() {
        if (keyword == null || keyword.isEmpty()) {
            this.activities = activityService.findAll();
            this.activities = activityService.search(keyword);
        }
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public Activity getSelectedActivity() {
        return selectedActivity;
    }
    
    public void setSelectedActivity(Activity selectedActivity) {
        this.selectedActivity = selectedActivity;
    }
}
