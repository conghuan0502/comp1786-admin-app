package com.example.yogaadmin.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FirebaseSyncManager {

    private static final String TAG = "FirebaseSyncManager";
    private final DatabaseHelper dbHelper;
    private final DatabaseReference firebaseDatabase;

    public FirebaseSyncManager(Context context) {
        dbHelper = new DatabaseHelper(context);
        firebaseDatabase = FirebaseDatabase.getInstance("https://yogaadmin-d50ee-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
    }

    public void syncAllData() {
        syncTable(DatabaseContract.TeacherEntry.TABLE_NAME);
        syncTable(DatabaseContract.CourseEntry.TABLE_NAME);
        syncTable(DatabaseContract.InstanceEntry.TABLE_NAME);
    }

    private void syncTable(String tableName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);
        DatabaseReference tableRef = firebaseDatabase.child(tableName);

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                Map<String, Object> rowData = new HashMap<>();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    String columnName = cursor.getColumnName(i);
                    if (columnName.equals("_id")) continue; // Skip the local ID

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
                tableRef.child(id).setValue(rowData)
                        .addOnSuccessListener(aVoid -> Log.d(TAG, "Successfully synced row " + id + " to " + tableName))
                        .addOnFailureListener(e -> Log.e(TAG, "Failed to sync row " + id + " to " + tableName, e));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}