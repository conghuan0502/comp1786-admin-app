package com.example.yogaadmin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.yogaadmin.models.YogaCourse;
import java.util.List;

/**
 * CourseSpinnerAdapter - ArrayAdapter for displaying courses in Spinner components
 * 
 * This adapter provides a custom implementation of ArrayAdapter for displaying
 * YogaCourse objects in Spinner widgets. It handles both the main view and dropdown
 * view of the spinner with proper course name display.
 * 
 * Features:
 * - Display course names in spinner components
 * - Custom view creation for both main and dropdown views
 * - Efficient view recycling with convertView pattern
 * - Proper handling of YogaCourse objects in spinner context
 * - Clean separation between data and display logic
 * 
 * This adapter is used in forms and dialogs where course selection is required,
 * such as when creating class instances or other course-related operations.
 */
public class CourseSpinnerAdapter extends ArrayAdapter<YogaCourse> {

    /**
     * Constructor for CourseSpinnerAdapter
     * 
     * @param context The context for inflating layouts
     * @param courses The list of courses to display in the spinner
     */
    public CourseSpinnerAdapter(@NonNull Context context, @NonNull List<YogaCourse> courses) {
        super(context, 0, courses);
    }

    /**
     * Creates the main view for the spinner item
     * This is the view that appears when the spinner is collapsed
     * 
     * @param position The position of the item in the data set
     * @param convertView The old view to reuse, if possible
     * @param parent The parent that this view will eventually be attached to
     * @return A view corresponding to the data at the specified position
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    /**
     * Creates the dropdown view for the spinner item
     * This is the view that appears in the dropdown list when the spinner is expanded
     * 
     * @param position The position of the item in the data set
     * @param convertView The old view to reuse, if possible
     * @param parent The parent that this view will eventually be attached to
     * @return A view corresponding to the data at the specified position
     */
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    /**
     * Creates a view for displaying course information
     * Handles view recycling and course name display
     * 
     * @param position The position of the item in the data set
     * @param convertView The old view to reuse, if possible
     * @param parent The parent that this view will eventually be attached to
     * @return A view displaying the course name
     */
    private View createView(int position, View convertView, ViewGroup parent) {
        // Reuse convertView if available for better performance
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        // Get the text view and course data
        TextView textView = convertView.findViewById(android.R.id.text1);
        YogaCourse course = getItem(position);
        
        // Set the course name if the course object is not null
        if (course != null) {
            textView.setText(course.getName());
        }

        return convertView;
    }
}