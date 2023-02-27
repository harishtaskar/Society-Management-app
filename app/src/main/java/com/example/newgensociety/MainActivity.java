package com.example.newgensociety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button SocietyM_login, CommitteeM_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SocietyM_login = findViewById(R.id.btn_asSocietyMember);
        CommitteeM_Login = findViewById(R.id.btn_asComitteeMember);
        CommitteeM_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Committee_LoginPage.class);
                startActivity(intent);
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