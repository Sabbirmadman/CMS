package com.cse489.coursemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cse489.coursemanagement.Models.CoPo;
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

    private Button courseEdit;


    private ArrayList<User> users = new ArrayList<>();

    private DatabaseReference userRef1;
    private DatabaseReference routineRef;
    private DatabaseReference copoRef;

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
        courseEdit = findViewById(R.id.CourseEditTv);


        courseIdTV.setText(i.getStringExtra("id"));
        courseNameTv.setText(i.getStringExtra("name") + " (" + i.getStringExtra("credit") + ")");
        descTv.setText(i.getStringExtra("desc")+i.getStringExtra("students"));

















//get course ins info
        userRef1 = FirebaseDatabase.getInstance().getReference().child("users").child(i.getStringExtra("created_by"));
        userRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                courseIns=dataSnapshot.getValue(User.class);
                if(!courseIns.getId().equals(i.getStringExtra("user_id")) ){
                    courseEdit.setVisibility(View.GONE);
                }

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




    //CourseEditBtn
        courseEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(CourseActivity.this,CourseUpdateActivity.class);
                startActivity(I);
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
        //getting CoPo
        copoRef = FirebaseDatabase.getInstance().getReference().child("CoPo").child(i.getStringExtra("id"));

        copoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                CoPo copo = snapshot.getValue(CoPo.class);
//                TextView copotv = findViewById(R.id.coPoTv);
//                copotv.setText(copo.getCo1());

                TextView cotv1 = findViewById(R.id.copo1);
                TextView cotv2 = findViewById(R.id.copo2);
                TextView cotv3 = findViewById(R.id.copo3);
                TextView cotv4 = findViewById(R.id.copo4);
                TextView cotv5 = findViewById(R.id.copo5);
                TextView cotv6 = findViewById(R.id.copo6);
                TextView potv1 = findViewById(R.id.copo7);
                TextView potv2 = findViewById(R.id.copo8);
                TextView potv3 = findViewById(R.id.copo9);
                TextView potv4 = findViewById(R.id.copo10);
                TextView potv5 = findViewById(R.id.copo11);
                TextView potv6 = findViewById(R.id.copo12);

                //put co po on a grid view
                cotv1.setText("Co1 : "+copo.getCo1());
                cotv2.setText("Co2 : "+copo.getCo2());
                cotv3.setText("Co3 : "+copo.getCo3());
                cotv4.setText("Co4 : "+copo.getCo4());
                cotv5.setText("Co5 : "+copo.getCo5());
                cotv6.setText("Co6 : "+copo.getCo6());
                potv1.setText("Po1 : "+copo.getPo1());
                potv2.setText("Po2 : "+copo.getPo2());
                potv3.setText("Po3 : "+copo.getPo3());
                potv4.setText("Po4 : "+copo.getPo4());
                potv5.setText("Po5 : "+copo.getPo5());
                potv6.setText("Po6 : "+copo.getPo6());












            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });









    }
}