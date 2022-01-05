package com.cse489.coursemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class TeacherRegistrationActivity extends AppCompatActivity {

    private TextInputEditText registerFullName, registerRegisterId, registerPhoneNo, registerEmail, registerPassword;
    private Button registerBtn;
    private ProgressDialog loader;

    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_registration);


        TextView backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeacherRegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        registerFullName = findViewById(R.id.registerFullName);
        registerRegisterId = findViewById(R.id.registerIdNumber);
        registerPhoneNo = findViewById(R.id.registerPhoneNumber);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        registerBtn = findViewById(R.id.registerButton);
        loader = new ProgressDialog(this);


        mAuth = FirebaseAuth.getInstance();


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = registerEmail.getText().toString().trim();
                String password = registerPassword.getText().toString().trim();
                String fullName = registerFullName.getText().toString().trim();
                String idNumber = registerRegisterId.getText().toString().trim();
                String phoneNumber = registerPhoneNo.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    registerEmail.setError("Email is required");
                }
                if (TextUtils.isEmpty(password)) {
                    registerPassword.setError("password is required");
                }
                if (TextUtils.isEmpty(fullName)) {
                    registerFullName.setError("Name is required");
                }

                if (TextUtils.isEmpty(idNumber)) {
                    registerRegisterId.setError("id is required");
                }
                if (TextUtils.isEmpty(phoneNumber)) {
                    registerPhoneNo.setError("Phone Number is required");
                } else {

                    loader.setMessage("Registering you as a teacher");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();


                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                String error = task.getException().toString();
                                Toast.makeText(getApplicationContext(), "Error "+error, Toast.LENGTH_SHORT).show();
                            }else {
                                String currentUserId = mAuth.getCurrentUser().getUid();
                                userDatabaseRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);

                                HashMap userInfo = new HashMap();
                                userInfo.put("id", currentUserId);
                                userInfo.put("name", fullName);
                                userInfo.put("email", email);
                                userInfo.put("idnumber", idNumber);
                                userInfo.put("phonenumber", phoneNumber);
                                userInfo.put("type", "Teacher");
                                userInfo.put("search", "Teacher"+email);


                                userDatabaseRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if(task.isSuccessful()){

                                            Toast.makeText(getApplicationContext(), "Data stored successfully!", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(getApplicationContext(), "Data was not stored successfully!", Toast.LENGTH_SHORT).show();
                                        }
                                        finish();
                                    }
                                });


                                Intent intent = new Intent(TeacherRegistrationActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                loader.dismiss();


                            }
                        }
                    });

                }


            }
        });


    }
}