package com.cse489.coursemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.cse489.coursemanagement.Models.Questions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class GiveExamsActivity extends AppCompatActivity {


    private DatabaseReference questionRef;

    private ArrayList<Questions> questions = new ArrayList<>();


    private TextView questionBtn, SelectedAns;
    private Button option1Btn;
    private Button option2Btn;
    private Button option3Btn;
    private Button option4Btn;
    private Button SubmitAnsBtn;

    private String Answer = "";
    private String QunAnswer = "";


    private int index = 1;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_exams);

        Intent E = getIntent();




        questionBtn = findViewById(R.id.question);
        option1Btn = findViewById(R.id.op1);
        option2Btn = findViewById(R.id.op2);
        option3Btn = findViewById(R.id.op3);
        option4Btn = findViewById(R.id.op4);
        SubmitAnsBtn = findViewById(R.id.submitQun);
        SelectedAns = findViewById(R.id.selectedAns);


        questionRef = FirebaseDatabase.getInstance().getReference().child("questions").child(E.getStringExtra("id"));

        questionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Questions q1 = postSnapshot.getValue(Questions.class);
                    questions.add(q1);
                }
                if (questions.size() > 0) {

                    System.out.println(questions.get(0).getQuestion());


                    questionBtn.setText(questions.get(0).getQuestion());
                    option1Btn.setText(questions.get(0).getOp1());
                    option2Btn.setText(questions.get(0).getOp2());
                    option3Btn.setText(questions.get(0).getOp3());
                    option4Btn.setText(questions.get(0).getOp4());
                    QunAnswer = questions.get(0).getSol();

                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                    option1Btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SelectedAns.setText(option1Btn.getText().toString());
                            Answer = "op1";
                        }
                    });
                    option2Btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SelectedAns.setText(option2Btn.getText().toString());
                            Answer = "op2";
                        }
                    });

                    option3Btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SelectedAns.setText(option3Btn.getText().toString());
                            Answer = "op3";
                        }
                    });

                    option4Btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SelectedAns.setText(option4Btn.getText().toString());
                            Answer = "op4";
                        }
                    });


                    SubmitAnsBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (Answer.equals("")) {
                                SelectedAns.setText("Please Select answer");
                            } else {
                                System.out.println(QunAnswer+" "+Answer);
                                if (QunAnswer.equals(Answer)) {
                                    Answer="";
                                    score++;
                                }

                                if (index < questions.size()) {

                                    questionBtn.setText(questions.get(index).getQuestion());
                                    option1Btn.setText(questions.get(index).getOp1());
                                    option2Btn.setText(questions.get(index).getOp2());
                                    option3Btn.setText(questions.get(index).getOp3());
                                    option4Btn.setText(questions.get(index).getOp4());
                                    QunAnswer = questions.get(index).getSol();


                                    System.out.println(questions.get(index).getQuestion());
                                    index++;
                                    Answer = "";
                                } else {
                                    questionBtn.setVisibility(View.GONE);
                                    option1Btn.setVisibility(View.GONE);
                                    option2Btn.setVisibility(View.GONE);
                                    option3Btn.setVisibility(View.GONE);
                                    option4Btn.setVisibility(View.GONE);
                                    SubmitAnsBtn.setVisibility(View.GONE);

                                    TextView tv = findViewById(R.id.QuestionHeaderTxt);
                                    tv.setText("Your Score is " + score);
                                    SelectedAns.setText("");
                                }
                            }


                        }
                    });

                }else{
                    questionBtn.setVisibility(View.GONE);
                    option1Btn.setVisibility(View.GONE);
                    option2Btn.setVisibility(View.GONE);
                    option3Btn.setVisibility(View.GONE);
                    option4Btn.setVisibility(View.GONE);
                    SubmitAnsBtn.setVisibility(View.GONE);
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    TextView tv = findViewById(R.id.QuestionHeaderTxt);
                    tv.setText("No Questions set yet");
                    SelectedAns.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error);
            }

        });


    }


    @Override
    protected void onResume() {
        super.onResume();


    }


}