package com.yona.quiz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class QuestionActivity extends AppCompatActivity {

    private Boolean isCorrect = false;
    private RadioGroup optionsList;
    private TextView txtDescription;
    private String correctAnswer;
    private ImageView cover;
    private ArrayList<String> questions;
    private Integer currentQuestion;
    private Integer correctCount = 0;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.content_question);

        Intent previousIntent = getIntent();
        Integer categoryId;
        categoryId = previousIntent.getIntExtra("category", 0);
        username = previousIntent.getStringExtra("username");

        questions = new ArrayList<>();

        if (categoryId == 1) {

            questions.add("quiz/questions/la_casa_de_papel/1");
            questions.add("quiz/questions/la_casa_de_papel/2");
            questions.add("quiz/questions/la_casa_de_papel/3");
            questions.add("quiz/questions/la_casa_de_papel/4");
            questions.add("quiz/questions/la_casa_de_papel/5");

        } else if (categoryId == 2) {
            questions.add("quiz/questions/lucifer/1");
            questions.add("quiz/questions/lucifer/2");
            questions.add("quiz/questions/lucifer/3");
            questions.add("quiz/questions/lucifer/4");
            questions.add("quiz/questions/lucifer/5");
        } else if (categoryId == 3) {
            questions.add("quiz/questions/you/1");
            questions.add("quiz/questions/you/2");
            questions.add("quiz/questions/you/3");
            questions.add("quiz/questions/you/4");
            questions.add("quiz/questions/you/5");
        }


        optionsList = findViewById(R.id.radioGroup);
        txtDescription = findViewById(R.id.description);
        cover = findViewById(R.id.cover);



        optionsList.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton selectedOption = findViewById(checkedId);
                isCorrect = (selectedOption.getText() == correctAnswer);
            }
        });

        showQuestion(0);

    }

    public void showNextQuestion(View view){
        Integer checkedRadio = optionsList.getCheckedRadioButtonId();
        if (checkedRadio == -1) {
            Toast.makeText(this,"Selecione uma opção para continuar",Toast.LENGTH_LONG).show();
        } else if (currentQuestion < questions.size() - 1){
            if(isCorrect == true){
                correctCount += 1;
            }
            showQuestion(++currentQuestion);
        } else {
            Intent intent = new Intent(this, ResultActivity.class);
            if (isCorrect == true) {
                intent.putExtra("correctCount", ++correctCount);
            } else {
                intent.putExtra("correctCount", correctCount);
            }
            intent.putExtra("username", username);
            startActivity(intent);
        }
    }

    public void showQuestion(Integer questionNumber){
        currentQuestion = questionNumber;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference(questions.get(questionNumber));
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    URL url = new URL(dataSnapshot.child("cover_image").getValue().toString());
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    cover.setImageBitmap(bmp);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                txtDescription.setText(dataSnapshot.child("description").getValue().toString());


                RadioButton button;

                optionsList.removeAllViews();

                for (DataSnapshot postSnapshot : dataSnapshot.child("options").getChildren()) {
                    button = new RadioButton(getApplicationContext());
                    button.setText(postSnapshot.getKey());
                    optionsList.addView(button);
                    if (postSnapshot.getValue().toString() == "true") {
                        correctAnswer = postSnapshot.getKey();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}