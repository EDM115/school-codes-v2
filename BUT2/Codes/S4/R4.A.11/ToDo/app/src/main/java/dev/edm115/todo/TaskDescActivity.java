package dev.edm115.todo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

public class TaskDescActivity extends AppCompatActivity {

    private TextView tvTaskTitle, tvTaskDescription, tvTaskDate, tvTaskDuration, tvTaskWebLink, tvTaskContext, tvTaskStatus;
    private String taskId, taskTitle, taskDescription, taskDate, taskDuration, taskWebLink, taskContext, taskStatus;
    private Gson gson;
    private SharedPreferences sharedPreferences;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_desc);

        tvTaskTitle = findViewById(R.id.tvTaskTitle);
        tvTaskDescription = findViewById(R.id.tvTaskDescription);
        tvTaskDate = findViewById(R.id.tvTaskDate);
        tvTaskDuration = findViewById(R.id.tvTaskDuration);
        tvTaskWebLink = findViewById(R.id.tvTaskWebLink);
        tvTaskContext = findViewById(R.id.tvTaskContext);
        tvTaskStatus = findViewById(R.id.tvTaskStatus);

        sharedPreferences = getSharedPreferences("tasks", Context.MODE_PRIVATE);
        gson = new Gson();

        taskId = getIntent().getStringExtra("taskId");
        taskTitle = getIntent().getStringExtra("taskTitle");
        taskDescription = getIntent().getStringExtra("taskDescription");
        taskDate = getIntent().getStringExtra("taskDate");
        taskDuration = getIntent().getStringExtra("taskDuration");
        taskWebLink = getIntent().getStringExtra("taskWebLink");
        taskContext = getIntent().getStringExtra("taskContext");
        taskStatus = getIntent().getStringExtra("taskStatus");

        tvTaskTitle.setText(getString(R.string.item_title) + taskTitle);
        tvTaskDescription.setText(getString(R.string.item_description) + taskDescription);
        tvTaskDate.setText(getString(R.string.item_date) + taskDate);
        tvTaskDuration.setText(getString(R.string.item_duration) + taskDuration);
        tvTaskWebLink.setText(getString(R.string.item_web_link) + taskWebLink);
        tvTaskContext.setText(getString(R.string.item_context) + taskContext);
        tvTaskStatus.setText(getString(R.string.item_status) + taskStatus);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnEdit).setOnClickListener(v -> {
            Intent editIntent = new Intent(TaskDescActivity.this, TaskCreateActivity.class);
            editIntent.putExtra("taskId", taskId);
            editIntent.putExtra("taskTitle", taskTitle);
            editIntent.putExtra("taskDescription", taskDescription);
            editIntent.putExtra("taskDate", taskDate);
            editIntent.putExtra("taskDuration", taskDuration);
            editIntent.putExtra("taskWebLink", taskWebLink);
            editIntent.putExtra("taskContext", taskContext);
            editIntent.putExtra("taskStatus", taskStatus);
            startActivityForResult(editIntent, 2);
        });

        findViewById(R.id.btnChangeStatus).setOnClickListener(v -> showStatusChangeDialog());

        findViewById(R.id.btnDelete).setOnClickListener(v -> {
            if ("Terminée".equals(taskStatus)) {
                DeleteTask(taskId);
                Toast.makeText(TaskDescActivity.this, "Tâche supprimée", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(TaskDescActivity.this, "Seules les tâches complétées peuvent être supprimées", Toast.LENGTH_SHORT).show();
            }
        });

        if ("Terminée".equals(taskStatus)) {
            findViewById(R.id.btnDelete).setVisibility(View.VISIBLE);
        }

        tvTaskWebLink.setOnClickListener(v -> {
            Intent intent = new Intent(TaskDescActivity.this, WebViewActivity.class);
            intent.putExtra("url", taskWebLink);
            startActivity(intent);
        });
    }

    private void showStatusChangeDialog() {
        final String[] statuses = {"A faire", "En cours", "Terminée"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogTheme);
        builder.setTitle("Changer le statut")
                .setItems(statuses, (dialog, which) -> {
                    taskStatus = statuses[which];
                    tvTaskStatus.setText(getString(R.string.item_status) + taskStatus);
                    SaveTaskStatus(taskId, taskStatus);
                    if ("Terminée".equals(taskStatus)) {
                        findViewById(R.id.btnDelete).setVisibility(View.VISIBLE);
                    } else {
                        findViewById(R.id.btnDelete).setVisibility(View.GONE);
                    }
                });
        builder.create().show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            taskTitle = data.getStringExtra("taskTitle");
            taskDescription = data.getStringExtra("taskDescription");
            taskDate = data.getStringExtra("taskDate");
            taskDuration = data.getStringExtra("taskDuration");
            taskWebLink = data.getStringExtra("taskWebLink");
            taskContext = data.getStringExtra("taskContext");
            taskStatus = data.getStringExtra("taskStatus");

            tvTaskTitle.setText(getString(R.string.item_title) + taskTitle);
            tvTaskDescription.setText(getString(R.string.item_description) + taskDescription);
            tvTaskDate.setText(getString(R.string.item_date) + taskDate);
            tvTaskDuration.setText(getString(R.string.item_duration) + taskDuration);
            tvTaskWebLink.setText(getString(R.string.item_web_link) + taskWebLink);
            tvTaskContext.setText(getString(R.string.item_context) + taskContext);
            tvTaskStatus.setText(getString(R.string.item_status) + taskStatus);

            SaveTaskDetails(new Task(taskId, taskTitle, taskDescription, taskDate, taskDuration, taskWebLink, taskContext, taskStatus));
        }
    }

    private void SaveTaskStatus(String taskId, String taskStatus) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> tasksSet = sharedPreferences.getStringSet("tasks", new HashSet<>());
        Set<String> updatedTasksSet = new HashSet<>();

        for (String taskJson : tasksSet) {
            Task task = gson.fromJson(taskJson, Task.class);
            if (task.getId().equals(taskId)) {
                task.setStatus(taskStatus);
            }
            updatedTasksSet.add(gson.toJson(task));
        }

        editor.putStringSet("tasks", updatedTasksSet);
        editor.apply();
    }

    private void DeleteTask(String taskId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> tasksSet = sharedPreferences.getStringSet("tasks", new HashSet<>());
        Set<String> updatedTasksSet = new HashSet<>();

        for (String taskJson : tasksSet) {
            Task task = gson.fromJson(taskJson, Task.class);
            if (!task.getId().equals(taskId)) {
                updatedTasksSet.add(gson.toJson(task));
            }
        }

        editor.putStringSet("tasks", updatedTasksSet);
        editor.apply();
    }

    private void SaveTaskDetails(Task task) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> tasksSet = sharedPreferences.getStringSet("tasks", new HashSet<>());
        Set<String> updatedTasksSet = new HashSet<>();

        boolean taskUpdated = false;
        for (String taskJson : tasksSet) {
            Task existingTask = gson.fromJson(taskJson, Task.class);
            if (existingTask.getId().equals(task.getId())) {
                updatedTasksSet.add(gson.toJson(task));
                taskUpdated = true;
            } else {
                updatedTasksSet.add(taskJson);
            }
        }
        if (!taskUpdated) {
            updatedTasksSet.add(gson.toJson(task));
        }

        editor.putStringSet("tasks", updatedTasksSet);
        editor.apply();
    }
}
