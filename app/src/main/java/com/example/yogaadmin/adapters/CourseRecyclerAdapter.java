package com.example.yogaadmin.adapters;

import android.content.Context;
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
 * CourseRecyclerAdapter - Simple RecyclerView adapter for displaying courses in a list
 * 
 * This adapter provides a basic interface for displaying yoga courses in a RecyclerView
 * with click handling. It's designed for simple course listing scenarios where users
 * need to select a course from a list.
 * 
 * Features:
 * - Display course name and description
 * - Click handling for course selection
 * - Simple, clean layout for course items
 * - Efficient view holder pattern implementation
 * - Callback interface for handling course selection
 * 
 * This adapter is typically used in scenarios like course selection for instance
 * management or other course-related operations.
 */
public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.CourseViewHolder> {

    // Context for inflating layouts and accessing resources
    private final Context context;
    
    // Data source containing all courses to display
    private final List<YogaCourse> courses;
    
    // Callback interface for handling course item clicks
    private final OnCourseClickListener onCourseClickListener;

    /**
     * Interface for handling course item clicks
     * Provides a callback method when a course item is selected
     */
    public interface OnCourseClickListener {
        /**
         * Called when a course item is clicked
         * @param course The course that was clicked
         */
        void onCourseClick(YogaCourse course);
    }

    /**
     * Constructor for CourseRecyclerAdapter
     * 
     * @param context The context for inflating layouts
     * @param courses The list of courses to display
     * @param onCourseClickListener The callback interface for handling course clicks
     */
    public CourseRecyclerAdapter(Context context, List<YogaCourse> courses, OnCourseClickListener onCourseClickListener) {
        this.context = context;
        this.courses = courses;
        this.onCourseClickListener = onCourseClickListener;
    }

    /**
     * Creates a new ViewHolder for displaying course items
     * Inflates the layout for individual course items
     * 
     * @param parent The ViewGroup into which the new View will be added
     * @param viewType The view type of the new View
     * @return A new CourseViewHolder that holds a View of the given view type
     */
    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_course_manage, parent, false);
        return new CourseViewHolder(view);
    }

    /**
     * Binds course data to the ViewHolder at the specified position
     * Sets up the display and click listener for each course item
     * 
     * @param holder The ViewHolder to bind data to
     * @param position The position of the item in the data set
     */
    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        YogaCourse course = courses.get(position);
        holder.bind(course, onCourseClickListener);
    }

    /**
     * Returns the total number of items in the data set
     * 
     * @return The number of courses in the list
     */
    @Override
    public int getItemCount() {
        return courses.size();
    }

    /**
     * ViewHolder class for holding references to views in each course item
     * Implements the ViewHolder pattern for efficient view recycling
     */
    static class CourseViewHolder extends RecyclerView.ViewHolder {
        
        // UI Components for displaying course information
        private final TextView tvCourseName;
        private final TextView tvCourseDescription;

        /**
         * Constructor for CourseViewHolder
         * Initializes all view references from the item layout
         * 
         * @param itemView The view for this ViewHolder
         */
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            
            // Initialize text views for course information
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvCourseDescription = itemView.findViewById(R.id.tvCourseDescription);
        }

        /**
         * Binds course data to the views and sets up click listener
         * Displays course information and configures item click handling
         * 
         * @param course The course data to display
         * @param listener The callback interface for handling item clicks
         */
        public void bind(final YogaCourse course, final OnCourseClickListener listener) {
            // Display course information
            tvCourseName.setText(course.getName());
            tvCourseDescription.setText(course.getDescription());
            
            // Set up click listener for the entire item
            itemView.setOnClickListener(v -> listener.onCourseClick(course));
        }
    }
}