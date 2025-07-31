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

public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.CourseViewHolder> {

    private final Context context;
    private final List<YogaCourse> courses;
    private final OnCourseClickListener onCourseClickListener;

    public interface OnCourseClickListener {
        void onCourseClick(YogaCourse course);
    }

    public CourseRecyclerAdapter(Context context, List<YogaCourse> courses, OnCourseClickListener onCourseClickListener) {
        this.context = context;
        this.courses = courses;
        this.onCourseClickListener = onCourseClickListener;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_course_manage, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        YogaCourse course = courses.get(position);
        holder.bind(course, onCourseClickListener);
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    static class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCourseName;
        private final TextView tvCourseDescription;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvCourseDescription = itemView.findViewById(R.id.tvCourseDescription);
        }

        public void bind(final YogaCourse course, final OnCourseClickListener listener) {
            tvCourseName.setText(course.getName());
            tvCourseDescription.setText(course.getDescription());
            itemView.setOnClickListener(v -> listener.onCourseClick(course));
        }
    }
}