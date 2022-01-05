package com.cse489.coursemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartingActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        Button studentLogin = findViewById(R.id.studentLogin);
        Button teacherLogin = findViewById(R.id.teacherLogin);



        teacherLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StartingActivity.this,TeacherRegistrationActivity.class);
                StartingActivity.this.startActivity(i);

            }
        });

        studentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StartingActivity.this,StudentRegistrationActivity.class);
                StartingActivity.this.startActivity(i);

            }
        });

        TextView backBtn = findViewById(R.id.footer);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartingActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });




    }
}