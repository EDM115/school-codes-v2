package fr.ubs.sporttrack.models.user;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;
import java.util.List;
import org.json.JSONArray;

public class JSONFileWriterUser {
    private BufferedWriter bufferedWriter;

    /**
     * Creates a JSONFileWriter object that opens the file specified
     * as parameter in order to write objects of type
     * <code>User</code> into it.
     * @param f The file that must be opened.
     * @exception IOException if the file cannot be open in write mode.
     */
    public JSONFileWriterUser(File f) throws IOException {
        if (!f.exists()) {
            f.createNewFile();
        }
        this.bufferedWriter = new BufferedWriter(new FileWriter(f, false));
    }


    /**
     * Writes a list of objects of type <code>User</code> into
     * the file.
     * @param activities The activities that must be written into the file.
     * @exception IOException if an error occurs while writting data.
     */
    public void writeData(List<User> users) throws IOException {
        JSONArray jsonArray = new JSONArray();
        for (User user : users) {
            jsonArray.put(user.toJSON());
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
