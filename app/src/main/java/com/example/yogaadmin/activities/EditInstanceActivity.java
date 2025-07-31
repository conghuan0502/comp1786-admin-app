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

public class EditInstanceActivity extends AppCompatActivity {
    private TextView tvCourseName, tvCourseDay;
    private EditText etDate;
    private Spinner spinnerTeacher;
    private Button btnSelectDate, btnSaveInstance;
    private InstanceDAO instanceDAO;
    private TeacherDAO teacherDAO;
    private int instanceId;
    private int courseId;
    private int teacherId;
    private String courseName;
    private String courseDay;
    private Calendar selectedDate;
    private List<Teacher> teacherList;
    private TeacherSpinnerAdapter teacherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_instance);

        instanceDAO = new InstanceDAO(this);
        teacherDAO = new TeacherDAO(this);

        if (!unpackIntentExtras()) {
            Toast.makeText(this, "Error: Instance data missing.", Toast.LENGTH_LONG).show();
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
        instanceId = extras.getInt("instance_id", -1);
        courseId = extras.getInt("course_id", -1);
        teacherId = extras.getInt("teacher_id", -1);
        courseName = extras.getString("course_name");
        courseDay = extras.getString("course_day");

        return instanceId != -1 && courseId != -1 && teacherId != -1 && courseName != null && courseDay != null;
    }

    private void initializeViews() {
        tvCourseName = findViewById(R.id.tvCourseName);
        tvCourseDay = findViewById(R.id.tvCourseDay);
        etDate = findViewById(R.id.etDate);
        spinnerTeacher = findViewById(R.id.spinnerTeacher);
        btnSelectDate = findViewById(R.id.btnSelectDate);
        btnSaveInstance = findViewById(R.id.btnSaveInstance);
    }

    private void populateUI() {
        tvCourseName.setText(courseName);
        tvCourseDay.setText(courseDay);

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

    private void setupClickListeners() {
        btnSelectDate.setOnClickListener(v -> showDatePicker());
        etDate.setOnClickListener(v -> showDatePicker());
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
        Teacher selectedTeacher = (Teacher) spinnerTeacher.getSelectedItem();

        if (TextUtils.isEmpty(date)) {
            showToast("Please select a valid date.");
            return;
        }

        if (selectedTeacher == null) {
            showToast("Please select a teacher.");
            return;
        }

        int newTeacherId = selectedTeacher.getId();
        ClassInstance instance = new ClassInstance(instanceId, courseId, newTeacherId, date);
        int result = instanceDAO.updateInstance(instance);

        if (result > 0) {
            showToast("Class instance updated successfully!");
            finish();
        } else {
            showToast("Error updating class instance.");
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
