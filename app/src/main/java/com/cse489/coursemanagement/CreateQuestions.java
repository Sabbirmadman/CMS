package com.cse489.coursemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CreateQuestions extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private int numOfQue=0;
    private int index =0;
    private String sol = "";
    private TextView ShowQuestionNum;
    private DatabaseReference questionRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_questions);

        Intent E = getIntent();
        questionRef = FirebaseDatabase.getInstance().getReference().child("questions").child(E.getStringExtra("id"));

        ShowQuestionNum=findViewById(R.id.ShowQuestionNum);

        //stage 1
        findViewById(R.id.saveNumberOfQuestions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText inp = findViewById(R.id.numberOfQuestions);
                if(!TextUtils.isEmpty(inp.getText().toString().trim())) {
                    numOfQue = Integer.parseInt(inp.getText().toString().trim());
                    if (numOfQue <= 0) {
                        inp.setError("Input must be grater then 1");
                    } else {
                        findViewById(R.id.numberOfQuestionsParent).setVisibility(View.GONE);
                        findViewById(R.id.saveNumberOfQuestions).setVisibility(View.GONE);
                        inp.setVisibility(View.GONE);
                        findViewById(R.id.NumberOfQuestionsFleshScreen).setVisibility(View.GONE);
                    }
                }else{
                    inp.setError("Inter a value");
                }
            }
        });


//stage 2

        Spinner solSpinner = findViewById(R.id.spinnerSolSelector);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        solSpinner.setAdapter(adapter);
        solSpinner.setOnItemSelectedListener(this);


        ShowQuestionNum.setText("Question "+(index+1));



    findViewById(R.id.submitQunBtn).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (index < numOfQue) {
                TextInputEditText QuestionInput = findViewById(R.id.QuestionInput);
                TextInputEditText op1Input = findViewById(R.id.op1Input);
                TextInputEditText op2Input = findViewById(R.id.op2Input);
                TextInputEditText op3Input = findViewById(R.id.op3Input);
                TextInputEditText op4Input = findViewById(R.id.op4Input);

                if (TextUtils.isEmpty(QuestionInput.getText().toString().trim())) {
                    QuestionInput.setError("Please Enter value");
                }
                else if (TextUtils.isEmpty(op1Input.getText().toString().trim())) {
                    op1Input.setError("Please Enter value");
                }
                else if (TextUtils.isEmpty(op2Input.getText().toString().trim())) {
                    op2Input.setError("Please Enter value");
                }
                else if (TextUtils.isEmpty(op3Input.getText().toString().trim())) {
                    op3Input.setError("Please Enter value");
                }
                else if (TextUtils.isEmpty(op4Input.getText().toString().trim())) {
                    op4Input.setError("Please Enter value");
                }else {

                    System.out.println(QuestionInput.getText().toString().trim());

                    String qid = "q"+index;

                    HashMap MakeQuestions = new HashMap();
                    MakeQuestions.put("queId",qid);
                    MakeQuestions.put("question",QuestionInput.getText().toString());
                    MakeQuestions.put("op1",op1Input.getText().toString());
                    MakeQuestions.put("op2",op2Input.getText().toString());
                    MakeQuestions.put("op3",op3Input.getText().toString());
                    MakeQuestions.put("op4",op4Input.getText().toString());
                    MakeQuestions.put("sol",sol);

                    questionRef.child(qid).updateChildren(MakeQuestions).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull  Task task) {
                            if (task.isSuccessful()){

                                QuestionInput.setText("");
                                op1Input.setText("");
                                op2Input.setText("");
                                op3Input.setText("");
                                op4Input.setText("");

                                index++;
                                ShowQuestionNum.setText("Question "+(index+1));
                                Toast.makeText(getApplicationContext(), "Data stored successfully!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });








                }
            }
        }
    });















    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        sol = adapterView.getItemAtPosition(i).toString();


        Toast.makeText(adapterView.getContext(),sol,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}