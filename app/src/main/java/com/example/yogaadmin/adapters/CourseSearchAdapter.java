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

public class CourseSearchAdapter extends RecyclerView.Adapter<CourseSearchAdapter.CourseViewHolder> {

    private List<YogaCourse> courseList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(YogaCourse course);
    }

    public CourseSearchAdapter(List<YogaCourse> courseList, OnItemClickListener listener) {
        this.courseList = courseList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_search, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        YogaCourse currentCourse = courseList.get(position);
        holder.courseNameTextView.setText(currentCourse.getName());
        holder.teacherNameTextView.setText("Taught by " + currentCourse.getTeacherName());
        holder.dayOfWeekTextView.setText("on " + currentCourse.getDayOfWeek());
        holder.timeTextView.setText("at " + currentCourse.getTime());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(currentCourse));
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView courseNameTextView;
        TextView teacherNameTextView;
        TextView dayOfWeekTextView;
        TextView timeTextView;

        CourseViewHolder(View itemView) {
            super(itemView);
            courseNameTextView = itemView.findViewById(R.id.courseNameTextView);
            teacherNameTextView = itemView.findViewById(R.id.teacherNameTextView);
            dayOfWeekTextView = itemView.findViewById(R.id.dayOfWeekTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
        }
    }
}
