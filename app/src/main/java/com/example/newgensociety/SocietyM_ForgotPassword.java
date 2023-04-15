package com.example.newgensociety;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SocietyM_ForgotPassword extends AppCompatActivity {

    EditText Email;
    Button ForgotPass;
    FirebaseFirestore db;
    ImageView Back;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_mforgot_password);

        Back = findViewById(R.id.SocietyM_ForgotPass_Back_Btn);
        Email = findViewById(R.id.Society_ForgotPass_Email);
        ForgotPass = findViewById(R.id.Sm_ForgotPass);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        ForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String email = Email.getText().toString();
                mAuth = FirebaseAuth.getInstance();
                String fireBaseEmail = Objects.requireNonNull(mAuth.getCurrentUser()).getEmail();

                assert fireBaseEmail != null;
                if(fireBaseEmail.equals(email)) {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SocietyM_ForgotPassword.this, "Password Link is Shared on your email", Toast.LENGTH_SHORT).show();
                                Email.setText("");
                            } else {
                                Toast.makeText(SocietyM_ForgotPassword.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SocietyM_ForgotPassword.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    Email.setError("Invalid Email");
                    Toast.makeText(SocietyM_ForgotPassword.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocietyM_ForgotPassword.this,SocietyM_ChangePass.class);
                startActivity(intent);
                finish();
            }
        });
    }
}