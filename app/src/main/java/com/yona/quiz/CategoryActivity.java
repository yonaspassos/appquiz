package com.yona.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class CategoryActivity extends AppCompatActivity {

    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_category);
        Intent previousIntent = getIntent();
        username = previousIntent.getStringExtra("username");

        Button btnCategory1 = findViewById(R.id.btnCategory1);
        Button btnCategory2 = findViewById(R.id.btnCategory2);
        Button btnCategory3 = findViewById(R.id.btnCategory3);



        btnCategory1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQuestion(1);
            }
        });

        btnCategory2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQuestion(2);
            }
        });

        btnCategory3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQuestion(3);
            }
        });


    }

    private void showQuestion(Integer categoryId){
        Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
        intent.putExtra("category", categoryId.intValue());
        intent.putExtra("username", username);

        startActivity(intent);
    }
}
