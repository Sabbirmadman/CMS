package com.cse489.coursemanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cse489.coursemanagement.Models.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseListAdapter extends ArrayAdapter<Course> {

    private static final String TAG = "CourseListAdapter";
    private Context mContext;
    int mResource;

    public CourseListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Course> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String id = getItem(position).getCourse_id();
        String credit = getItem(position).getCourse_Credit();
        String createdBy = getItem(position).getCreated_by();
        String name = getItem(position).getCourse_Name();
        String res_id = getItem(position).getResource_id();


        Course course = new Course(id, name, credit, createdBy, res_id);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tv1 = (TextView) convertView.findViewById(R.id.tv1);
        TextView tv2 = (TextView) convertView.findViewById(R.id.tv2);
        TextView tv3 = (TextView) convertView.findViewById(R.id.tv3);

        tv1.setText(id);
        tv2.setText(name);
        tv3.setText("credit : "+credit);


        return convertView;
    }
}
