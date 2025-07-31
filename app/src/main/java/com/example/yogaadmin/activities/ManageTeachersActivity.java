package com.example.yogaadmin.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.yogaadmin.R;
import com.example.yogaadmin.adapters.TeacherAdapter;
import com.example.yogaadmin.database.TeacherDAO;
import com.example.yogaadmin.models.Teacher;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;

public class ManageTeachersActivity extends AppCompatActivity {

    private TextInputEditText etTeacherName, etTeacherEmail, etTeacherPhone;
    private Button btnAddTeacher;
    private RecyclerView rvTeachers;
    private TeacherAdapter teacherAdapter;
    private List<Teacher> teacherList = new ArrayList<>();
    private TeacherDAO teacherDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_teachers);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        teacherDAO = new TeacherDAO(this);

        initializeViews();
        setupRecyclerView();
        loadTeachers();

        btnAddTeacher.setOnClickListener(v -> addTeacher());
    }

    private void initializeViews() {
        etTeacherName = findViewById(R.id.etTeacherName);
        etTeacherEmail = findViewById(R.id.etTeacherEmail);
        etTeacherPhone = findViewById(R.id.etTeacherPhone);
        btnAddTeacher = findViewById(R.id.btnAddTeacher);
        rvTeachers = findViewById(R.id.rvTeachers);
    }

    private void setupRecyclerView() {
        rvTeachers.setLayoutManager(new LinearLayoutManager(this));
        teacherAdapter = new TeacherAdapter(teacherList);
        rvTeachers.setAdapter(teacherAdapter);
    }

    private void loadTeachers() {
        List<Teacher> teachers = teacherDAO.getAllTeachers();
        teacherAdapter.updateData(teachers);
    }

    private void addTeacher() {
        String name = etTeacherName.getText().toString().trim();
        String email = etTeacherEmail.getText().toString().trim();
        String phone = etTeacherPhone.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Teacher name is required", Toast.LENGTH_SHORT).show();
            return;
        }

        Teacher teacher = new Teacher(0, name, email, phone);
        long id = teacherDAO.insertTeacher(teacher);

        if (id != -1) {
            Toast.makeText(this, "Teacher added successfully", Toast.LENGTH_SHORT).show();
            etTeacherName.setText("");
            etTeacherEmail.setText("");
            etTeacherPhone.setText("");
            loadTeachers(); // Refresh the list
        } else {
            Toast.makeText(this, "Error adding teacher", Toast.LENGTH_SHORT).show();
        }
    }
}