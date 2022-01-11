package com.cse489.coursemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cse489.coursemanagement.Models.Routine;
import com.cse489.coursemanagement.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CourseActivity extends AppCompatActivity {
    private TextView courseIdTV;
    private TextView courseNameTv;
    private TextView courseInsTv;
    private TextView descTv;


    private ArrayList<User> users = new ArrayList<>();

    private DatabaseReference userRef1;
    private DatabaseReference routineRef;

    private User courseIns;
    private Routine routine;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Intent i = getIntent();
        String id = i.getStringExtra("user_id") + i.getStringExtra("name");


        courseIdTV = findViewById(R.id.courseIdTv);
        courseNameTv = findViewById(R.id.CourseNameTv);
        descTv = findViewById(R.id.descTv);


        courseIdTV.setText(i.getStringExtra("id"));
        courseNameTv.setText(i.getStringExtra("name") + " (" + i.getStringExtra("credit") + ")");
        descTv.setText(i.getStringExtra("desc"));

//get course ins info
        userRef1 = FirebaseDatabase.getInstance().getReference().child("users").child(i.getStringExtra("created_by"));
        userRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                courseIns=dataSnapshot.getValue(User.class);

                TextView insNameTv=findViewById(R.id.InsNameTv);
                TextView insEmailTv = findViewById(R.id.InsEmailTv);
                TextView InsPhoneTv = findViewById(R.id.InsPhoneTv);

                insNameTv.setText("Name : "+courseIns.getName());
                insEmailTv.setText("Email : "+courseIns.getEmail());
                InsPhoneTv.setText("Phone : "+courseIns.getPhonenumber());

            }

            //make models for Courses
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



//getting course routine
        routineRef = FirebaseDatabase.getInstance().getReference().child("routine").child(i.getStringExtra("id"));
        routineRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                routine =snapshot.getValue(Routine.class);
                TextView tv1 = findViewById(R.id.routineMWTv);
                tv1.setText("MW : "+routine.getMW());

                TextView tv2 = findViewById(R.id.routineSRTv);
                tv2.setText("SR : "+routine.getSR());

                TextView tv3 = findViewById(R.id.routineSTTv);
                tv3.setText("ST : "+routine.getST());

                TextView tv4 = findViewById(R.id.routineTRTv);
                tv4.setText("TR : "+routine.getTR());
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });













    }
}