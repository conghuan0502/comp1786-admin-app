package com.example.yogaadmin.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.yogaadmin.R;
import com.example.yogaadmin.adapters.CourseViewAdapter;
import com.example.yogaadmin.database.CourseDAO;
import com.example.yogaadmin.models.YogaCourse;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class ViewCoursesActivity extends AppCompatActivity implements CourseViewAdapter.OnCourseActionsClickListener {
    private RecyclerView rvCourses;
    private CourseDAO courseDAO;
    private CourseViewAdapter courseAdapter;
    private List<YogaCourse> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_courses);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        courseDAO = new CourseDAO(this);
        rvCourses = findViewById(R.id.rvCourses);
        rvCourses.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fabAddCourse = findViewById(R.id.fabAddCourse);
        fabAddCourse.setOnClickListener(v -> {
            startActivity(new Intent(ViewCoursesActivity.this, AddCourseActivity.class));
        });

        loadCourses();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCourses();
    }

    private void loadCourses() {
        courseList = courseDAO.getAllCourses();
        courseAdapter = new CourseViewAdapter(this, courseList, this);
        rvCourses.setAdapter(courseAdapter);
    }

    @Override
    public void onViewDetailsClick(YogaCourse course) {
        Intent intent = new Intent(this, CourseDetailActivity.class);
        intent.putExtra("course_id", course.getId());
        startActivity(intent);
    }

    @Override
    public void onManageInstancesClick(YogaCourse course) {
        Intent intent = new Intent(this, ViewInstancesActivity.class);
        intent.putExtra("course_id", course.getId());
        intent.putExtra("course_name", course.getName() + " - " + course.getDayOfWeek() + " " + course.getTime());
        startActivity(intent);
    }

    @Override
    public void onEditCourseClick(YogaCourse course) {
        editCourse(course);
    }

    @Override
    public void onDeleteCourseClick(YogaCourse course) {
        deleteCourse(course);
    }

    private void editCourse(YogaCourse course) {
        Intent intent = new Intent(this, AddCourseActivity.class);
        intent.putExtra("course_id", course.getId());
        startActivity(intent);
    }

    private void deleteCourse(YogaCourse course) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Course")
                .setMessage("Are you sure you want to delete this course? This will also delete all associated class instances.")
                .setPositiveButton("Delete", (dialog, which) -> {
                    courseDAO.deleteCourse(course.getId());
                    loadCourses();
                    showToast("Course deleted successfully");
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showToast(String message) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show();
    }
}