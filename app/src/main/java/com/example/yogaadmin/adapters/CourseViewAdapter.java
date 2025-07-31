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

public class CourseViewAdapter extends RecyclerView.Adapter<CourseViewAdapter.CourseViewHolder> {

    private final Context context;
    private final List<YogaCourse> courses;
    private final OnCourseActionsClickListener onCourseActionsClickListener;

    public interface OnCourseActionsClickListener {
        void onViewDetailsClick(YogaCourse course);
        void onManageInstancesClick(YogaCourse course);
        void onEditCourseClick(YogaCourse course);
        void onDeleteCourseClick(YogaCourse course);
    }

    public CourseViewAdapter(Context context, List<YogaCourse> courses, OnCourseActionsClickListener onCourseActionsClickListener) {
        this.context = context;
        this.courses = courses;
        this.onCourseActionsClickListener = onCourseActionsClickListener;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_course_view, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        YogaCourse course = courses.get(position);
        holder.bind(course, onCourseActionsClickListener);
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    static class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCourseName;
        private final TextView tvCourseDetails;
        private final Button btnViewDetails;
        private final Button btnManageInstances;
        private final Button btnEditCourse;
        private final Button btnDeleteCourse;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvCourseDetails = itemView.findViewById(R.id.tvCourseDetails);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);
            btnManageInstances = itemView.findViewById(R.id.btnManageInstances);
            btnEditCourse = itemView.findViewById(R.id.btnEditCourse);
            btnDeleteCourse = itemView.findViewById(R.id.btnDeleteCourse);
        }

        public void bind(final YogaCourse course, final OnCourseActionsClickListener listener) {
            tvCourseName.setText(course.getName());
            tvCourseDetails.setText(course.getDayOfWeek() + ", " + course.getTime());
            btnViewDetails.setOnClickListener(v -> listener.onViewDetailsClick(course));
            btnManageInstances.setOnClickListener(v -> listener.onManageInstancesClick(course));
            btnEditCourse.setOnClickListener(v -> listener.onEditCourseClick(course));
            btnDeleteCourse.setOnClickListener(v -> listener.onDeleteCourseClick(course));
        }
    }
}