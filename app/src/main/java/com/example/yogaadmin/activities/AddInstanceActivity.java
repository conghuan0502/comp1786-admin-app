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

public class AddInstanceActivity extends AppCompatActivity {
    private TextView tvCourseName, tvCourseDay;
    private EditText etDate;
    private Button btnSelectDate, btnSaveInstance;
    private InstanceDAO instanceDAO;
    private int courseId;
    private int teacherId;
    private String courseName;
    private String courseDay;
    private Calendar selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instance);

        instanceDAO = new InstanceDAO(this);

        if (!unpackIntentExtras()) {
            Toast.makeText(this, "Error: Course data missing.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        initializeViews();
        populateUI();
        setupClickListeners();
    }

    private boolean unpackIntentExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return false;
        }
        courseId = extras.getInt("course_id", -1);
        teacherId = extras.getInt("teacher_id", -1);
        courseName = extras.getString("course_name");
        courseDay = extras.getString("course_day");

        Log.d("AddInstanceActivity", "Received courseId: " + courseId + ", teacherId: " + teacherId + ", courseName: " + courseName + ", courseDay: " + courseDay);

        return courseId != -1 && teacherId != -1 && courseName != null && courseDay != null;
    }

    private void initializeViews() {
        tvCourseName = findViewById(R.id.tvCourseName);
        tvCourseDay = findViewById(R.id.tvCourseDay);
        etDate = findViewById(R.id.etDate);
        btnSelectDate = findViewById(R.id.btnSelectDate);
        btnSaveInstance = findViewById(R.id.btnSaveInstance);
    }

    private void populateUI() {
        tvCourseName.setText(courseName);
        tvCourseDay.setText(courseDay);
    }

    private void setupClickListeners() {
        btnSelectDate.setOnClickListener(v -> showDatePicker());
        etDate.setOnClickListener(v -> showDatePicker()); // Also allow clicking the EditText
        btnSaveInstance.setOnClickListener(v -> validateAndSaveInstance());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    selectedDate = Calendar.getInstance();
                    selectedDate.set(year, month, dayOfMonth);

                    int selectedDayOfWeek = selectedDate.get(Calendar.DAY_OF_WEEK);
                    int courseDayOfWeek = DateTimeUtils.getDayOfWeekFromString(courseDay, Locale.getDefault());

                    if (selectedDayOfWeek != courseDayOfWeek) {
                        String selectedDayName = new SimpleDateFormat("EEEE", Locale.getDefault()).format(selectedDate.getTime());
                        showToast("Error: Selected date (" + selectedDayName + ") doesn't match the scheduled day (" + courseDay + ")");
                        etDate.setText("");
                    } else {
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

    private void validateAndSaveInstance() {
        String date = etDate.getText().toString().trim();

        if (TextUtils.isEmpty(date)) {
            showToast("Please select a valid date.");
            return;
        }

        ClassInstance instance = new ClassInstance(courseId, teacherId, date);
        long result = instanceDAO.insertInstance(instance);

        if (result != -1) {
            showToast("Class instance added successfully!");
            finish();
        } else {
            showToast("Error adding class instance. It may already exist.");
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
