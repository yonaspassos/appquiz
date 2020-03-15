package com.yona.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ShowRanking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ranking);
        Intent intent = getIntent();
        Ranking ranking = (Ranking) intent.getSerializableExtra("user");

        TextView txtName = findViewById(R.id.user_txt);
        TextView txtScore = findViewById(R.id.score_txt);

        txtName.setText(ranking.getName());
        txtScore.setText(ranking.getScore());

    }
}
