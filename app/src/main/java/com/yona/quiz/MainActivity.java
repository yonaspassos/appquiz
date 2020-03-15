package com.yona.quiz;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String username;
    private ListView rankingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        AdView mAdView;
        MobileAds.initialize(this, "ca-app-pub-1835076353634496~2849265011");
        mAdView = findViewById(R.id.adView);
        mAdView.setAdSize(AdSize.SMART_BANNER);
        mAdView.setAdUnitId("myAdUnitId");

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        rankingView = findViewById(R.id.listRanking);
        rankingView.setOnItemClickListener(new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                Ranking ranking_users = (Ranking) adapter.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), ShowRanking.class);
                intent.putExtra("user", ranking_users);
                startActivity(intent);
            }
        });


        Intent previousIntent = getIntent();
        username = previousIntent.getStringExtra("username");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference("results");
        Query queryRanking = dbRef.orderByValue();
        queryRanking.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Ranking> ranking = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Ranking score = new Ranking(postSnapshot.getKey(), postSnapshot.getValue().toString());
                    ranking.add(score);
                }
                Collections.reverse(ranking);
                ArrayAdapter<Ranking> adapter = new ArrayAdapter<>(
                        MainActivity.this, android.R.layout.simple_list_item_1,
                        ranking
                );
                rankingView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }

    public void showCategory(View view){
        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}
