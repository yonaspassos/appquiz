package com.yona.quiz;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private void playSoundWin() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.applause3);
        mediaPlayer.start();
    }

    private void playSoundLose() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.lose);
        mediaPlayer.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_result);

        Intent previousIntent = getIntent();
        Integer correctCount = previousIntent.getIntExtra("correctCount", 0);
        TextView result = findViewById(R.id.Result);
        result.setText(correctCount.toString());
        TextView resultText = findViewById(R.id.resultCount);
        resultText.setText(correctCount.toString());

        if(correctCount >= 4){
            playSoundWin();
            result.setText(correctCount.toString());
            resultText.setText("Parabens! Você manja de Netflix!");
        } else {
            playSoundLose();
            result.setText(correctCount.toString());
            resultText.setText("Parabens!Você tem vida social!");
        }
    }

    public void showMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
