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

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder> {

    private List<Teacher> teacherList;

    public TeacherAdapter(List<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teacher, parent, false);
        return new TeacherViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {
        Teacher currentTeacher = teacherList.get(position);
        holder.tvTeacherName.setText(currentTeacher.getName());
        holder.tvTeacherEmail.setText(currentTeacher.getEmail());
        holder.tvTeacherPhone.setText(currentTeacher.getPhone());
    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }

    public void updateData(List<Teacher> newTeachers) {
        this.teacherList.clear();
        this.teacherList.addAll(newTeachers);
        notifyDataSetChanged();
    }

    static class TeacherViewHolder extends RecyclerView.ViewHolder {
        TextView tvTeacherName;
        TextView tvTeacherEmail;
        TextView tvTeacherPhone;

        TeacherViewHolder(View itemView) {
            super(itemView);
            tvTeacherName = itemView.findViewById(R.id.tvTeacherName);
            tvTeacherEmail = itemView.findViewById(R.id.tvTeacherEmail);
            tvTeacherPhone = itemView.findViewById(R.id.tvTeacherPhone);
        }
    }
}
