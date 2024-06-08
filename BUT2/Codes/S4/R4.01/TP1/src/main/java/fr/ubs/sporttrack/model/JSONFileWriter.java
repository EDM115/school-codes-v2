package fr.ubs.sporttrack.model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;
import java.util.List;
import org.json.JSONArray;

public class JSONFileWriter {
    private BufferedWriter bufferedWriter;

    /**
     * Creates a JSONFileWriter object that opens the file specified
     * as parameter in order to write objects of type
     * <code>Activity</code> into it.
     * @param f The file that must be opened.
     * @exception IOException if the file cannot be open in write mode.
     */
    public JSONFileWriter(File f) throws IOException {
        if (!f.exists()) {
            f.createNewFile();
        }
        this.bufferedWriter = new BufferedWriter(new FileWriter(f, false));
    }


    /**
     * Writes a list of objects of type <code>Activity</code> into
     * the file.
     * @param activities The activities that must be written into the file.
     * @exception IOException if an error occurs while writting data.
     */
    public void writeData(List<Activity> activities) throws IOException {
        JSONArray jsonArray = new JSONArray();
        for (Activity activity : activities) {
            jsonArray.put(activity.toJSON());
        }
        bufferedWriter.write(jsonArray.toString());
    }


    /**
     * Closes the file.
     * @exception IOException if an error occurs while closing the file.
     */
    public void close() throws IOException {
        if (bufferedWriter != null) {
            bufferedWriter.close();
        }
    }
}
