package fr.ubs.sporttrack.webapp;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import java.util.List;
import java.util.ArrayList;

import fr.ubs.sporttrack.model.Activity;

public class MyViewModel {

    private List<Activity> activities;
    private List<Activity> actList;
    private Activity selectedActivity;
    private String keyword;

    public MyViewModel(){
        
    }

	@Init
	public void init() {
		this.activities = new ArrayList<>();
        this.actList = new ArrayList<>();
        Activity act1 = new Activity("12/12/2022", "Natation 1", 700, 40, 90, null);
        Activity act2 = new Activity("20/12/2022", "Course 1", 700, 60, 140, null);
        Activity act3 = new Activity("20/12/2022", "Course 2", 700, 40, 120, null);
        this.activities.add(act1);
        this.activities.add(act2);
        this.activities.add(act3);
        this.actList.addAll(this.activities);
	}

	@Command
	@NotifyChange("actList")
	public void search() {
        this.actList.clear();
        for(Activity act: this.activities){
            if(act.getDescription().contains(this.keyword)){
                this.actList.add(act);
            }
        }
	}

    public void setKeyword(String k){
        this.keyword = k;
    }

    public String getKeyword(){
        return this.keyword;
    }

	public List<Activity> getActList() {
		return this.actList;
	}

    public void setSelectedActivity(Activity act){
        this.selectedActivity = act;
    }

    public Activity getSelectedActivity(){
        return this.selectedActivity;
    }
}
