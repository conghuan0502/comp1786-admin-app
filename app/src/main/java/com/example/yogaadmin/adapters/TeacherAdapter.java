package com.example.yogaadmin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.yogaadmin.R;
import com.example.yogaadmin.models.Teacher;
import java.util.List;

/**
 * TeacherAdapter - RecyclerView adapter for displaying teachers in a list
 * 
 * This adapter provides an interface for displaying teachers in a RecyclerView.
 * It shows teacher information including name, email, and phone number for each
 * teacher in a clean, organized layout.
 * 
 * Features:
 * - Display teacher name, email, and phone information
 * - Simple list layout for teacher items
 * - Efficient view holder pattern implementation
 * - Data update functionality for dynamic content
 * - Clean separation of concerns between data and display
 * 
 * This adapter is used in activities that need to display a list of teachers,
 * such as the teacher management screen.
 */
public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder> {

    // Data source containing all teachers to display
    private List<Teacher> teacherList;

    /**
     * Constructor for TeacherAdapter
     * 
     * @param teacherList The list of teachers to display
     */
    public TeacherAdapter(List<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    /**
     * Creates a new ViewHolder for displaying teacher items
     * Inflates the layout for individual teacher items
     * 
     * @param parent The ViewGroup into which the new View will be added
     * @param viewType The view type of the new View
     * @return A new TeacherViewHolder that holds a View of the given view type
     */
    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teacher, parent, false);
        return new TeacherViewHolder(itemView);
    }

    /**
     * Binds teacher data to the ViewHolder at the specified position
     * Sets up the display for each teacher item
     * 
     * @param holder The ViewHolder to bind data to
     * @param position The position of the item in the data set
     */
    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {
        Teacher currentTeacher = teacherList.get(position);
        
        // Display teacher information in the text views
        holder.tvTeacherName.setText(currentTeacher.getName());
        holder.tvTeacherEmail.setText(currentTeacher.getEmail());
        holder.tvTeacherPhone.setText(currentTeacher.getPhone());
    }

    /**
     * Returns the total number of items in the data set
     * 
     * @return The number of teachers in the list
     */
    @Override
    public int getItemCount() {
        return teacherList.size();
    }

    /**
     * Updates the data source and refreshes the RecyclerView
     * Clears the current list and adds all new teachers, then notifies the adapter
     * 
     * @param newTeachers The new list of teachers to display
     */
    public void updateData(List<Teacher> newTeachers) {
        this.teacherList.clear();
        this.teacherList.addAll(newTeachers);
        notifyDataSetChanged();
    }

    /**
     * ViewHolder class for holding references to views in each teacher item
     * Implements the ViewHolder pattern for efficient view recycling
     */
    static class TeacherViewHolder extends RecyclerView.ViewHolder {
        
        // UI Components for displaying teacher information
        TextView tvTeacherName;
        TextView tvTeacherEmail;
        TextView tvTeacherPhone;

        /**
         * Constructor for TeacherViewHolder
         * Initializes all view references from the item layout
         * 
         * @param itemView The view for this ViewHolder
         */
        TeacherViewHolder(View itemView) {
            super(itemView);
            
            // Initialize text views for teacher information
            tvTeacherName = itemView.findViewById(R.id.tvTeacherName);
            tvTeacherEmail = itemView.findViewById(R.id.tvTeacherEmail);
            tvTeacherPhone = itemView.findViewById(R.id.tvTeacherPhone);
        }
    }
}
