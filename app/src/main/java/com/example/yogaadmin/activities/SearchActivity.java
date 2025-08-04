package com.example.yogaadmin.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.yogaadmin.R;
import com.example.yogaadmin.adapters.CourseSearchAdapter;
import com.example.yogaadmin.database.CourseDAO;
import com.example.yogaadmin.models.YogaCourse;
import com.example.yogaadmin.utils.Constants;
import com.google.android.material.textfield.TextInputEditText;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * SearchActivity - Activity for searching and filtering yoga courses
 * 
 * This activity provides advanced search functionality for finding yoga courses
 * based on multiple criteria. Users can search by teacher name, filter by day
 * of the week, and filter by specific dates.
 * 
 * Features:
 * - Text search by teacher name
 * - Dropdown filter by day of the week
 * - Date picker for filtering by specific dates
 * - Real-time search results display
 * - Clear filters functionality
 * - Navigation to course details from search results
 * - Progress indicator during search
 * - Empty state handling
 * 
 * The activity uses a combination of SearchView, AutoCompleteTextView, and
 * DatePickerDialog to provide a comprehensive search interface.
 */
public class SearchActivity extends AppCompatActivity {

    // UI Components - Search Interface
    private SearchView searchView;
    private AutoCompleteTextView dayOfWeekSpinner;
    private TextInputEditText datePickerEditText;
    
    // UI Components - Action Buttons
    private Button searchButton, clearButton;
    
    // UI Components - Results Display
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView noResultsTextView;

    // Data Access Objects
    private CourseDAO courseDAO;
    
    // Adapters and Data
    private CourseSearchAdapter courseAdapter;
    private List<YogaCourse> courseList = new ArrayList<>();
    
    // Date Selection
    private Calendar selectedDate;

    /**
     * Called when the activity is first created
     * Initializes the UI components and sets up all search functionality
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Initialize database access
        courseDAO = new CourseDAO(this);

        // Initialize all UI components
        searchView = findViewById(R.id.searchView);
        dayOfWeekSpinner = findViewById(R.id.dayOfWeekSpinner);
        datePickerEditText = findViewById(R.id.datePickerEditText);
        searchButton = findViewById(R.id.searchButton);
        clearButton = findViewById(R.id.clearButton);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        noResultsTextView = findViewById(R.id.noResultsTextView);

        // Set up all search functionality
        setupRecyclerView();
        setupDayOfWeekSpinner();
        setupDatePicker();
        setupActionButtons();
    }

    /**
     * Sets up the RecyclerView for displaying search results
     * Configures the layout manager and adapter with click handling
     */
    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter = new CourseSearchAdapter(courseList, course -> {
            // Handle course item click - navigate to course details
            Intent intent = new Intent(SearchActivity.this, CourseDetailActivity.class);
            intent.putExtra(Constants.EXTRA_COURSE_ID, course.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(courseAdapter);
    }

    /**
     * Sets up the day of week dropdown spinner
     * Populates the spinner with predefined day options from Constants
     */
    private void setupDayOfWeekSpinner() {
        ArrayAdapter<String> dayOfWeekAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, Constants.DAYS_OF_WEEK);
        dayOfWeekSpinner.setAdapter(dayOfWeekAdapter);
    }

    /**
     * Sets up the date picker functionality
     * Creates a DatePickerDialog that allows users to select specific dates
     * for filtering courses by scheduled instances
     */
    private void setupDatePicker() {
        datePickerEditText.setOnClickListener(v -> {
            // Initialize calendar with current date or previously selected date
            final Calendar c = Calendar.getInstance();
            if (selectedDate != null) {
                c.setTime(selectedDate.getTime());
            }
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create and show date picker dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        // Handle date selection
                        selectedDate = Calendar.getInstance();
                        selectedDate.set(year1, monthOfYear, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
                        datePickerEditText.setText(sdf.format(selectedDate.getTime()));
                    }, year, month, day);
            datePickerDialog.show();
        });
    }

    /**
     * Sets up the action buttons (search and clear)
     * Configures click listeners for performing search and clearing filters
     */
    private void setupActionButtons() {
        searchButton.setOnClickListener(v -> performSearch());
        clearButton.setOnClickListener(v -> clearFilters());
    }

    /**
     * Performs the search operation based on current filter criteria
     * 
     * This method:
     * 1. Shows progress indicator
     * 2. Hides results and empty state views
     * 3. Extracts search criteria from UI components
     * 4. Executes database search with combined filters
     * 5. Updates UI based on search results
     * 6. Refreshes the RecyclerView adapter
     */
    private void performSearch() {
        // Show progress indicator and hide other views
        progressBar.setVisibility(View.VISIBLE);
        noResultsTextView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        courseList.clear();

        // Extract search criteria from UI components
        String teacherName = searchView.getQuery().toString().trim();
        String dayOfWeek = dayOfWeekSpinner.getText().toString();
        String date = datePickerEditText.getText().toString();

        // Perform search with combined filters
        courseList.addAll(courseDAO.searchCourses(teacherName, dayOfWeek, date));

        // Update UI based on search results
        progressBar.setVisibility(View.GONE);
        if (courseList.isEmpty()) {
            // Show empty state when no results found
            noResultsTextView.setVisibility(View.VISIBLE);
        } else {
            // Show results when courses are found
            recyclerView.setVisibility(View.VISIBLE);
        }
        courseAdapter.notifyDataSetChanged();
    }

    /**
     * Clears all search filters and resets the UI
     * 
     * This method:
     * 1. Clears the search view query
     * 2. Resets the day of week spinner
     * 3. Clears the date picker
     * 4. Resets the selected date
     * 5. Clears the results list
     * 6. Hides results and empty state views
     */
    private void clearFilters() {
        // Clear all search inputs
        searchView.setQuery("", false);
        dayOfWeekSpinner.setText("", false);
        datePickerEditText.setText("");
        selectedDate = null;
        
        // Clear results and update UI
        courseList.clear();
        courseAdapter.notifyDataSetChanged();
        noResultsTextView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }
}
