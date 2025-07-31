package com.example.yogaadmin.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.yogaadmin.R;
import com.example.yogaadmin.adapters.CourseRecyclerAdapter;
import com.example.yogaadmin.database.CourseDAO;
import com.example.yogaadmin.models.YogaCourse;
import com.google.android.material.appbar.MaterialToolbar;
import java.util.List;

public class ManageInstancesActivity extends AppCompatActivity {
    private RecyclerView rvCourses;
    private CourseDAO courseDAO;
    private CourseRecyclerAdapter courseAdapter;
    private List<YogaCourse> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_instances);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        courseDAO = new CourseDAO(this);
        rvCourses = findViewById(R.id.rvCourses);
        rvCourses.setLayoutManager(new LinearLayoutManager(this));

        loadCourses();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCourses();
    }

    private void loadCourses() {
        courseList = courseDAO.getAllCourses();
        courseAdapter = new CourseRecyclerAdapter(this, courseList, course -> {
            Intent intent = new Intent(ManageInstancesActivity.this, ViewInstancesActivity.class);
            intent.putExtra("course_id", course.getId());
            intent.putExtra("course_name", course.getName());
            startActivity(intent);
        });
        rvCourses.setAdapter(courseAdapter);
    }
}