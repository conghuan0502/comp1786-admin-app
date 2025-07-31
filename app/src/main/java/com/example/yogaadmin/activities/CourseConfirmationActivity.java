package com.example.yogaadmin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yogaadmin.R;

public class CourseConfirmationActivity extends AppCompatActivity {
    private TextView tvConfirmationDetails;
    private Button btnBackToMain, btnEditCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_confirmation);

        tvConfirmationDetails = findViewById(R.id.tvConfirmationDetails);
        btnBackToMain = findViewById(R.id.btnBackToMain);
        btnEditCourse = findViewById(R.id.btnEditCourse);

        displayCourseDetails();

        btnBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        btnEditCourse.setOnClickListener(v -> {
            finish(); // Go back to edit
        });
    }

    private void displayCourseDetails() {
        Intent intent = getIntent();
        StringBuilder details = new StringBuilder();
        details.append("Course Details:\n\n");
        details.append("Name: ").append(intent.getStringExtra("course_name")).append("\n");
        details.append("Description: ").append(intent.getStringExtra("course_description")).append("\n");
        details.append("Teacher ID: ").append(intent.getIntExtra("course_teacher_id", 0)).append("\n");
        details.append("Day: ").append(intent.getStringExtra("course_day_of_week")).append("\n");
        details.append("Time: ").append(intent.getStringExtra("course_time")).append("\n");
        details.append("Duration: ").append(intent.getIntExtra("course_duration", 0)).append(" minutes\n");
        details.append("Max Capacity: ").append(intent.getIntExtra("course_max_capacity", 0)).append(" people\n");

        tvConfirmationDetails.setText(details.toString());
    }
}