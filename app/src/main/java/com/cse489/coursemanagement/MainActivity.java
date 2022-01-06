package com.cse489.coursemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cse489.coursemanagement.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private TextView ShowData;
    private DatabaseReference userRef;
    private DatabaseReference userRef1;
    private Button logout;



    private TextView emailTV;
    private TextView nameTV;
    private TextView phoneTV;
    private TextView typeTV;



    private ProgressDialog loader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logout = findViewById(R.id.logoutBtn);
//        ShowData = findViewById(R.id.showUserData);


        emailTV = findViewById(R.id.showEmail);
        nameTV = findViewById(R.id.showName);
        phoneTV = findViewById(R.id.showPhone);
        typeTV = findViewById(R.id.showType);









        ArrayList<User> users = new ArrayList<>();


        userRef = FirebaseDatabase.getInstance().getReference().child("users").child(
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        );


        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {


                    String name = snapshot.child("name").getValue().toString();
                    String Email = snapshot.child("email").getValue().toString();
                    String type = snapshot.child("type").getValue().toString();
                    String Phone = snapshot.child("phonenumber").getValue().toString();


                    emailTV.setText(Email);
                    nameTV.setText(name);
                    phoneTV.setText(Phone);
                    typeTV.setText(type);




                }

            }

            //9--------------------------------------------------
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        userRef1 = FirebaseDatabase.getInstance().getReference().child("users");

        userRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    users.add(postSnapshot.getValue(User.class));

                }
                String values = "";
                for (User u : users) {
                    values = values + " " + u.getEmail();
                }
//                ShowData.setText(values);
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }
//make models for Courses
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
}