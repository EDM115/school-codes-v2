package fr.ubs.sporttrack.model;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;


public class JSONFileReader{
    private List<Activity> activities;

    /**
     * Creates a JSONFileReader object that opens and reads the file
     * specified as parameter, and stores the content in a list of
     * objects of type <code>Activity</code>.
     * @param f The file that must be read.
     * @exception IOException if the file does not exist or cannot be
     * read.
     */
    public JSONFileReader(File f) throws IOException{
        this.activities = new ArrayList<>();
        // TODO: Read the file and load data here
        // ...
    }


    /**
     * Returns a list of objects of type <code>JSONObject</code> that have
     * been read from the file.
     * @return a list of objects of type <code>JSONObject</code>.
     */
    public List<Activity> getActivities(){
        return this.activities;
    }

}
