package com.example.yogaadmin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.yogaadmin.R;
import com.example.yogaadmin.models.ClassInstance;
import java.util.List;

/**
 * InstanceAdapter - RecyclerView adapter for displaying class instances
 * 
 * This adapter provides an interface for displaying class instances in a RecyclerView.
 * It shows the date and teacher information for each class instance and handles
 * click events for user interaction.
 * 
 * Features:
 * - Display class instance date and teacher information
 * - Click handling for instance selection
 * - Simple, clean layout for instance items
 * - Efficient view holder pattern implementation
 * - Callback interface for handling instance selection
 * 
 * This adapter is used in activities that need to display a list of class instances,
 * such as when viewing instances for a specific course.
 */
public class InstanceAdapter extends RecyclerView.Adapter<InstanceAdapter.ViewHolder> {
    
    // Context for inflating layouts and accessing resources
    private Context context;
    
    // Data source containing all class instances to display
    private List<ClassInstance> instances;
    
    // Callback interface for handling instance item clicks
    private OnItemClickListener listener;

    /**
     * Interface for handling instance item clicks
     * Provides a callback method when an instance item is selected
     */
    public interface OnItemClickListener {
        /**
         * Called when an instance item is clicked
         * @param instance The class instance that was clicked
         */
        void onItemClick(ClassInstance instance);
    }

    /**
     * Constructor for InstanceAdapter
     * 
     * @param context The context for inflating layouts
     * @param instances The list of class instances to display
     * @param listener The callback interface for handling instance clicks
     */
    public InstanceAdapter(Context context, List<ClassInstance> instances, OnItemClickListener listener) {
        this.context = context;
        this.instances = instances;
        this.listener = listener;
    }

    /**
     * Creates a new ViewHolder for displaying instance items
     * Inflates the layout for individual instance items
     * 
     * @param parent The ViewGroup into which the new View will be added
     * @param viewType The view type of the new View
     * @return A new ViewHolder that holds a View of the given view type
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_instance, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Binds instance data to the ViewHolder at the specified position
     * Sets up the display and click listener for each instance item
     * 
     * @param holder The ViewHolder to bind data to
     * @param position The position of the item in the data set
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClassInstance instance = instances.get(position);
        holder.bind(instance, listener);
    }

    /**
     * Returns the total number of items in the data set
     * 
     * @return The number of instances in the list
     */
    @Override
    public int getItemCount() {
        return instances.size();
    }

    /**
     * ViewHolder class for holding references to views in each instance item
     * Implements the ViewHolder pattern for efficient view recycling
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        
        // UI Components for displaying instance information
        private TextView text1; // Date display
        private TextView text2; // Teacher display

        /**
         * Constructor for ViewHolder
         * Initializes all view references from the item layout
         * 
         * @param itemView The view for this ViewHolder
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            
            // Initialize text views for instance information
            text1 = itemView.findViewById(R.id.text1);
            text2 = itemView.findViewById(R.id.text2);
        }

        /**
         * Binds instance data to the views and sets up click listener
         * Displays instance information and configures item click handling
         * 
         * @param instance The instance data to display
         * @param listener The callback interface for handling item clicks
         */
        public void bind(final ClassInstance instance, final OnItemClickListener listener) {
            // Display instance information
            text1.setText("Date: " + instance.getDate());
            text2.setText("Teacher: " + instance.getTeacherName());
            
            // Set up click listener for the entire item
            itemView.setOnClickListener(v -> listener.onItemClick(instance));
        }
    }
}