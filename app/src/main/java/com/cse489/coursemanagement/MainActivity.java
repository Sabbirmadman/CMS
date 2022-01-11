package com.cse489.coursemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cse489.coursemanagement.Models.Course;
import com.cse489.coursemanagement.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    private TextView ShowData;
    private DatabaseReference userRef;
    private DatabaseReference userRef1;
    private DatabaseReference courseRef;
    private DatabaseReference studentCourseRef;



    private Button logout;
    private Button createCourses;

    private TextView emailTV;
    private TextView nameTV;
    private TextView phoneTV;
    private TextView typeTV;

    private ListView myCourseListView;



    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Course> courses = new ArrayList<>();
    private HashMap <String, String> stdCourse = new HashMap<String, String>();
    String type;





    String p;

    private ProgressDialog loader;

    //current User Id
    private String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logout = findViewById(R.id.logoutBtn);


        //list view shows my courses
        myCourseListView = findViewById(R.id.courseListView);


        emailTV = findViewById(R.id.showEmail);
        nameTV = findViewById(R.id.showName);
        phoneTV = findViewById(R.id.showPhone);
        typeTV = findViewById(R.id.showType);
        createCourses = findViewById(R.id.createCourse);




//user gmail
        String uid = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
        System.out.println("uid=" + uid);

//userId
        userRef = FirebaseDatabase.getInstance().getReference().child("users").child(
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        );




        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.print("Hi I am working");

                if (snapshot.exists()) {


                    String name = snapshot.child("name").getValue().toString();
                    String Email = snapshot.child("email").getValue().toString();
                    type = snapshot.child("type").getValue().toString();
                    String Phone = snapshot.child("phonenumber").getValue().toString();


                    emailTV.setText(Email);
                    nameTV.setText(name);
                    phoneTV.setText(Phone);
                    typeTV.setText(type);


                    //removing create btn for user student
                    if (type.equals("Student")) {
                        createCourses.setVisibility(View.GONE);
                    }else {
                        //teacher related stuff invisible
                    }


                }

            }

            //Have to show all the courses on the adopter
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                emailTV.setText("ERR");
            }
        });

//get all the users
        userRef1 = FirebaseDatabase.getInstance().getReference().child("users");
        userRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    users.add(postSnapshot.getValue(User.class));

                }
                String values = "";
                for (User u : users) {
                    values = values + " " + u.getPhonenumber();
                }

//                ShowData.setText(values);
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }

            //make models for Courses
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });









        myCourseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Course selectedCourse = courses.get(i);
                Intent intent = new Intent(MainActivity.this, CourseActivity.class);
                intent.putExtra("user_id",currentUserId);
                intent.putExtra("id", selectedCourse.getCourse_id());
                intent.putExtra("name", selectedCourse.getCourse_Name());
                intent.putExtra("credit", selectedCourse.getCourse_Credit());
                intent.putExtra("created_by", selectedCourse.getCreated_by());
                intent.putExtra("desc", selectedCourse.getDesc());
                intent.putExtra("res_id", selectedCourse.getResource_id());
                intent.putExtra("students",selectedCourse.getStudents());
                startActivity(intent);
            }
        });




        Button allCourse = findViewById(R.id.allCourse);

        allCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,AllCoursesActivity.class);
                startActivity(i);
            }
        });


        createCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Create_course.class);
                startActivity(i);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);

            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();



//getting courses
        courseRef = FirebaseDatabase.getInstance().getReference().child("course");
        courseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courses.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Course c1 = postSnapshot.getValue(Course.class);
                    if(type.equals("Teacher")&&c1.getCreated_by().toString().equals(currentUserId)){
                        courses.add(postSnapshot.getValue(Course.class));


                    }else if (type.equals("Student")){
                        String[] courseStudents =  c1.getStudents().split(",");
                        for(String i : courseStudents){
                            if(i.equals(currentUserId)){
                                courses.add(postSnapshot.getValue(Course.class));
                            }

                        }


                    }

                }


                CourseListAdapter CourseListAdapter = new CourseListAdapter(MainActivity.this, R.layout.course_adopter_view_layout, courses);
                myCourseListView.setAdapter(CourseListAdapter);

                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}