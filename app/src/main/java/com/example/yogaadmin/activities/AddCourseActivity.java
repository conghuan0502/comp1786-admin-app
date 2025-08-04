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

/**
 * AddCourseActivity - Activity for creating new yoga courses
 * 
 * This activity provides a comprehensive form for adding new yoga courses to the system.
 * It includes fields for course details, scheduling information, and teacher assignment.
 * 
 * Features:
 * - Course information input (name, description, capacity, duration, price)
 * - Dropdown selections for day, time, type, difficulty, and teacher
 * - Input validation and error handling
 * - Database integration for course creation
 * 
 * The activity uses Material Design components and provides a user-friendly
 * interface for course creation with proper validation.
 */
public class AddCourseActivity extends AppCompatActivity {

    // UI Components - Text Input Fields
    private TextInputEditText etName, etDescription, etCapacity, etDuration, etPrice;
    
    // UI Components - Dropdown Spinners
    private AutoCompleteTextView spinnerDay, spinnerTime, spinnerType, spinnerDifficulty, spinnerTeacher;
    
    // UI Components - Action Button
    private Button btnConfirm;

    // Data Access Objects
    private CourseDAO courseDAO;
    private TeacherDAO teacherDAO;
    
    // Data Collections
    private List<Teacher> teacherList;

    /**
     * Called when the activity is first created
     * Initializes the UI, database connections, and sets up dropdown options
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        // Set up the toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize database access objects
        courseDAO = new CourseDAO(this);
        teacherDAO = new TeacherDAO(this);

        // Set up UI components and dropdown options
        initializeViews();
        setupDropdowns();
    }

    /**
     * Initializes all UI components by finding them in the layout
     * Sets up the confirm button click listener
     */
    private void initializeViews() {
        // Text input fields for course details
        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        etCapacity = findViewById(R.id.etCapacity);
        etDuration = findViewById(R.id.etDuration);
        etPrice = findViewById(R.id.etPrice);
        
        // Dropdown spinners for selections
        spinnerDay = findViewById(R.id.spinnerDay);
        spinnerTime = findViewById(R.id.spinnerTime);
        spinnerType = findViewById(R.id.spinnerType);
        spinnerDifficulty = findViewById(R.id.spinnerDifficulty);
        spinnerTeacher = findViewById(R.id.spinnerTeacher);
        
        // Action button
        btnConfirm = findViewById(R.id.btnConfirm);

        // Set up the course creation action
        btnConfirm.setOnClickListener(v -> addCourse());
    }

    /**
     * Sets up all dropdown menus with predefined options and teacher data
     * Creates adapters for each dropdown with appropriate data sources
     */
    private void setupDropdowns() {
        // Day of Week dropdown - uses predefined constants
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, Constants.DAYS_OF_WEEK);
        spinnerDay.setAdapter(dayAdapter);

        // Time slots dropdown - uses predefined time options
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, Constants.TIME_SLOTS);
        spinnerTime.setAdapter(timeAdapter);

        // Course Type dropdown - uses predefined course types
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, Constants.COURSE_TYPES);
        spinnerType.setAdapter(typeAdapter);

        // Difficulty Level dropdown - uses predefined difficulty levels
        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, Constants.DIFFICULTY_LEVELS);
        spinnerDifficulty.setAdapter(difficultyAdapter);

        // Teacher dropdown - populated from database
        teacherList = teacherDAO.getAllTeachers();
        ArrayAdapter<Teacher> teacherAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, teacherList);
        spinnerTeacher.setAdapter(teacherAdapter);
    }

    /**
     * Validates user input and creates a new course in the database
     * 
     * This method performs the following steps:
     * 1. Extracts and validates all form inputs
     * 2. Validates required fields are not empty
     * 3. Finds the selected teacher from the dropdown
     * 4. Converts string inputs to appropriate data types
     * 5. Creates a new YogaCourse object
     * 6. Inserts the course into the database
     * 7. Provides user feedback on success/failure
     */
    private void addCourse() {
        // Extract text input values
        String name = etName.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String capacityStr = etCapacity.getText().toString().trim();
        String durationStr = etDuration.getText().toString().trim();
        String priceStr = etPrice.getText().toString().trim();

        // Validate that all required fields are filled
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(capacityStr) || TextUtils.isEmpty(durationStr) || TextUtils.isEmpty(priceStr)) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Find the selected teacher from the dropdown
        String teacherName = spinnerTeacher.getText().toString();
        Teacher selectedTeacher = null;
        for (Teacher teacher : teacherList) {
            if (teacher.getName().equals(teacherName)) {
                selectedTeacher = teacher;
                break;
            }
        }

        // Validate teacher selection
        if (selectedTeacher == null) {
            Toast.makeText(this, "Please select a valid teacher", Toast.LENGTH_SHORT).show();
            return;
        }

        // Extract dropdown selections
        String dayOfWeek = spinnerDay.getText().toString();
        String time = spinnerTime.getText().toString();
        String type = spinnerType.getText().toString();
        String difficulty = spinnerDifficulty.getText().toString();
        
        // Convert string inputs to appropriate data types
        int teacherId = selectedTeacher.getId();
        int capacity = Integer.parseInt(capacityStr);
        int duration = Integer.parseInt(durationStr);
        double price = Double.parseDouble(priceStr);

        // Create new course object and insert into database
        YogaCourse course = new YogaCourse(name, description, difficulty, dayOfWeek, time, type, teacherId, duration, capacity, price);
        long id = courseDAO.insertCourse(course);

        // Provide user feedback based on database operation result
        if (id != -1) {
            Toast.makeText(this, "Course added successfully", Toast.LENGTH_SHORT).show();
            finish(); // Close activity and return to previous screen
        } else {
            Toast.makeText(this, "Error adding course", Toast.LENGTH_SHORT).show();
        }
    }
}
