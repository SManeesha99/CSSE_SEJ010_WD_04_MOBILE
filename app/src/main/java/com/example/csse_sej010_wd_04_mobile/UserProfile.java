package com.example.csse_sej010_wd_04_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserProfile extends AppCompatActivity {

    Button topupbtn,journeylistbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        topupbtn = (Button) findViewById(R.id.TopupbtnID);
        topupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserProfile.this,Recharge.class));
            }
        });

        journeylistbtn = (Button) findViewById(R.id.myJourneyListbtnID);
        journeylistbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserProfile.this,JourneyList.class));
            }
        });

    }
}