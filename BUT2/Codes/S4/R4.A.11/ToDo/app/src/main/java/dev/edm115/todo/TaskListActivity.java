package dev.edm115.todo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class TaskListActivity extends AppCompatActivity {

    private ArrayList<Task> tasks;
    private TaskListAdapter tasksAdapter;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        ListView lvTasks = findViewById(R.id.lvTasks);
        tasks = new ArrayList<>();
        tasksAdapter = new TaskListAdapter(this, tasks);
        lvTasks.setAdapter(tasksAdapter);

        sharedPreferences = getSharedPreferences("tasks", Context.MODE_PRIVATE);
        gson = new Gson();

        lvTasks.setOnItemClickListener((parent, view, position, id) -> {
            Task task = tasks.get(position);

            Intent intent = new Intent(TaskListActivity.this, TaskDescActivity.class);
            intent.putExtra("taskId", task.getId());
            intent.putExtra("taskTitle", task.getTitle());
            intent.putExtra("taskDescription", task.getDescription());
            intent.putExtra("taskDate", task.getDate());
            intent.putExtra("taskDuration", task.getDuration());
            intent.putExtra("taskWebLink", task.getWebLink());
            intent.putExtra("taskContext", task.getContext());
            intent.putExtra("taskStatus", task.getStatus());
            startActivity(intent);
        });

        findViewById(R.id.btnAddTask).setOnClickListener(v -> {
            Intent intent = new Intent(TaskListActivity.this, TaskCreateActivity.class);
            startActivityForResult(intent, 1);
        });

        findViewById(R.id.btnFilterTask).setOnClickListener(v -> showFilterDialog());

        loadTasks();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String taskId = String.valueOf(System.currentTimeMillis());
            String taskTitle = data.getStringExtra("taskTitle");
            String taskDescription = data.getStringExtra("taskDescription");
            String taskDate = data.getStringExtra("taskDate");
            String taskDuration = data.getStringExtra("taskDuration");
            String taskWebLink = data.getStringExtra("taskWebLink");
            String taskContext = data.getStringExtra("taskContext");
            String taskStatus = data.getStringExtra("taskStatus");

            Task newTask = new Task(taskId, taskTitle, taskDescription, taskDate, taskDuration, taskWebLink, taskContext, taskStatus);
            tasks.add(0, newTask);
            tasksAdapter.notifyDataSetChanged();

            saveTasks();
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            loadTasks(); // Refresh the task list after editing
        }
    }

    private void loadTasks() {
        Set<String> tasksSet = sharedPreferences.getStringSet("tasks", new HashSet<>());
        tasks.clear();
        for (String taskJson : tasksSet) {
            Task task = gson.fromJson(taskJson, Task.class);
            tasks.add(task);
        }
        sortTasksByDate();
        tasksAdapter.notifyDataSetChanged();
    }

    private void saveTasks() {
        Set<String> tasksSet = new HashSet<>();
        for (Task task : tasks) {
            tasksSet.add(gson.toJson(task));
        }
        sharedPreferences.edit().putStringSet("tasks", tasksSet).apply();
    }

    private void showFilterDialog() {
        final String[] options = {"Date", "Statut", "Durée", "Contexte"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogTheme);
        builder.setTitle("Trier par")
                .setItems(options, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            sortTasksByDate();
                            break;
                        case 1:
                            sortTasksByStatus();
                            break;
                        case 2:
                            sortTasksByDuration();
                            break;
                        case 3:
                            sortTasksByContext();
                            break;
                    }
                });
        builder.create().show();
    }

    private void sortTasksByDate() {
        tasks.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
        tasksAdapter.notifyDataSetChanged();
    }

    private void sortTasksByStatus() {
        tasks.sort(Comparator.comparing(Task::getStatus));
        tasksAdapter.notifyDataSetChanged();
    }

    private void sortTasksByDuration() {
        tasks.sort(Comparator.comparing(Task::getDuration));
        tasksAdapter.notifyDataSetChanged();
    }

    private void sortTasksByContext() {
        tasks.sort(Comparator.comparing(Task::getContext));
        tasksAdapter.notifyDataSetChanged();
    }
}
