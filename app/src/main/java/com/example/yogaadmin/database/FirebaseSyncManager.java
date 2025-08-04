package com.example.yogaadmin.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * FirebaseSyncManager handles synchronization between the local SQLite database
 * and Firebase Realtime Database. This class provides methods to sync all local
 * data to Firebase and reset the Firebase database.
 * 
 * The FirebaseSyncManager supports:
 * - Network connectivity checking
 * - Synchronizing all database tables to Firebase
 * - Resetting the Firebase database
 * - Automatic data type conversion for Firebase storage
 * 
 * @author YogaAdmin Team
 * @version 1.0
 */
public class FirebaseSyncManager {

    /** Tag for logging purposes */
    private static final String TAG = "FirebaseSyncManager";
    
    /** Database helper instance for local database operations */
    private final DatabaseHelper dbHelper;
    
    /** Firebase database reference for remote operations */
    private final DatabaseReference firebaseDatabase;
    
    /** Application context for network and UI operations */
    private final Context context;

    /**
     * Constructs a new FirebaseSyncManager with the given context.
     * Initializes the Firebase database reference with the specific project URL.
     * 
     * @param context The application context
     */
    public FirebaseSyncManager(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
        firebaseDatabase = FirebaseDatabase.getInstance("https://yogaadmin-d50ee-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
    }

    /**
     * Checks if the device has an active network connection.
     * This method is used to determine if Firebase operations can be performed.
     * 
     * @return true if network is available and connected, false otherwise
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    /**
     * Synchronizes all local database tables to Firebase.
     * This method checks network connectivity and syncs teachers, courses, and instances.
     * If no network is available, it shows a toast message to the user.
     */
    public void syncAllData() {
        if (!isNetworkAvailable()) {
            Toast.makeText(context, "No connection, can't sync database", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Sync all tables in order
        syncTable(DatabaseContract.TeacherEntry.TABLE_NAME);
        syncTable(DatabaseContract.CourseEntry.TABLE_NAME);
        syncTable(DatabaseContract.InstanceEntry.TABLE_NAME);
    }

    /**
     * Resets the entire Firebase database by removing all data.
     * This method checks network connectivity before performing the operation.
     * If no network is available, it shows a toast message to the user.
     */
    public void resetFirebaseDatabase() {
        if (!isNetworkAvailable()) {
            Toast.makeText(context, "No connection, can't reset Firebase", Toast.LENGTH_SHORT).show();
            return;
        }
        
        firebaseDatabase.removeValue()
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Firebase database reset successfully."))
                .addOnFailureListener(e -> Log.e(TAG, "Failed to reset Firebase database.", e));
    }

    /**
     * Synchronizes a specific table from the local database to Firebase.
     * This method reads all data from the specified table and uploads it to Firebase
     * with proper data type conversion.
     * 
     * @param tableName The name of the table to synchronize
     */
    private void syncTable(String tableName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);
        DatabaseReference tableRef = firebaseDatabase.child(tableName);

        // Process each row in the table
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                Map<String, Object> rowData = new HashMap<>();
                
                // Convert each column to appropriate Firebase data type
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    String columnName = cursor.getColumnName(i);
                    if (columnName.equals("_id")) continue; // Skip the local ID

                    // Convert data based on SQLite column type
                    switch (cursor.getType(i)) {
                        case Cursor.FIELD_TYPE_INTEGER:
                            rowData.put(columnName, cursor.getLong(i));
                            break;
                        case Cursor.FIELD_TYPE_FLOAT:
                            rowData.put(columnName, cursor.getDouble(i));
                            break;
                        case Cursor.FIELD_TYPE_STRING:
                            rowData.put(columnName, cursor.getString(i));
                            break;
                        case Cursor.FIELD_TYPE_NULL:
                            rowData.put(columnName, null);
                            break;
                    }
                }
                
                // Upload row data to Firebase with success/failure logging
                tableRef.child(id).setValue(rowData)
                        .addOnSuccessListener(aVoid -> Log.d(TAG, "Successfully synced row " + id + " to " + tableName))
                        .addOnFailureListener(e -> Log.e(TAG, "Failed to sync row " + id + " to " + tableName, e));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}