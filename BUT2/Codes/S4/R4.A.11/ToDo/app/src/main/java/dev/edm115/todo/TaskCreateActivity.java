package dev.edm115.todo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class TaskCreateActivity extends AppCompatActivity {

    private EditText etTaskTitle, etTaskDescription, etTaskDate, etTaskDurationDays, etTaskDurationHours, etTaskDurationMinutes, etTaskWebLink;
    private Spinner spTaskContext, spTaskStatus;
    private Calendar calendar;
    private String taskId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_create);

        etTaskTitle = findViewById(R.id.etTaskTitle);
        etTaskDescription = findViewById(R.id.etTaskDescription);
        etTaskDate = findViewById(R.id.etTaskDate);
        etTaskDurationDays = findViewById(R.id.etTaskDurationDays);
        etTaskDurationHours = findViewById(R.id.etTaskDurationHours);
        etTaskDurationMinutes = findViewById(R.id.etTaskDurationMinutes);
        etTaskWebLink = findViewById(R.id.etTaskWebLink);
        spTaskContext = findViewById(R.id.spTaskContext);
        spTaskStatus = findViewById(R.id.spTaskStatus);

        calendar = Calendar.getInstance();

        // Check if editing an existing task
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("taskId")) {
            taskId = intent.getStringExtra("taskId");
            etTaskTitle.setText(intent.getStringExtra("taskTitle"));
            etTaskDescription.setText(intent.getStringExtra("taskDescription"));
            etTaskDate.setText(intent.getStringExtra("taskDate"));

            String[] duration = intent.getStringExtra("taskDuration").split(" ");
            etTaskDurationDays.setText(duration[0].replace("d", ""));
            etTaskDurationHours.setText(duration[1].replace("h", ""));
            etTaskDurationMinutes.setText(duration[2].replace("m", ""));

            etTaskWebLink.setText(intent.getStringExtra("taskWebLink"));
            // Set spTaskContext and spTaskStatus using the received data
        }

        Button btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            String taskTitle = etTaskTitle.getText().toString();
            String taskDescription = etTaskDescription.getText().toString();
            String taskDate = etTaskDate.getText().toString();
            String taskDuration = etTaskDurationDays.getText().toString() + "d " + etTaskDurationHours.getText().toString() + "h " + etTaskDurationMinutes.getText().toString() + "m";
            String taskWebLink = etTaskWebLink.getText().toString();
            String taskContext = spTaskContext.getSelectedItem().toString();
            String taskStatus = spTaskStatus.getSelectedItem().toString();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("taskId", taskId);
            resultIntent.putExtra("taskTitle", taskTitle);
            resultIntent.putExtra("taskDescription", taskDescription);
            resultIntent.putExtra("taskDate", taskDate);
            resultIntent.putExtra("taskDuration", taskDuration);
            resultIntent.putExtra("taskWebLink", taskWebLink);
            resultIntent.putExtra("taskContext", taskContext);
            resultIntent.putExtra("taskStatus", taskStatus);

            setResult(RESULT_OK, resultIntent);
            finish();
        });

        etTaskDate.setOnClickListener(v -> showDatePicker());
    }

    public void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            String formattedDate = getString(R.string.date_format, dayOfMonth, month + 1, year);
            etTaskDate.setText(formattedDate);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
