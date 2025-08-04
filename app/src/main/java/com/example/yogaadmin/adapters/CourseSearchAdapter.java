package com.example.yogaadmin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.yogaadmin.R;
import com.example.yogaadmin.models.YogaCourse;
import java.util.List;

/**
 * CourseSearchAdapter - RecyclerView adapter for displaying search results
 * 
 * This adapter provides an interface for displaying yoga courses in search results.
 * It shows comprehensive course information including name, teacher, day, and time
 * in a format optimized for search result display.
 * 
 * Features:
 * - Display course name, teacher, day, and time information
 * - Click handling for course selection from search results
 * - Optimized layout for search result display
 * - Efficient view holder pattern implementation
 * - Callback interface for handling course selection
 * 
 * This adapter is specifically designed for displaying search results in a
 * user-friendly format that makes it easy to identify and select courses.
 */
public class CourseSearchAdapter extends RecyclerView.Adapter<CourseSearchAdapter.CourseViewHolder> {

    // Data source containing search results to display
    private List<YogaCourse> courseList;
    
    // Callback interface for handling course item clicks
    private OnItemClickListener listener;

    /**
     * Interface for handling course item clicks in search results
     * Provides a callback method when a course item is selected
     */
    public interface OnItemClickListener {
        /**
         * Called when a course item is clicked in search results
         * @param course The course that was clicked
         */
        void onItemClick(YogaCourse course);
    }

    /**
     * Constructor for CourseSearchAdapter
     * 
     * @param courseList The list of courses (search results) to display
     * @param listener The callback interface for handling course clicks
     */
    public CourseSearchAdapter(List<YogaCourse> courseList, OnItemClickListener listener) {
        this.courseList = courseList;
        this.listener = listener;
    }

    /**
     * Creates a new ViewHolder for displaying course search result items
     * Inflates the layout for individual course search result items
     * 
     * @param parent The ViewGroup into which the new View will be added
     * @param viewType The view type of the new View
     * @return A new CourseViewHolder that holds a View of the given view type
     */
    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_search, parent, false);
        return new CourseViewHolder(itemView);
    }

    /**
     * Binds course data to the ViewHolder at the specified position
     * Sets up the display and click listener for each course search result item
     * 
     * @param holder The ViewHolder to bind data to
     * @param position The position of the item in the data set
     */
    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        YogaCourse currentCourse = courseList.get(position);
        
        // Display course information in a search-friendly format
        holder.courseNameTextView.setText(currentCourse.getName());
        holder.teacherNameTextView.setText("Taught by " + currentCourse.getTeacherName());
        holder.dayOfWeekTextView.setText("on " + currentCourse.getDayOfWeek());
        holder.timeTextView.setText("at " + currentCourse.getTime());
        
        // Set up click listener for the entire item
        holder.itemView.setOnClickListener(v -> listener.onItemClick(currentCourse));
    }

    /**
     * Returns the total number of items in the data set
     * 
     * @return The number of courses in the search results
     */
    @Override
    public int getItemCount() {
        return courseList.size();
    }

    /**
     * ViewHolder class for holding references to views in each course search result item
     * Implements the ViewHolder pattern for efficient view recycling
     */
    static class CourseViewHolder extends RecyclerView.ViewHolder {
        
        // UI Components for displaying course search result information
        TextView courseNameTextView;
        TextView teacherNameTextView;
        TextView dayOfWeekTextView;
        TextView timeTextView;

        /**
         * Constructor for CourseViewHolder
         * Initializes all view references from the item layout
         * 
         * @param itemView The view for this ViewHolder
         */
        CourseViewHolder(View itemView) {
            super(itemView);
            
            // Initialize text views for course search result information
            courseNameTextView = itemView.findViewById(R.id.courseNameTextView);
            teacherNameTextView = itemView.findViewById(R.id.teacherNameTextView);
            dayOfWeekTextView = itemView.findViewById(R.id.dayOfWeekTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
        }
    }
}
