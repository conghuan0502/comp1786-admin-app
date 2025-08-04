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
import com.example.yogaadmin.utils.ValidationUtils;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;

/**
 * ManageTeachersActivity - Activity for managing yoga teachers
 * 
 * This activity provides functionality to add new teachers and view existing ones.
 * It includes a form for adding teachers with validation and a RecyclerView to
 * display all teachers in the system.
 * 
 * Features:
 * - Add new teachers with name, email, and phone number
 * - Input validation for email and phone number formats
 * - Display all teachers in a scrollable list
 * - Real-time list updates after adding teachers
 * - Form clearing after successful teacher addition
 * 
 * The activity uses Material Design components and provides a clean interface
 * for teacher management with proper validation and user feedback.
 */
public class ManageTeachersActivity extends AppCompatActivity {

    // UI Components - Input Fields
    private TextInputEditText etTeacherName, etTeacherEmail, etTeacherPhone;
    
    // UI Components - Action Button
    private Button btnAddTeacher;
    
    // UI Components - RecyclerView
    private RecyclerView rvTeachers;
    
    // Adapters and Data
    private TeacherAdapter teacherAdapter;
    private List<Teacher> teacherList = new ArrayList<>();
    
    // Data Access Objects
    private TeacherDAO teacherDAO;

    /**
     * Called when the activity is first created
     * Initializes the UI, database connections, and sets up the RecyclerView
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_teachers);

        // Set up the toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize database access
        teacherDAO = new TeacherDAO(this);

        // Set up UI components and data
        initializeViews();
        setupRecyclerView();
        loadTeachers();

        // Set up the add teacher action
        btnAddTeacher.setOnClickListener(v -> addTeacher());
    }

    /**
     * Initializes all UI components by finding them in the layout
     * Binds Java variables to XML layout elements
     */
    private void initializeViews() {
        // Text input fields for teacher information
        etTeacherName = findViewById(R.id.etTeacherName);
        etTeacherEmail = findViewById(R.id.etTeacherEmail);
        etTeacherPhone = findViewById(R.id.etTeacherPhone);
        
        // Action button for adding teachers
        btnAddTeacher = findViewById(R.id.btnAddTeacher);
        
        // RecyclerView for displaying teachers
        rvTeachers = findViewById(R.id.rvTeachers);
    }

    /**
     * Sets up the RecyclerView with layout manager and adapter
     * Configures the RecyclerView to display teachers in a vertical list
     */
    private void setupRecyclerView() {
        rvTeachers.setLayoutManager(new LinearLayoutManager(this));
        teacherAdapter = new TeacherAdapter(teacherList);
        rvTeachers.setAdapter(teacherAdapter);
    }

    /**
     * Loads all teachers from the database and updates the RecyclerView
     * Fetches teacher data and refreshes the adapter to display current data
     */
    private void loadTeachers() {
        List<Teacher> teachers = teacherDAO.getAllTeachers();
        teacherAdapter.updateData(teachers);
    }

    /**
     * Validates user input and adds a new teacher to the database
     * 
     * This method performs the following steps:
     * 1. Extracts and trims input values
     * 2. Validates required fields (name is mandatory)
     * 3. Validates email format using ValidationUtils
     * 4. Validates phone number format using ValidationUtils
     * 5. Creates a new Teacher object
     * 6. Inserts the teacher into the database
     * 7. Provides user feedback and clears form on success
     * 8. Refreshes the teacher list to show the new entry
     */
    private void addTeacher() {
        // Extract and trim input values
        String name = etTeacherName.getText().toString().trim();
        String email = etTeacherEmail.getText().toString().trim();
        String phone = etTeacherPhone.getText().toString().trim();

        // Validate that teacher name is provided (required field)
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Teacher name is required", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Validate email format if provided
        if (!ValidationUtils.isValidEmail(email)) {
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate phone number format if provided
        if (!ValidationUtils.isValidPhone(phone)) {
            Toast.makeText(this, "Invalid phone number", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Create new teacher object and insert into database
        Teacher teacher = new Teacher(0, name, email, phone);
        long id = teacherDAO.insertTeacher(teacher);

        // Handle the result of the database operation
        if (id != -1) {
            // Success: show feedback and clear form
            Toast.makeText(this, "Teacher added successfully", Toast.LENGTH_SHORT).show();
            etTeacherName.setText("");
            etTeacherEmail.setText("");
            etTeacherPhone.setText("");
            loadTeachers(); // Refresh the list to show the new teacher
        } else {
            // Error: show error message
            Toast.makeText(this, "Error adding teacher", Toast.LENGTH_SHORT).show();
        }
    }
}