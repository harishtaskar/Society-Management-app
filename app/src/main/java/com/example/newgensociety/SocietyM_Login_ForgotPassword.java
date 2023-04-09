package com.example.newgensociety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class SocietyM_Login_ForgotPassword extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText Email;
    Button ForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_mlogin_forgot_password);
        Email = findViewById(R.id.Society_Login_ForgotPass_Email);
        ForgotPassword = findViewById(R.id.SM_Login_ForgotPass);


        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                String email = Email.getText().toString();
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Email.setText("");
                                    Toast.makeText(SocietyM_Login_ForgotPassword.this, "Password Send to your Email", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SocietyM_Login_ForgotPassword.this, "Email is not Registered", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
