package com.example.yogaadmin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.yogaadmin.R;
import com.example.yogaadmin.database.DatabaseHelper;
import com.example.yogaadmin.database.FirebaseSyncManager;

public class MainActivity extends AppCompatActivity {

    private CardView addCourseCard, viewCoursesCard, manageInstancesCard, searchCard, manageTeachersCard;
    private Button btnResetDatabase, btnSyncFirebase;
    private DatabaseHelper dbHelper;
    private FirebaseSyncManager firebaseSyncManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        firebaseSyncManager = new FirebaseSyncManager(this);
        initializeViews();
        setClickListeners();
    }

    private void initializeViews() {
        addCourseCard = findViewById(R.id.addCourseCard);
        viewCoursesCard = findViewById(R.id.viewCoursesCard);
        manageInstancesCard = findViewById(R.id.manageInstancesCard);
        searchCard = findViewById(R.id.searchCard);
        manageTeachersCard = findViewById(R.id.manageTeachersCard);
        btnResetDatabase = findViewById(R.id.btnResetDatabase);
        btnSyncFirebase = findViewById(R.id.btnSyncFirebase);
    }

    private void setClickListeners() {
        addCourseCard.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddCourseActivity.class)));

        viewCoursesCard.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ViewCoursesActivity.class)));

        manageInstancesCard.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ManageInstancesActivity.class)));

        searchCard.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SearchActivity.class)));

        manageTeachersCard.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ManageTeachersActivity.class)));

        btnResetDatabase.setOnClickListener(v -> {
            dbHelper.resetDatabase();
            showToast("Database reset successfully!");
        });

        btnSyncFirebase.setOnClickListener(v -> {
            firebaseSyncManager.syncAllData();
            showToast("Syncing data to Firebase!");
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}