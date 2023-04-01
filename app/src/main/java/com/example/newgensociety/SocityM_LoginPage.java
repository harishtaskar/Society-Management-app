package com.example.newgensociety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SocityM_LoginPage extends AppCompatActivity {

    EditText Email;
    TextView SignupText;
    FirebaseAuth mAuth;
    EditText password;
    Button loginButton;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser CurrentUser = mAuth.getCurrentUser();
        if(CurrentUser != null){
            Intent intent = new Intent(getApplicationContext(),SocietyM_HomePage.class);
            startActivity(intent);
            finish();
        }
    }
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socity_mlogin_page);
        mAuth = FirebaseAuth.getInstance();
        Email = findViewById(R.id.Society_Login_editT_Email);
        password = findViewById(R.id.Society_Login_editT_Pass);
        loginButton = findViewById(R.id.Society_loginButton);
        SignupText = findViewById(R.id.signupText);



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String String_email = String.valueOf(Email.getText());
                String String_password = String.valueOf(password.getText());

                    mAuth.signInWithEmailAndPassword(String_email, String_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(),"Login Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(),SocietyM_HomePage.class);
                                        startActivity(intent);
                                        finish();
                                    } else {

                                        Toast.makeText(SocityM_LoginPage.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

            }
        });
        SignupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocityM_LoginPage.this,SocietyM_RegistrationPage.class);
                startActivity(intent);
            }
        });
    }
}