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

public class InstanceAdapter extends RecyclerView.Adapter<InstanceAdapter.ViewHolder> {
    private Context context;
    private List<ClassInstance> instances;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ClassInstance instance);
    }

    public InstanceAdapter(Context context, List<ClassInstance> instances, OnItemClickListener listener) {
        this.context = context;
        this.instances = instances;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_instance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClassInstance instance = instances.get(position);
        holder.bind(instance, listener);
    }

    @Override
    public int getItemCount() {
        return instances.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text1;
        private TextView text2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.text1);
            text2 = itemView.findViewById(R.id.text2);
        }

        public void bind(final ClassInstance instance, final OnItemClickListener listener) {
            text1.setText("Date: " + instance.getDate());
            text2.setText("Teacher: " + instance.getTeacherName());
            itemView.setOnClickListener(v -> listener.onItemClick(instance));
        }
    }
}