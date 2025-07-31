package com.example.yogaadmin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.yogaadmin.R;
import com.example.yogaadmin.adapters.InstanceAdapter;
import com.example.yogaadmin.database.CourseDAO;
import com.example.yogaadmin.database.InstanceDAO;
import com.example.yogaadmin.models.ClassInstance;
import com.example.yogaadmin.models.YogaCourse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class ViewInstancesActivity extends AppCompatActivity {
    private TextView tvCourseName;
    private RecyclerView rvInstances;
    private FloatingActionButton btnAddInstance;
    private InstanceDAO instanceDAO;
    private CourseDAO courseDAO;
    private InstanceAdapter instanceAdapter;
    private List<ClassInstance> instanceList;
    private YogaCourse course;
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_instances);

        instanceDAO = new InstanceDAO(this);
        courseDAO = new CourseDAO(this);
        courseId = getIntent().getIntExtra("course_id", -1);

        if (courseId == -1) {
            showToast("Error: Invalid Course ID");
            finish();
            return;
        }

        course = courseDAO.getCourseById(courseId);

        if (course == null) {
            showToast("Error: Course not found");
            finish();
            return;
        }

        initializeViews();
        setupRecyclerView();
        loadInstances();

        btnAddInstance.setOnClickListener(v -> {
            Intent intent = new Intent(ViewInstancesActivity.this, AddInstanceActivity.class);
            intent.putExtra("course_id", course.getId());
            intent.putExtra("teacher_id", course.getTeacherId());
            intent.putExtra("course_name", course.getName());
            intent.putExtra("course_day", course.getDayOfWeek());
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadInstances();
    }

    private void initializeViews() {
        tvCourseName = findViewById(R.id.toolbar_title);
        rvInstances = findViewById(R.id.instancesRecyclerView);
        btnAddInstance = findViewById(R.id.addInstanceFab);
        tvCourseName.setText(course.getName());
    }

    private void setupRecyclerView() {
        rvInstances.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadInstances() {
        instanceList = instanceDAO.getInstancesForCourse(courseId);
        instanceAdapter = new InstanceAdapter(this, instanceList, this::showInstanceOptions);
        rvInstances.setAdapter(instanceAdapter);
    }

    private void showInstanceOptions(ClassInstance instance) {
        new AlertDialog.Builder(this)
                .setTitle("Instance Options")
                .setItems(new String[]{"Edit Instance", "Delete Instance"}, (dialog, which) -> {
                    if (which == 0) {
                        editInstance(instance);
                    } else if (which == 1) {
                        deleteInstance(instance);
                    }
                })
                .show();
    }

    private void editInstance(ClassInstance instance) {
        Intent intent = new Intent(this, EditInstanceActivity.class);
        intent.putExtra("instance_id", instance.getId());
        intent.putExtra("course_id", course.getId());
        intent.putExtra("teacher_id", course.getTeacherId());
        intent.putExtra("course_name", course.getName());
        intent.putExtra("course_day", course.getDayOfWeek());
        startActivity(intent);
    }

    private void deleteInstance(ClassInstance instance) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Instance")
                .setMessage("Are you sure you want to delete this class instance?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    instanceDAO.deleteInstance(instance.getId());
                    loadInstances();
                    showToast("Instance deleted successfully");
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
