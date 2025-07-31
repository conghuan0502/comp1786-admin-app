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

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private AutoCompleteTextView dayOfWeekSpinner;
    private TextInputEditText datePickerEditText;
    private Button searchButton, clearButton;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView noResultsTextView;

    private CourseDAO courseDAO;
    private CourseSearchAdapter courseAdapter;
    private List<YogaCourse> courseList = new ArrayList<>();
    private Calendar selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        courseDAO = new CourseDAO(this);

        // Initialize views
        searchView = findViewById(R.id.searchView);
        dayOfWeekSpinner = findViewById(R.id.dayOfWeekSpinner);
        datePickerEditText = findViewById(R.id.datePickerEditText);
        searchButton = findViewById(R.id.searchButton);
        clearButton = findViewById(R.id.clearButton);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        noResultsTextView = findViewById(R.id.noResultsTextView);

        setupRecyclerView();
        setupDayOfWeekSpinner();
        setupDatePicker();
        setupActionButtons();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter = new CourseSearchAdapter(courseList, course -> {
            Intent intent = new Intent(SearchActivity.this, CourseDetailActivity.class);
            intent.putExtra(Constants.EXTRA_COURSE_ID, course.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(courseAdapter);
    }

    private void setupDayOfWeekSpinner() {
        ArrayAdapter<String> dayOfWeekAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, Constants.DAYS_OF_WEEK);
        dayOfWeekSpinner.setAdapter(dayOfWeekAdapter);
    }

    private void setupDatePicker() {
        datePickerEditText.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            if (selectedDate != null) {
                c.setTime(selectedDate.getTime());
            }
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        selectedDate = Calendar.getInstance();
                        selectedDate.set(year1, monthOfYear, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
                        datePickerEditText.setText(sdf.format(selectedDate.getTime()));
                    }, year, month, day);
            datePickerDialog.show();
        });
    }

    private void setupActionButtons() {
        searchButton.setOnClickListener(v -> performSearch());
        clearButton.setOnClickListener(v -> clearFilters());
    }

    private void performSearch() {
        progressBar.setVisibility(View.VISIBLE);
        noResultsTextView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        courseList.clear();

        String teacherName = searchView.getQuery().toString().trim();
        String dayOfWeek = dayOfWeekSpinner.getText().toString();
        String date = datePickerEditText.getText().toString();

        courseList.addAll(courseDAO.searchCourses(teacherName, dayOfWeek, date));

        progressBar.setVisibility(View.GONE);
        if (courseList.isEmpty()) {
            noResultsTextView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
        }
        courseAdapter.notifyDataSetChanged();
    }

    private void clearFilters() {
        searchView.setQuery("", false);
        dayOfWeekSpinner.setText("", false);
        datePickerEditText.setText("");
        selectedDate = null;
        courseList.clear();
        courseAdapter.notifyDataSetChanged();
        noResultsTextView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }
}
