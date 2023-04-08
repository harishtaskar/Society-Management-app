package com.example.newgensociety;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CommitteeM_showHall extends AppCompatActivity {

    ImageView AddHall;
    TextView Request;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_mshow_hall);

         Request = findViewById(R.id.Committee_Hall_Request);
         AddHall = findViewById(R.id.Committee_Hall_Add);

         Request.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

             }
         });

         AddHall.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(CommitteeM_showHall.this,CommitteeM_AddHall.class);
                 startActivity(intent);
             }
         });
    }
}