package com.example.yogaadmin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.yogaadmin.R;
import com.example.yogaadmin.models.YogaCourse;
import java.util.List;

/**
 * CourseViewAdapter - RecyclerView adapter for displaying yoga courses with action buttons
 * 
 * This adapter provides a comprehensive interface for displaying yoga courses in a RecyclerView
 * with multiple action buttons for each course. It handles the display of course information
 * and manages user interactions through a callback interface.
 * 
 * Features:
 * - Display course name and schedule information
 * - Multiple action buttons for each course (View Details, Manage Instances, Edit, Delete)
 * - Callback interface for handling user actions
 * - Efficient view holder pattern implementation
 * - Clean separation of concerns between display and action handling
 * 
 * The adapter uses the ViewHolder pattern for optimal performance and provides
 * a callback interface to handle user interactions without tight coupling.
 */
public class CourseViewAdapter extends RecyclerView.Adapter<CourseViewAdapter.CourseViewHolder> {

    // Context for inflating layouts and accessing resources
    private final Context context;
    
    // Data source containing all courses to display
    private final List<YogaCourse> courses;
    
    // Callback interface for handling user actions on course items
    private final OnCourseActionsClickListener onCourseActionsClickListener;

    /**
     * Interface for handling course action button clicks
     * Provides callback methods for all available actions on a course item
     */
    public interface OnCourseActionsClickListener {
        /**
         * Called when the "View Details" button is clicked
         * @param course The course to view details for
         */
        void onViewDetailsClick(YogaCourse course);
        
        /**
         * Called when the "Manage Instances" button is clicked
         * @param course The course to manage instances for
         */
        void onManageInstancesClick(YogaCourse course);
        
        /**
         * Called when the "Edit Course" button is clicked
         * @param course The course to edit
         */
        void onEditCourseClick(YogaCourse course);
        
        /**
         * Called when the "Delete Course" button is clicked
         * @param course The course to delete
         */
        void onDeleteCourseClick(YogaCourse course);
    }

    /**
     * Constructor for CourseViewAdapter
     * 
     * @param context The context for inflating layouts
     * @param courses The list of courses to display
     * @param onCourseActionsClickListener The callback interface for handling user actions
     */
    public CourseViewAdapter(Context context, List<YogaCourse> courses, OnCourseActionsClickListener onCourseActionsClickListener) {
        this.context = context;
        this.courses = courses;
        this.onCourseActionsClickListener = onCourseActionsClickListener;
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_course_view, parent, false);
        return new CourseViewHolder(view);
    }

    /**
     * Binds course data to the ViewHolder at the specified position
     * Sets up the display and click listeners for each course item
     * 
     * @param holder The ViewHolder to bind data to
     * @param position The position of the item in the data set
     */
    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        YogaCourse course = courses.get(position);
        holder.bind(course, onCourseActionsClickListener);
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
        private final TextView tvCourseDetails;
        
        // Action buttons for course management
        private final Button btnViewDetails;
        private final Button btnManageInstances;
        private final Button btnEditCourse;
        private final Button btnDeleteCourse;

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
            tvCourseDetails = itemView.findViewById(R.id.tvCourseDetails);
            
            // Initialize action buttons
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);
            btnManageInstances = itemView.findViewById(R.id.btnManageInstances);
            btnEditCourse = itemView.findViewById(R.id.btnEditCourse);
            btnDeleteCourse = itemView.findViewById(R.id.btnDeleteCourse);
        }

        /**
         * Binds course data to the views and sets up click listeners
         * Displays course information and configures action button callbacks
         * 
         * @param course The course data to display
         * @param listener The callback interface for handling button clicks
         */
        public void bind(final YogaCourse course, final OnCourseActionsClickListener listener) {
            // Display course information
            tvCourseName.setText(course.getName());
            tvCourseDetails.setText(course.getDayOfWeek() + ", " + course.getTime());
            
            // Set up click listeners for action buttons
            btnViewDetails.setOnClickListener(v -> listener.onViewDetailsClick(course));
            btnManageInstances.setOnClickListener(v -> listener.onManageInstancesClick(course));
            btnEditCourse.setOnClickListener(v -> listener.onEditCourseClick(course));
            btnDeleteCourse.setOnClickListener(v -> listener.onDeleteCourseClick(course));
        }
    }
}