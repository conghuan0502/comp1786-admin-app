package com.example.yogaadmin.activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.yogaadmin.R;
import com.example.yogaadmin.database.CourseDAO;
import com.example.yogaadmin.models.YogaCourse;

/**
 * CourseDetailActivity - Activity for displaying detailed information about a yoga course
 * 
 * This activity shows comprehensive information about a specific yoga course including:
 * - Course name and description
 * - Teacher information
 * - Schedule details (day and time)
 * - Course specifications (duration, capacity, difficulty, type)
 * 
 * The activity receives a course ID from the calling activity and fetches
 * the complete course information from the database for display.
 * 
 * Features:
 * - Detailed course information display
 * - Navigation back to previous screen
 * - Error handling for invalid course IDs
 * - Clean, organized layout of course details
 * 
 * The activity uses a toolbar with back navigation and displays all course
 * information in a user-friendly format.
 */
public class CourseDetailActivity extends AppCompatActivity {

    // Data Access Objects
    private CourseDAO courseDAO;

    /**
     * Called when the activity is first created
     * Initializes the UI, retrieves course data, and populates the display
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        // Set up the toolbar with back navigation
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize database access
        courseDAO = new CourseDAO(this);

        // Retrieve course ID from the intent
        int courseId = getIntent().getIntExtra("course_id", -1);

        // Validate course ID and load course data
        if (courseId != -1) {
            YogaCourse course = courseDAO.getCourseById(courseId);
            if (course != null) {
                // Course found, populate the UI with course details
                populateUI(course);
            } else {
                // Course not found in database
                Toast.makeText(this, "Course not found", Toast.LENGTH_SHORT).show();
                finish(); // Close activity and return to previous screen
            }
        } else {
            // Invalid course ID provided
            Toast.makeText(this, "Invalid Course ID", Toast.LENGTH_SHORT).show();
            finish(); // Close activity and return to previous screen
        }
    }

    /**
     * Populates all UI elements with course information
     * Sets the toolbar title and fills all text views with course details
     * 
     * @param course The course object containing all the information to display
     */
    private void populateUI(YogaCourse course) {
        // Set the toolbar title to the course name
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(course.getName());
        }

        // Find all text views for displaying course information
        TextView detailCourseName = findViewById(R.id.detailCourseName);
        TextView detailTeacherName = findViewById(R.id.detailTeacherName);
        TextView detailDescription = findViewById(R.id.detailDescription);
        TextView detailDayOfWeek = findViewById(R.id.detailDayOfWeek);
        TextView detailTime = findViewById(R.id.detailTime);
        TextView detailDuration = findViewById(R.id.detailDuration);
        TextView detailCapacity = findViewById(R.id.detailCapacity);
        TextView detailDifficulty = findViewById(R.id.detailDifficulty);
        TextView detailType = findViewById(R.id.detailType);

        // Populate all text views with course information
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

    /**
     * Handles the back navigation from the toolbar
     * Called when the user taps the back arrow in the toolbar
     * 
     * @return true to indicate the event was handled
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // Trigger the back button behavior
        return true;
    }
}