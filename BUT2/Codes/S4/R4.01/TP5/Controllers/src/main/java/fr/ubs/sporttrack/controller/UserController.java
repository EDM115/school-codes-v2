package fr.ubs.sporttrack.controller;

import fr.ubs.sporttrack.models.user.User;
import fr.ubs.sporttrack.models.user.JSONFileReaderUser;
import fr.ubs.sporttrack.models.user.JSONFileWriterUser;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.io.File;
import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3030")
public class UserController {
    @Autowired
    private Validator validator;

    private List<User> users;
    private File file = new File(getClass().getClassLoader().getResource("user_data.json").getFile());
    private JSONFileWriterUser writer;

    public UserController() {
        try {
            JSONFileReaderUser reader = new JSONFileReaderUser(file);
            this.users = reader.getUsers();
        } catch (IOException e) {
            e.printStackTrace();
            this.users = List.of();
        }

    }

    @Operation(summary = "Retrieve all users", description = "Returns a list of all users stored in the database.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/")
    @CrossOrigin(origins = "http://localhost:3030")
    public List<User> findAll() {
        return this.users;
    }

    @Operation(summary = "Add a new user", description = "Adds a new user to the database and returns it.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User successfully added", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "400", description = "Invalid user supplied")
    })
    @PostMapping(path="/register", consumes="application/json", produces="application/json")
    @CrossOrigin(origins = "http://localhost:3030")
    public ResponseEntity<?> addUser(@RequestBody User user) throws IOException {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining("; "));
            return ResponseEntity.badRequest().body(errorMessage);
        }

        this.users.add(user);
        this.writer = new JSONFileWriterUser(file);
        this.writer.writeData(this.users);
        this.writer.close();
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Find user by email", description = "Returns the user with the specified email.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully found user", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "No user found with the email")
    })
    @GetMapping("/{email}")
    @CrossOrigin(origins = "http://localhost:3030")
    public List<User> findByKeyword(@PathVariable("email") String email) {
        return this.users.stream()
        .filter(user -> user.getEmail().toLowerCase().contains(email.toLowerCase()))
        .collect(Collectors.toList());
    }

    @Operation(summary = "Checks the user's credentials",
           description = "Allows the user to log in with their email and password.")
    @ApiResponse(responseCode = "200",
                description = "User successfully logged in",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
    @PostMapping(path="/login", consumes="application/json", produces="application/json")
    @CrossOrigin(origins = "http://localhost:3030")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        for (User storedUser : users) {
            if (storedUser.getEmail().equals(user.getEmail()) && storedUser.getPassword().equals(user.getPassword())) {
                return ResponseEntity.ok(storedUser);
            }
        }
        return ResponseEntity.status(401).body("Nom d'utilisateur ou mot de passe incorrect");
    }

    @GetMapping
    @CrossOrigin(origins = "http://localhost:3030")
    public ResponseEntity<Void> redirectToSlash() {
        return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT).location(URI.create("/users/")).build();
    }
}
