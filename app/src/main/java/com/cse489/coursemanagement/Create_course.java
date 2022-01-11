package com.cse489.coursemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cse489.coursemanagement.Models.Course;
import com.cse489.coursemanagement.Models.Routine;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Create_course extends AppCompatActivity {
    private DatabaseReference courseRef;
    private DatabaseReference routineRef;
    private DatabaseReference CoPoRef;
    private TextView show;
    private Button createCourseBtn, cancelBtn;
    private TextInputEditText course_Id, name, credit, resource_id;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        show = findViewById(R.id.show);
        createCourseBtn = findViewById(R.id.createCourseButton);
        cancelBtn = findViewById(R.id.cancelButton);

        name = findViewById(R.id.CourseName);
        course_Id = findViewById(R.id.courseId);
        credit = findViewById(R.id.credit);
        resource_id = findViewById(R.id.recourse_id);

        mAuth = FirebaseAuth.getInstance();
        String currentUserId = mAuth.getCurrentUser().getUid();





        createCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name1 = name.getText().toString().trim();
                String course_id1 = course_Id.getText().toString().trim();
                String credit1 = credit.getText().toString().trim();
                String res_id = resource_id.getText().toString().trim();

                if (TextUtils.isEmpty(name1)) {
                    name.setError("Name is required");
                }
                if (TextUtils.isEmpty(course_id1)) {
                    name.setError("CourseId is required");
                }
                if (TextUtils.isEmpty(credit1)) {
                    course_Id.setError("Credit is required");
                } else {
                    if (TextUtils.isEmpty(res_id)) {
                        res_id = "";
                    }


                    courseRef = FirebaseDatabase.getInstance().getReference().child("course").child(course_id1);

                    HashMap courseInfo = new HashMap();
                    courseInfo.put("course_id",course_id1);
                    courseInfo.put("course_Name", name1);
                    courseInfo.put("course_Credit", credit1);
                    courseInfo.put("created_by", currentUserId);
                    courseInfo.put("desc","");
                    courseInfo.put("resource_id", res_id);
                    courseInfo.put("students", "");


                    courseRef.updateChildren(courseInfo).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull  Task task) {
                            if (task.isSuccessful()){

                                routineRef =FirebaseDatabase.getInstance().getReference().child("routine").child(course_id1);
                                HashMap routineInfo = new HashMap();
                                routineInfo.put("MW","");
                                routineInfo.put("SR","");
                                routineInfo.put("ST","");
                                routineInfo.put("TR","");
                                routineInfo.put("course_id",course_id1);

                                routineRef.updateChildren(routineInfo).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(getApplicationContext(), "Data stored successfully!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });



                                CoPoRef =FirebaseDatabase.getInstance().getReference().child("CoPo").child(course_id1);
                                HashMap CoPoInfo = new HashMap();
                                CoPoInfo.put("co1","");
                                CoPoInfo.put("co2","");
                                CoPoInfo.put("co3","");
                                CoPoInfo.put("co4","");
                                CoPoInfo.put("co5","");
                                CoPoInfo.put("co6","");
                                CoPoInfo.put("course_id",course_id1);
                                CoPoInfo.put("po1","");
                                CoPoInfo.put("po2","");
                                CoPoInfo.put("po3","");
                                CoPoInfo.put("po4","");
                                CoPoInfo.put("po5","");
                                CoPoInfo.put("po6","");

                                CoPoRef.updateChildren(CoPoInfo).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(getApplicationContext(), "Data stored successfully!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });



                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Data was not stored successfully!", Toast.LENGTH_SHORT).show();
                            }
                            finish();
                        }
                    });


                }

            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Create_course.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });


        String uid = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
//        show.setText(uid);

        System.out.println(uid);


    }
}