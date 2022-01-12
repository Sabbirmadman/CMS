package com.cse489.coursemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cse489.coursemanagement.Models.Routine;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CourseUpdateActivity extends AppCompatActivity {


    private TextInputEditText NoticeField,  changeCourseName, ChangeCourseCredit, DescUpdateField, MWinputField, SRinputField, STinputField, TRinputField, ExamDateField;
    private Button CourseEditTv,CourseDelete;

    private DatabaseReference courseInfoRef;
    private DatabaseReference routineRef;


    private Routine routine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_update);

        Intent I = getIntent();

//firebase references
        courseInfoRef = FirebaseDatabase.getInstance().getReference().child("course").child(I.getStringExtra("id"));
        routineRef = FirebaseDatabase.getInstance().getReference().child("routine").child(I.getStringExtra("id"));


        changeCourseName = findViewById(R.id.CourseNameChangeField);
        ChangeCourseCredit = findViewById(R.id.CourseCreditChangeField);
        NoticeField = findViewById(R.id.NoticeUpdateField);
        DescUpdateField = findViewById(R.id.DescUpdateField);
        MWinputField = findViewById(R.id.MWinputField);
        SRinputField = findViewById(R.id.SRinputField);
        STinputField = findViewById(R.id.STinputField);
        TRinputField = findViewById(R.id.TRinputField);
        ExamDateField = findViewById(R.id.ExamDateField);
        //btn
        CourseEditTv = findViewById(R.id.CourseEditTv);


        //assign value

        changeCourseName.setText(I.getStringExtra("name"));
        ChangeCourseCredit.setText(I.getStringExtra("credit"));
        NoticeField.setText(I.getStringExtra("notice"));
        DescUpdateField.setText(I.getStringExtra("desc"));





        routineRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                routine =snapshot.getValue(Routine.class);
                MWinputField.setText(routine.getMW());
                SRinputField.setText(routine.getSR());
                TRinputField.setText(routine.getST());
                ExamDateField.setText(routine.getTR());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        //get all the input from Course
        //userProfile is also needed
        //A way to add courses for students
        //extras

//btn Action
        CourseEditTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap courseInfo = new HashMap();
                courseInfo.put("course_id",I.getStringExtra("id"));
                courseInfo.put("course_Name", changeCourseName.getText().toString());
                courseInfo.put("course_Credit", ChangeCourseCredit.getText().toString());
                courseInfo.put("created_by", I.getStringExtra("created_by"));
                courseInfo.put("desc",I.getStringExtra("desc"));
                courseInfo.put("notice", NoticeField.getText().toString());
                courseInfo.put("resource_id", I.getStringExtra("resource_id"));
                courseInfo.put("students", I.getStringExtra("students"));


                courseInfoRef.updateChildren(courseInfo).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {

                        HashMap routineInfo = new HashMap();
                        routineInfo.put("MW",MWinputField.getText().toString());
                        routineInfo.put("SR",SRinputField.getText().toString());
                        routineInfo.put("ST",STinputField.getText().toString());
                        routineInfo.put("TR",TRinputField.getText().toString());
                        routineInfo.put("course_id",I.getStringExtra("id"));



                        routineRef.updateChildren(routineInfo).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Data updated successfully!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                        Intent i = new Intent(CourseUpdateActivity.this,MainActivity.class);
                        startActivity(i);
                    }
                });
            }
        });

    }
}