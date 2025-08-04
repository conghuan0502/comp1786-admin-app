package com.example.yogaadmin.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.yogaadmin.R;
import com.example.yogaadmin.adapters.TeacherSpinnerAdapter;
import com.example.yogaadmin.database.InstanceDAO;
import com.example.yogaadmin.database.TeacherDAO;
import com.example.yogaadmin.models.ClassInstance;
import com.example.yogaadmin.models.Teacher;
import com.example.yogaadmin.utils.DateTimeUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * EditInstanceActivity - Activity for editing existing class instances
 * 
 * This activity allows users to modify existing class instances by changing
 * the date and/or the assigned teacher. It validates that the selected date
 * matches the course's scheduled day and prevents invalid modifications.
 * 
 * Features:
 * - Display current instance information
 * - Edit class date with date picker
 * - Change assigned teacher via dropdown
 * - Validation of date against course schedule
 * - Teacher selection from available teachers
 * - Update instance in database
 * 
 * The activity receives instance and course information from the calling activity
 * and provides a user-friendly interface for editing class instances.
 */
public class EditInstanceActivity extends AppCompatActivity {
    
    // UI Components - Display Information
    private TextView tvCourseName, tvCourseDay;
    
    // UI Components - Edit Fields
    private EditText etDate;
    private Spinner spinnerTeacher;
    
    // UI Components - Action Buttons
    private Button btnSelectDate, btnSaveInstance;
    
    // Data Access Objects
    private InstanceDAO instanceDAO;
    private TeacherDAO teacherDAO;
    
    // Instance Information (received from intent)
    private int instanceId;
    private int courseId;
    private int teacherId;
    private String courseName;
    private String courseDay;
    
    // Date Selection
    private Calendar selectedDate;
    
    // Teacher Selection
    private List<Teacher> teacherList;
    private TeacherSpinnerAdapter teacherAdapter;

    /**
     * Called when the activity is first created
     * Initializes the UI and validates received instance data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_instance);

        // Initialize database access
        instanceDAO = new InstanceDAO(this);
        teacherDAO = new TeacherDAO(this);

        // Validate and extract instance data from intent
        if (!unpackIntentExtras()) {
            Toast.makeText(this, "Error: Instance data missing.", Toast.LENGTH_LONG).show();
            finish(); // Close activity if required data is missing
            return;
        }

        // Set up UI components and functionality
        initializeViews();
        populateUI();
        setupClickListeners();
    }

    /**
     * Extracts and validates instance data from the intent extras
     * 
     * @return true if all required data is present and valid, false otherwise
     */
    private boolean unpackIntentExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return false;
        }
        
        // Extract instance and course information from intent
        instanceId = extras.getInt("instance_id", -1);
        courseId = extras.getInt("course_id", -1);
        teacherId = extras.getInt("teacher_id", -1);
        courseName = extras.getString("course_name");
        courseDay = extras.getString("course_day");

        // Validate that all required data is present
        return instanceId != -1 && courseId != -1 && teacherId != -1 && courseName != null && courseDay != null;
    }

    /**
     * Initializes all UI components by finding them in the layout
     * Binds Java variables to XML layout elements
     */
    private void initializeViews() {
        // Text views for displaying course information
        tvCourseName = findViewById(R.id.tvCourseName);
        tvCourseDay = findViewById(R.id.tvCourseDay);
        
        // Edit fields for instance data
        etDate = findViewById(R.id.etDate);
        spinnerTeacher = findViewById(R.id.spinnerTeacher);
        
        // Action buttons
        btnSelectDate = findViewById(R.id.btnSelectDate);
        btnSaveInstance = findViewById(R.id.btnSaveInstance);
    }

    /**
     * Populates the UI with course information and sets up teacher dropdown
     * Loads all teachers and sets the current teacher as selected
     */
    private void populateUI() {
        // Display course information
        tvCourseName.setText(courseName);
        tvCourseDay.setText(courseDay);

        // Set up teacher dropdown
        teacherList = teacherDAO.getAllTeachers();
        teacherAdapter = new TeacherSpinnerAdapter(this, teacherList);
        spinnerTeacher.setAdapter(teacherAdapter);

        // Set the initial selection to the current teacher
        for (int i = 0; i < teacherList.size(); i++) {
            if (teacherList.get(i).getId() == teacherId) {
                spinnerTeacher.setSelection(i);
                break;
            }
        }
    }

    /**
     * Sets up click listeners for all interactive UI elements
     * Configures date picker and save functionality
     */
    private void setupClickListeners() {
        btnSelectDate.setOnClickListener(v -> showDatePicker());
        etDate.setOnClickListener(v -> showDatePicker()); // Also allow clicking the EditText
        btnSaveInstance.setOnClickListener(v -> validateAndSaveInstance());
    }

    /**
     * Shows a date picker dialog for selecting the class date
     * Validates that the selected date matches the course's scheduled day
     */
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    // Create calendar instance for selected date
                    selectedDate = Calendar.getInstance();
                    selectedDate.set(year, month, dayOfMonth);

                    // Validate that selected date matches course schedule
                    int selectedDayOfWeek = selectedDate.get(Calendar.DAY_OF_WEEK);
                    int courseDayOfWeek = DateTimeUtils.getDayOfWeekFromString(courseDay, Locale.getDefault());

                    if (selectedDayOfWeek != courseDayOfWeek) {
                        // Show error if selected date doesn't match course schedule
                        String selectedDayName = new SimpleDateFormat("EEEE", Locale.getDefault()).format(selectedDate.getTime());
                        showToast("Error: Selected date (" + selectedDayName + ") doesn't match the scheduled day (" + courseDay + ")");
                        etDate.setText(""); // Clear the invalid date
                    } else {
                        // Format and display the valid selected date
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        etDate.setText(sdf.format(selectedDate.getTime()));
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    /**
     * Validates user input and updates the class instance
     * 
     * This method:
     * 1. Validates that a date has been selected
     * 2. Validates that a teacher has been selected
     * 3. Creates an updated ClassInstance object
     * 4. Updates the instance in the database
     * 5. Provides user feedback on success/failure
     * 6. Closes the activity on successful update
     */
    private void validateAndSaveInstance() {
        String date = etDate.getText().toString().trim();
        Teacher selectedTeacher = (Teacher) spinnerTeacher.getSelectedItem();

        // Validate that a date has been selected
        if (TextUtils.isEmpty(date)) {
            showToast("Please select a valid date.");
            return;
        }

        // Validate that a teacher has been selected
        if (selectedTeacher == null) {
            showToast("Please select a teacher.");
            return;
        }

        // Create updated instance and save to database
        int newTeacherId = selectedTeacher.getId();
        ClassInstance instance = new ClassInstance(instanceId, courseId, newTeacherId, date);
        int result = instanceDAO.updateInstance(instance);

        // Handle the result of the database operation
        if (result > 0) {
            showToast("Class instance updated successfully!");
            finish(); // Close activity and return to previous screen
        } else {
            showToast("Error updating class instance.");
        }
    }

    /**
     * Utility method to display toast messages to the user
     * Provides user feedback for various operations
     * 
     * @param message The message to display in the toast
     */
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
