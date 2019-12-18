package com.yona.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Question4Activity extends AppCompatActivity {

    private boolean isCorrect = false;
    private RadioGroup optionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_question4);

        optionsList = findViewById(R.id.radioGroup);

        optionsList.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButton3) {
                    isCorrect = true;
                } else {
                    isCorrect = false;
                }
            }
        });
    }

    public void showNextQuestion(View view) {
        Intent previousIntent = getIntent();
        Integer correctCount = previousIntent.getIntExtra("correctCount", 0);
        Integer checkedRadio = optionsList.getCheckedRadioButtonId();
        if (checkedRadio == -1) {
            Toast.makeText(this, "Selecione uma opção para continuar", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, Question5Activity.class);
            if (isCorrect == true) {
                intent.putExtra("correctCount", ++correctCount);
            } else {
                intent.putExtra("correctCount", correctCount);
            }
            startActivity(intent);
        }
    }
}
