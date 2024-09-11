package com.example.evaluacion1vmx;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    private LinearLayout tasksContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        View rootView = findViewById(R.id.main);
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setButtonListeners();

        Button addButton = findViewById(R.id.button);
        tasksContainer = findViewById(R.id.tasksContainer);

        addButton.setOnClickListener(v -> {
            View newTaskView = createTaskView();
            tasksContainer.addView(newTaskView);
        });

        loadTasks();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        // No llames a super.onBackPressed() aquí, ya que queremos controlar el cierre de la actividad

        AlertDialog dialog = showFeedbackDialog(); // Obtén una referencia al diálogo creado

        // Configura un listener para detectar cuándo se cierra el diálogo
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish(); // Cierra la actividad cuando el diálogo se cierra
            }
        });
    }

    private AlertDialog showFeedbackDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View feedbackView = getLayoutInflater().inflate(R.layout.feedback_layout, null);
        builder.setView(feedbackView);

        RatingBar ratingBar = feedbackView.findViewById(R.id.ratingBar);
        Button submitButton = feedbackView.findViewById(R.id.submitButton);

        AlertDialog dialog = builder.create();

        submitButton.setOnClickListener(v -> {
            float rating = ratingBar.getRating();

            // Save the tasks to SharedPreferences before finishing
            saveTasks();

            // Close the dialog
            dialog.dismiss();

            // Delay finishing the activity to allow the dialog to close smoothly
            new Handler().postDelayed(this::finish, 9000); // 300ms delay
        });

        dialog.show();
        return dialog;
    }

    private void setButtonListeners() {
    }



    private View createTaskView() {
        View taskView = getLayoutInflater().inflate(R.layout.task_item, null);

        CheckBox checkBox = taskView.findViewById(R.id.taskCheckBox);
        Button editButton = taskView.findViewById(R.id.editTaskButton);
        Button deleteButton = taskView.findViewById(R.id.deleteTaskButton);

        editButton.setOnClickListener(v -> showEditDialog(checkBox));

        deleteButton.setOnClickListener(v -> {
            tasksContainer.removeView(taskView);
            saveTasks(); // Save the updated list after deletion
        });

        return taskView;
    }

    private void showEditDialog(CheckBox checkBoxToEdit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar Nombre");

        final EditText input = new EditText(this);
        input.setText(checkBoxToEdit.getText());
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            checkBoxToEdit.setText(input.getText().toString());
            saveTasks(); // Save tasks after editing
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void saveTasks() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyTasks", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        StringBuilder tasks = new StringBuilder();
        for (int i = 0; i < tasksContainer.getChildCount(); i++) {
            View taskView = tasksContainer.getChildAt(i);
            CheckBox checkBox = taskView.findViewById(R.id.taskCheckBox);
            tasks.append(checkBox.getText().toString()).append(","); // Separate tasks with commas
        }
        editor.putString("tasks", tasks.toString());
        editor.apply();
    }

    private void loadTasks() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyTasks", MODE_PRIVATE);
        String tasksString = sharedPreferences.getString("tasks", "");
        if (!tasksString.isEmpty()) {
            String[] tasks = tasksString.split(",");
            for (String task : tasks) {
                View newTaskView = createTaskView();
                CheckBox checkBox = newTaskView.findViewById(R.id.taskCheckBox);
                checkBox.setText(task);
                tasksContainer.addView(newTaskView);
            }
        }
    }
}