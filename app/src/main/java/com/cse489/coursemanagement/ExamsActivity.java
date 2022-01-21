package com.cse489.coursemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cse489.coursemanagement.Models.Course;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExamsActivity extends AppCompatActivity {
    private DatabaseReference courseRef;
    private ArrayList<Course> courses = new ArrayList<>();

    private String type;
    private String currentUserId;
    private ListView myCourseListView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exams);

        myCourseListView = findViewById(R.id.courseListView);

        Intent E = getIntent();
        type=E.getStringExtra("type");
        currentUserId=E.getStringExtra("currentUserId");




        courseRef = FirebaseDatabase.getInstance().getReference().child("course");
        courseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courses.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Course c1 = postSnapshot.getValue(Course.class);
                    if (type.equals("Teacher") && c1.getCreated_by().toString().equals(currentUserId)) {
                        courses.add(postSnapshot.getValue(Course.class));


                    } else if (type.equals("Student")) {
                        String[] courseStudents = c1.getStudents().split(",");
                        for (String i : courseStudents) {
                            if (i.equals(currentUserId)) {
                                courses.add(postSnapshot.getValue(Course.class));
                            }

                        }


                    }

                }


                CourseListAdapter CourseListAdapter = new CourseListAdapter(ExamsActivity.this, R.layout.course_adopter_view_layout, courses);
                myCourseListView.setAdapter(CourseListAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myCourseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int iter, long l) {
                Course selectedCourse = courses.get(iter);
                if(type.equals("Teacher")){
                    Intent i = new Intent(ExamsActivity.this,CreateQuestions.class);
                    startActivity(i);
                }else{
                    Intent i = new Intent(ExamsActivity.this,GiveExamsActivity.class);
                    i.putExtra("user_id", currentUserId);
                    i.putExtra("id", selectedCourse.getCourse_id());
                    i.putExtra("name", selectedCourse.getCourse_Name());
                    i.putExtra("credit", selectedCourse.getCourse_Credit());
                    i.putExtra("created_by", selectedCourse.getCreated_by());
                    i.putExtra("students", selectedCourse.getStudents());
                    startActivity(i);
                }


            }
        });
























    }
}