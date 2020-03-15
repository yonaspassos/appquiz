package com.yona.quiz;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
        String user = previousIntent.getStringExtra("username");
        TextView result = findViewById(R.id.Result);
        result.setText(correctCount.toString());
        TextView resultText = findViewById(R.id.resultCount);
        resultText.setText(correctCount.toString());

        if (correctCount >= 4) {
            playSoundWin();
            result.setText(correctCount.toString());
            resultText.setText("Parabens! Você conhece muito sobre Netflix!");
        } else {
            playSoundLose();
            result.setText(correctCount.toString());
            resultText.setText("Continue assim, na próxima você consegue!");
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference results = database.getReference("results");
        results.child(user).setValue(correctCount);

    }


        public void showMain(View view){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }



}
