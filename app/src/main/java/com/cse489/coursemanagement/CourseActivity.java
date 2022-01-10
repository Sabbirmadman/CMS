package com.cse489.coursemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Intent i = getIntent();
        String id = i.getStringExtra("user_id")+i.getStringExtra("id")+i.getStringExtra("name");
        TextView tv = findViewById(R.id.viewThings);
        tv.setText(id);







    }
}