package com.example.yogaadmin.activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.yogaadmin.R;
import com.example.yogaadmin.database.CourseDAO;
import com.example.yogaadmin.models.YogaCourse;

public class CourseDetailActivity extends AppCompatActivity {

    private CourseDAO courseDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        courseDAO = new CourseDAO(this);

        int courseId = getIntent().getIntExtra("course_id", -1);

        if (courseId != -1) {
            YogaCourse course = courseDAO.getCourseById(courseId);
            if (course != null) {
                populateUI(course);
            } else {
                Toast.makeText(this, "Course not found", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Invalid Course ID", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void populateUI(YogaCourse course) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(course.getName());
        }

        TextView detailCourseName = findViewById(R.id.detailCourseName);
        TextView detailTeacherName = findViewById(R.id.detailTeacherName);
        TextView detailDescription = findViewById(R.id.detailDescription);
        TextView detailDayOfWeek = findViewById(R.id.detailDayOfWeek);
        TextView detailTime = findViewById(R.id.detailTime);
        TextView detailDuration = findViewById(R.id.detailDuration);
        TextView detailCapacity = findViewById(R.id.detailCapacity);
        TextView detailDifficulty = findViewById(R.id.detailDifficulty);
        TextView detailType = findViewById(R.id.detailType);

        detailCourseName.setText(course.getName());
        detailTeacherName.setText("Taught by " + course.getTeacherName());
        detailDescription.setText(course.getDescription());
        detailDayOfWeek.setText(course.getDayOfWeek());
        detailTime.setText(course.getTime());
        detailDuration.setText(course.getDuration() + " minutes");
        detailCapacity.setText(course.getMaxCapacity() + " people");
        detailDifficulty.setText(course.getDifficulty());
        detailType.setText(course.getType());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}