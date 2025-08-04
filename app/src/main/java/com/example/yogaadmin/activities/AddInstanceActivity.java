package com.example.yogaadmin.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.yogaadmin.R;
import com.example.yogaadmin.database.InstanceDAO;
import com.example.yogaadmin.models.ClassInstance;
import com.example.yogaadmin.utils.DateTimeUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * AddInstanceActivity - Activity for scheduling new class instances
 *
 * This activity allows users to schedule specific class instances for existing courses.
 * It validates that the selected date matches the course's scheduled day of the week
 * and prevents scheduling conflicts.
 *
 * Features:
 * - Display course information (name and scheduled day)
 * - Date picker for selecting class dates
 * - Validation that selected date matches course schedule
 * - Prevention of duplicate class instances
 * - User-friendly error messages and feedback
 *
 * The activity receives course and teacher information from the calling activity
 * and ensures that class instances are only scheduled on the correct days.
 */
public class AddInstanceActivity extends AppCompatActivity {

    // UI Components - Display Information
    private TextView tvCourseName, tvCourseDay;

    // UI Components - Date Selection
    private EditText etDate;

    // UI Components - Action Buttons
    private Button btnSelectDate, btnSaveInstance;

    // Data Access Objects
    private InstanceDAO instanceDAO;

    // Course Information (received from intent)
    private int courseId;
    private int teacherId;
    private String courseName;
    private String courseDay;

    // Date Selection
    private Calendar selectedDate;

    /**
     * Called when the activity is first created
     * Initializes the UI and validates received course data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instance);

        // Initialize database access
        instanceDAO = new InstanceDAO(this);

        // Validate and extract course data from intent
        if (!unpackIntentExtras()) {
            Toast.makeText(this, "Error: Course data missing.", Toast.LENGTH_LONG).show();
            finish(); // Close activity if required data is missing
            return;
        }

        // Set up UI components and functionality
        initializeViews();
        populateUI();
        setupClickListeners();
    }

    /**
     * Extracts and validates course data from the intent extras
     *
     * @return true if all required data is present and valid, false otherwise
     */
    private boolean unpackIntentExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return false;
        }

        // Extract course information from intent
        courseId = extras.getInt("course_id", -1);
        teacherId = extras.getInt("teacher_id", -1);
        courseName = extras.getString("course_name");
        courseDay = extras.getString("course_day");

        // Log the received data for debugging
        Log.d("AddInstanceActivity", "Received courseId: " + courseId + ", teacherId: " + teacherId + ", courseName: " + courseName + ", courseDay: " + courseDay);

        // Validate that all required data is present
        return courseId != -1 && teacherId != -1 && courseName != null && courseDay != null;
    }

    /**
     * Initializes all UI components by finding them in the layout
     * Binds Java variables to XML layout elements
     */
    private void initializeViews() {
        // Text views for displaying course information
        tvCourseName = findViewById(R.id.tvCourseName);
        tvCourseDay = findViewById(R.id.tvCourseDay);

        // Date input field
        etDate = findViewById(R.id.etDate);

        // Action buttons
        btnSelectDate = findViewById(R.id.btnSelectDate);
        btnSaveInstance = findViewById(R.id.btnSaveInstance);
    }

    /**
     * Populates the UI with course information
     * Displays the course name and scheduled day in the text views
     */
    private void populateUI() {
        tvCourseName.setText(courseName);
        tvCourseDay.setText(courseDay);
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
     * Validates the selected date and saves the class instance
     *
     * This method:
     * 1. Validates that a date has been selected
     * 2. Creates a new ClassInstance object
     * 3. Inserts the instance into the database
     * 4. Provides user feedback on success/failure
     * 5. Closes the activity on successful save
     */
    private void validateAndSaveInstance() {
        String date = etDate.getText().toString().trim();

        // Validate that a date has been selected
        if (TextUtils.isEmpty(date)) {
            showToast("Please select a valid date.");
            return;
        }

        // Create and save the class instance
        ClassInstance instance = new ClassInstance(courseId, teacherId, date);
        long result = instanceDAO.insertInstance(instance);

        // Handle the result of the database operation
        if (result != -1) {
            showToast("Class instance added successfully!");
            finish(); // Close activity and return to previous screen
        } else {
            showToast("Error adding class instance. It may already exist.");
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
