package fr.ubs.sporttrack.controller;

import fr.ubs.sporttrack.models.activity.Activity;
import fr.ubs.sporttrack.models.activity.Data;
import fr.ubs.sporttrack.models.activity.JSONFileReader;
import fr.ubs.sporttrack.models.activity.JSONFileWriter;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.io.File;
import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/activities")
@CrossOrigin(origins = "http://localhost:3030")
public class ActivityController {
    @Autowired
    private Validator validator;

    private List<Activity> activities;
    private File file = new File(getClass().getClassLoader().getResource("activity_data.json").getFile());
    private JSONFileWriter writer;

    public ActivityController() {
        try {
            JSONFileReader reader = new JSONFileReader(file);
            this.activities = reader.getActivities();
        } catch (IOException e) {
            e.printStackTrace();
            this.activities = List.of();
        }

    }

    @Operation(summary = "Retrieve all activities", description = "Returns a list of all activities stored in the database.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Activity.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/")
    @CrossOrigin(origins = "http://localhost:3030")
    public List<Activity> findAll() {
        return this.activities;
    }

    @Operation(summary = "Find activities by keyword in description", description = "Returns a list of activities that contain the keyword in their description.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully found activities", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Activity.class))),
        @ApiResponse(responseCode = "404", description = "No activities found with the keyword")
    })
    @GetMapping("/{keyword}")
    @CrossOrigin(origins = "http://localhost:3030")
    public List<Activity> findByKeyword(@PathVariable("keyword") String keyword) {
        return this.activities.stream()
        .filter(activity -> activity.getDescription().toLowerCase().contains(keyword.toLowerCase()))
        .collect(Collectors.toList());
    }

    @Operation(summary = "Add a new activity", description = "Adds a new activity to the database and returns it.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Activity successfully added", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Activity.class))),
        @ApiResponse(responseCode = "400", description = "Invalid activity supplied")
    })
    @PostMapping(path="/", consumes="application/json", produces="application/json")
    @CrossOrigin(origins = "http://localhost:3030")
    public ResponseEntity<?> addActivity(@RequestBody Activity activity) throws IOException {
        Set<ConstraintViolation<Activity>> violations = validator.validate(activity);
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining("; "));
            return ResponseEntity.badRequest().body(errorMessage);
        }
        for (int i = 0; i < activity.getData().size(); i++) {
            Set<ConstraintViolation<Data>> dataViolations = validator.validate(activity.getData().get(i));
            if (!dataViolations.isEmpty()) {
                String errorMessage = dataViolations.stream()
                    .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                    .collect(Collectors.joining("; "));
                return ResponseEntity.badRequest().body(errorMessage);
            }
        }

        this.activities.add(activity);
        this.writer = new JSONFileWriter(file);
        this.writer.writeData(this.activities);
        this.writer.close();
        return ResponseEntity.ok(activity);
    }

    @GetMapping
    @CrossOrigin(origins = "http://localhost:3030")
    public ResponseEntity<Void> redirectToSlash() {
        return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT).location(URI.create("/activities/")).build();
    }
}
