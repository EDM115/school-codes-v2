package fr.ubs.sporttrack.models.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONFileReaderUser {
    private List<User> users;

    /**
     * Creates a JSONFileReader object that opens and reads the file
     * specified as parameter, and stores the content in a list of
     * objects of type <code>Activity</code>.
     * @param f The file that must be read.
     * @exception IOException if the file does not exist or cannot be
     * read.
     */
    public JSONFileReaderUser(File f) throws IOException {
        this.users = new ArrayList<>();
        StringBuilder jsonContent = new StringBuilder();
        try (Scanner scanner = new Scanner(f)) {
            while (scanner.hasNextLine()) {
                jsonContent.append(scanner.nextLine());
            }
            JSONArray jsonArray = new JSONArray(jsonContent.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject userObject = jsonArray.getJSONObject(i);
                User user = User.fromJSON(userObject);
                this.users.add(user);
            }
        } catch (FileNotFoundException e) {
            throw new IOException("File not found: " + f.getAbsolutePath(), e);
        }
    }

    /**
     * Returns a list of objects of type <code>User</code> that have
     * been read from the file.
     * @return a list of objects of type <code>User</code>.
     */
    public List<User> getUsers() {
        return this.users;
    }
}
