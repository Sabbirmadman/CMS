package com.cse489.coursemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cse489.coursemanagement.Models.CoPo;
import com.cse489.coursemanagement.Models.Routine;
import com.cse489.coursemanagement.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class CourseActivity extends AppCompatActivity {
    private TextView courseIdTV;
    private TextView courseNameTv;
    private TextView courseInsTv;
    private TextView descTv;
    private TextView noticeBord;


    private Button courseEdit, EnrollBtn;


    private ArrayList<User> users = new ArrayList<>();

    private DatabaseReference userRef1;
    private DatabaseReference routineRef;
    private DatabaseReference copoRef;
    private DatabaseReference courseInfoRef;

    private User courseIns;
    private Routine routine;

    private Button setALarmBtn;

    private TextView tv1, ExamsDateTv;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;

    private String user, type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Intent i = getIntent();
        String id = i.getStringExtra("user_id") + i.getStringExtra("name");

        courseInfoRef = FirebaseDatabase.getInstance().getReference().child("course").child(i.getStringExtra("id"));

        courseIdTV = findViewById(R.id.courseIdTv);
        courseNameTv = findViewById(R.id.CourseNameTv);
        descTv = findViewById(R.id.descTv);
        courseEdit = findViewById(R.id.CourseEditTv);
        noticeBord = findViewById(R.id.noticeBordTv);
        EnrollBtn = findViewById(R.id.EntollBtn);
        ExamsDateTv = findViewById(R.id.ExamsDateTv);
        setALarmBtn = findViewById(R.id.setAlarmBtn);

        courseIdTV.setText(i.getStringExtra("id"));
        courseNameTv.setText(i.getStringExtra("name") + " (" + i.getStringExtra("credit") + ")");
        descTv.setText(i.getStringExtra("desc"));
        noticeBord.setText(i.getStringExtra("notice"));


        ExamsDateTv.setText(i.getStringExtra("examTime"));


        setALarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(i.getStringExtra("examTime"))) {


                    String[] timeDate = i.getStringExtra("examTime").split(":");

                    int hour = Integer.parseInt(timeDate[0]);
                    int minute = Integer.parseInt(timeDate[1]);


                    Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                    intent.putExtra(AlarmClock.EXTRA_HOUR, hour);
                    intent.putExtra(AlarmClock.EXTRA_MINUTES, minute);
                    intent.putExtra(AlarmClock.EXTRA_MESSAGE, i.getStringExtra("name") + " Exam alarm ");


                    if (hour <= 24 && minute <= 60 && !TextUtils.isEmpty(i.getStringExtra("examTime"))) {
                        startActivity(intent);
                    }

                }
            }
        });


//Enroll
        EnrollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap courseInfo = new HashMap();
                courseInfo.put("course_id", i.getStringExtra("id"));
                courseInfo.put("course_Name", i.getStringExtra("name"));
                courseInfo.put("course_Credit", i.getStringExtra("credit"));
                courseInfo.put("created_by", i.getStringExtra("created_by"));
                courseInfo.put("desc", i.getStringExtra("desc"));
                courseInfo.put("notice", i.getStringExtra("notice"));

                courseInfo.put("resource_id", i.getStringExtra("res_id"));
                courseInfo.put("students", i.getStringExtra("students") + "," + i.getStringExtra("user_id"));

                courseInfoRef.updateChildren(courseInfo).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        Toast.makeText(getApplicationContext(), "Course added!", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


//get course ins info
        userRef1 = FirebaseDatabase.getInstance().getReference().child("users").child(i.getStringExtra("created_by"));
        userRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {

                    courseIns = dataSnapshot.getValue(User.class);
                    System.out.println(courseIns);
                    System.out.println(i.getStringExtra("created_by"));
                    if (!courseIns.getId().equals(i.getStringExtra("user_id"))) {
                        courseEdit.setVisibility(View.GONE);
                    }
                    if (!i.getStringExtra("type").equals("Student")) {
                        EnrollBtn.setVisibility(View.GONE);

                    } else {
                        if (i.getStringExtra("students").contains(i.getStringExtra("user_id"))) {
                            EnrollBtn.setVisibility(View.GONE);
                        }
                    }


                    TextView insNameTv = findViewById(R.id.InsNameTv);
                    TextView insEmailTv = findViewById(R.id.InsEmailTv);
                    TextView InsPhoneTv = findViewById(R.id.InsPhoneTv);

                    insNameTv.setText("Name : " + courseIns.getName());
                    insEmailTv.setText("Email : " + courseIns.getEmail());
                    InsPhoneTv.setText("Phone : " + courseIns.getPhonenumber());


                } catch (Exception e) {
                    System.out.println(e);
                }

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
                Intent I = new Intent(CourseActivity.this, CourseUpdateActivity.class);
                I.putExtra("current_user", i.getStringExtra("user_id"));
                I.putExtra("id", i.getStringExtra("id"));
                I.putExtra("name", i.getStringExtra("name"));
                I.putExtra("credit", i.getStringExtra("credit"));
                I.putExtra("created_by", i.getStringExtra("created_by"));
                I.putExtra("desc", i.getStringExtra("desc"));
                I.putExtra("notice", i.getStringExtra("notice"));
                I.putExtra("examTime", i.getStringExtra("examTime"));
                System.out.println(i.getStringExtra("res_id"));
                I.putExtra("resource_id", i.getStringExtra("res_id"));
                I.putExtra("students", i.getStringExtra("students"));
                startActivity(I);


            }
        });


//getting course routine
        routineRef = FirebaseDatabase.getInstance().getReference().child("routine").child(i.getStringExtra("id"));
        routineRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                routine = snapshot.getValue(Routine.class);
                if (routine != null) {

                    tv1 = findViewById(R.id.routineMWTv);
                    tv1.setText(routine.getMW());

                    tv2 = findViewById(R.id.routineSRTv);
                    tv2.setText(routine.getSR());

                    tv3 = findViewById(R.id.routineSTTv);
                    tv3.setText(routine.getST());

                    tv4 = findViewById(R.id.routineTRTv);
                    tv4.setText(routine.getTR());

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //getting CoPo
        copoRef = FirebaseDatabase.getInstance().getReference().child("CoPo").child(i.getStringExtra("id"));

        copoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                try {


                    CoPo copo = snapshot.getValue(CoPo.class);


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
                    cotv1.setText("Co1 : " + copo.getCo1());
                    cotv2.setText("Co2 : " + copo.getCo2());
                    cotv3.setText("Co3 : " + copo.getCo3());
                    cotv4.setText("Co4 : " + copo.getCo4());
                    cotv5.setText("Co5 : " + copo.getCo5());
                    cotv6.setText("Co6 : " + copo.getCo6());
                    potv1.setText("Po1 : " + copo.getPo1());
                    potv2.setText("Po2 : " + copo.getPo2());
                    potv3.setText("Po3 : " + copo.getPo3());
                    potv4.setText("Po4 : " + copo.getPo4());
                    potv5.setText("Po5 : " + copo.getPo5());
                    potv6.setText("Po6 : " + copo.getPo6());

                } catch (Exception e) {

                    System.out.println(e);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}