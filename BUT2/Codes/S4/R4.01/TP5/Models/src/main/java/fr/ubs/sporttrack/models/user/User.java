package fr.ubs.sporttrack.models.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.json.JSONObject;

public class User {
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 3, max = 100)
    private String name;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Email doit être valide")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;

    public User() {}

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static User fromJSON(JSONObject obj) {
        return new User(obj.getString("name"), obj.getString("email"), obj.getString("password"));
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("name", this.name);
        obj.put("email", this.email);
        obj.put("password", this.password);
        return obj;
    }

    @Override
    public String toString() {
        return this.toJSON().toString();
    }
}
