package com.example.newgensociety;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SocityM_LoginPage extends AppCompatActivity {

    EditText Email;
    EditText password;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socity_mlogin_page);
        Email = findViewById(R.id.Society_Login_editT_Email);
        password = findViewById(R.id.Society_Login_editT_Pass);
        loginButton = findViewById(R.id.Society_loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Email.getText().toString().equals("user") && password.getText().toString().equals("1234")) {
                    Toast.makeText(SocityM_LoginPage.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SocityM_LoginPage.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}