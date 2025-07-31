package com.example.yogaadmin.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.yogaadmin.R;
import com.example.yogaadmin.database.CourseDAO;
import com.example.yogaadmin.database.TeacherDAO;
import com.example.yogaadmin.models.Teacher;
import com.example.yogaadmin.models.YogaCourse;
import com.example.yogaadmin.utils.Constants;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import java.util.List;

public class AddCourseActivity extends AppCompatActivity {

    private TextInputEditText etName, etDescription, etCapacity, etDuration, etPrice;
    private AutoCompleteTextView spinnerDay, spinnerTime, spinnerType, spinnerDifficulty, spinnerTeacher;
    private Button btnConfirm;

    private CourseDAO courseDAO;
    private TeacherDAO teacherDAO;
    private List<Teacher> teacherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        courseDAO = new CourseDAO(this);
        teacherDAO = new TeacherDAO(this);

        initializeViews();
        setupDropdowns();
    }

    private void initializeViews() {
        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        etCapacity = findViewById(R.id.etCapacity);
        etDuration = findViewById(R.id.etDuration);
        etPrice = findViewById(R.id.etPrice);
        spinnerDay = findViewById(R.id.spinnerDay);
        spinnerTime = findViewById(R.id.spinnerTime);
        spinnerType = findViewById(R.id.spinnerType);
        spinnerDifficulty = findViewById(R.id.spinnerDifficulty);
        spinnerTeacher = findViewById(R.id.spinnerTeacher);
        btnConfirm = findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(v -> addCourse());
    }

    private void setupDropdowns() {
        // Day of Week
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, Constants.DAYS_OF_WEEK);
        spinnerDay.setAdapter(dayAdapter);

        // Time
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, Constants.TIME_SLOTS);
        spinnerTime.setAdapter(timeAdapter);

        // Course Type
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, Constants.COURSE_TYPES);
        spinnerType.setAdapter(typeAdapter);

        // Difficulty
        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, Constants.DIFFICULTY_LEVELS);
        spinnerDifficulty.setAdapter(difficultyAdapter);

        // Teacher
        teacherList = teacherDAO.getAllTeachers();
        ArrayAdapter<Teacher> teacherAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, teacherList);
        spinnerTeacher.setAdapter(teacherAdapter);
    }

    private void addCourse() {
        String name = etName.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String capacityStr = etCapacity.getText().toString().trim();
        String durationStr = etDuration.getText().toString().trim();
        String priceStr = etPrice.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(capacityStr) || TextUtils.isEmpty(durationStr) || TextUtils.isEmpty(priceStr)) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String teacherName = spinnerTeacher.getText().toString();
        Teacher selectedTeacher = null;
        for (Teacher teacher : teacherList) {
            if (teacher.getName().equals(teacherName)) {
                selectedTeacher = teacher;
                break;
            }
        }

        if (selectedTeacher == null) {
            Toast.makeText(this, "Please select a valid teacher", Toast.LENGTH_SHORT).show();
            return;
        }

        String dayOfWeek = spinnerDay.getText().toString();
        String time = spinnerTime.getText().toString();
        String type = spinnerType.getText().toString();
        String difficulty = spinnerDifficulty.getText().toString();
        int teacherId = selectedTeacher.getId();
        int capacity = Integer.parseInt(capacityStr);
        int duration = Integer.parseInt(durationStr);
        double price = Double.parseDouble(priceStr);

        YogaCourse course = new YogaCourse(name, description, difficulty, dayOfWeek, time, type, teacherId, duration, capacity, price);
        long id = courseDAO.insertCourse(course);

        if (id != -1) {
            Toast.makeText(this, "Course added successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error adding course", Toast.LENGTH_SHORT).show();
        }
    }
}
