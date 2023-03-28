package com.example.newgensociety;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    CardView SocietyM_login, CommitteeM_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SocietyM_login = findViewById(R.id.btn_asSocietyMember);
        CommitteeM_Login = findViewById(R.id.btn_asComitteeMember);
        CommitteeM_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CommitteeM_LoginPage.class);
                startActivity(intent);
                finish();
            }
        });
        SocietyM_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SocityM_LoginPage.class);
                startActivity(intent);
                finish();
            }
        });
    }
}