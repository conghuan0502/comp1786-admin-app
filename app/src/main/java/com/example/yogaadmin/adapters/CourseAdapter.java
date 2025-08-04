package com.example.yogaadmin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yogaadmin.R;
import com.example.yogaadmin.models.YogaCourse;

import java.util.List;

/**
 * CourseAdapter - BaseAdapter for displaying courses in ListView components
 * 
 * This adapter provides a custom implementation of BaseAdapter for displaying
 * YogaCourse objects in ListView widgets. It handles view creation and data
 * binding for course items with comprehensive course information display.
 * 
 * Features:
 * - Display course name and detailed schedule information
 * - Efficient view recycling with convertView pattern
 * - Proper handling of YogaCourse objects in ListView context
 * - Clean separation between data and display logic
 * - Comprehensive course information display (name, schedule, duration, capacity)
 * 
 * This adapter is used in ListView components where course selection or display
 * is required, providing a simple and efficient way to show course information.
 */
public class CourseAdapter extends BaseAdapter {
    
    // Context for inflating layouts and accessing resources
    private Context context;
    
    // Data source containing all courses to display
    private List<YogaCourse> courses;
    
    // LayoutInflater for creating views efficiently
    private LayoutInflater inflater;

    /**
     * Constructor for CourseAdapter
     * 
     * @param context The context for inflating layouts
     * @param courses The list of courses to display
     */
    public CourseAdapter(Context context, List<YogaCourse> courses) {
        this.context = context;
        this.courses = courses;
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * Returns the total number of items in the data set
     * 
     * @return The number of courses in the list
     */
    @Override
    public int getCount() {
        return courses.size();
    }

    /**
     * Returns the course object at the specified position
     * 
     * @param position The position of the item in the data set
     * @return The course object at the specified position
     */
    @Override
    public Object getItem(int position) {
        return courses.get(position);
    }

    /**
     * Returns the unique ID for the course at the specified position
     * Uses the course's database ID as the unique identifier
     * 
     * @param position The position of the item in the data set
     * @return The unique ID of the course at the specified position
     */
    @Override
    public long getItemId(int position) {
        return courses.get(position).getId();
    }

    /**
     * Creates and returns a view for displaying course information
     * Handles view recycling and comprehensive course data display
     * 
     * @param position The position of the item in the data set
     * @param convertView The old view to reuse, if possible
     * @param parent The parent that this view will eventually be attached to
     * @return A view displaying the course information
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Reuse convertView if available for better performance
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        // Get the course data for this position
        YogaCourse course = courses.get(position);

        // Get references to the text views in the layout
        TextView text1 = convertView.findViewById(android.R.id.text1);
        TextView text2 = convertView.findViewById(android.R.id.text2);

        // Display course name in the primary text view
        text1.setText(course.getName());
        
        // Display comprehensive course information in the secondary text view
        // Format: "Day Time • Duration min • Capacity people"
        text2.setText(course.getDayOfWeek() + " " + course.getTime() +
                " • " + course.getDuration() + " min • " + course.getMaxCapacity() + " people");

        return convertView;
    }
}