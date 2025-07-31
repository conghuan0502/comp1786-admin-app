package com.example.yogaadmin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yogaadmin.R;
import com.example.yogaadmin.models.YogaCourse;

import java.util.List;

public class CourseAdapter extends BaseAdapter {
    private Context context;
    private List<YogaCourse> courses;
    private LayoutInflater inflater;

    public CourseAdapter(Context context, List<YogaCourse> courses) {
        this.context = context;
        this.courses = courses;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return courses.size();
    }

    @Override
    public Object getItem(int position) {
        return courses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return courses.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        YogaCourse course = courses.get(position);

        TextView text1 = convertView.findViewById(android.R.id.text1);
        TextView text2 = convertView.findViewById(android.R.id.text2);

        text1.setText(course.getName());
        text2.setText(course.getDayOfWeek() + " " + course.getTime() +
                " • " + course.getDuration() + " min • " + course.getMaxCapacity() + " people");

        return convertView;
    }
}