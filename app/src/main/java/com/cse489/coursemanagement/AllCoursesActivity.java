package com.cse489.coursemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cse489.coursemanagement.Models.Course;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllCoursesActivity extends AppCompatActivity {

    private ArrayList<Course> courses = new ArrayList<>();
    private ListView myCourseListView;

    private String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private TextInputEditText searchInput;
    private Button searchBtn;

    String searchInputValue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_courses);


        myCourseListView = findViewById(R.id.courseListView);


        searchInput=findViewById(R.id.searchInput);
        searchBtn=findViewById(R.id.searchBtn);







        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchInputValue = searchInput.getText().toString();
                System.out.println("Find"+searchInputValue);








                DatabaseReference courseRef = FirebaseDatabase.getInstance().getReference().child("course");
                courseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        courses.clear();
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            Course c1 = postSnapshot.getValue(Course.class);

                            if(TextUtils.isEmpty(searchInputValue)){
                                courses.add(postSnapshot.getValue(Course.class));
                            }
                            else if( c1.getCourse_id().contains(searchInputValue)){
                                courses.add(postSnapshot.getValue(Course.class));
                            }

                        }


                        CourseListAdapter CourseListAdapter = new CourseListAdapter(AllCoursesActivity.this, R.layout.course_adopter_view_layout, courses);
                        myCourseListView.setAdapter(CourseListAdapter);

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




            }
        });








            DatabaseReference courseRef = FirebaseDatabase.getInstance().getReference().child("course");
            courseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    courses.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
//
                        courses.add(postSnapshot.getValue(Course.class));
                    }


                    CourseListAdapter CourseListAdapter = new CourseListAdapter(AllCoursesActivity.this, R.layout.course_adopter_view_layout, courses);
                    myCourseListView.setAdapter(CourseListAdapter);

                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });












        myCourseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int iter, long l) {
                Intent i = getIntent();
                Course selectedCourse = courses.get(iter);
                Intent intent = new Intent(AllCoursesActivity.this, CourseActivity.class);
                intent.putExtra("user_id",currentUserId);
                intent.putExtra("id", selectedCourse.getCourse_id());
                intent.putExtra("name", selectedCourse.getCourse_Name());
                intent.putExtra("credit", selectedCourse.getCourse_Credit());
                intent.putExtra("created_by", selectedCourse.getCreated_by());
                intent.putExtra("desc", selectedCourse.getDesc());
                System.out.println(selectedCourse.getResource_id());
                intent.putExtra("res_id", selectedCourse.getResource_id());
                intent.putExtra("students",selectedCourse.getStudents());
                intent.putExtra("type", i.getStringExtra("type"));
                startActivity(intent);
            }
        });














    }


    @Override
    protected void onResume() {
        super.onResume();


//getting courses

    }


}