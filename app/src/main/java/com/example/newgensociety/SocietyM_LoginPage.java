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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class SocietyM_LoginPage extends AppCompatActivity {

    EditText Email;
    TextView SignupText, ForgotPassword;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    EditText password, SocietyCode;
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
        setContentView(R.layout.activity_society_mlogin_page);
        mAuth = FirebaseAuth.getInstance();
        Email = findViewById(R.id.Society_Login_editT_Email);
        password = findViewById(R.id.Society_Login_editT_Pass);
        loginButton = findViewById(R.id.Society_loginButton);
        SignupText = findViewById(R.id.signupText);
        ForgotPassword = findViewById(R.id.Society_Login_forgot);
        SocietyCode = findViewById(R.id.Society_Login_editT_Scode);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = FirebaseFirestore.getInstance();
                db.collection("Society")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for ( QueryDocumentSnapshot document : task.getResult()){
                                        Log.d(TAG, document.getId()+ "=>"+ document.getData());
                                        String societyCode = Objects.requireNonNull(document.get("Society_Code")).toString();
                                        String userS_code = SocietyCode.getText().toString();
                                        String societyEmail = Objects.requireNonNull(document.get("Cm_email")).toString();
                                        String enteredEmail = Email.getText().toString();
                                        if(societyEmail.equals(enteredEmail)){
                                            Email.setError("Email is invalid");
                                            return;
                                        }
                                        else if(userS_code.equals(societyCode)){
                                            userAuthentication();
                                        }
                                        else{
                                            SocietyCode.setError("invalid society code");
                                        }
                                    }

                                }else{
                                    Toast.makeText(SocietyM_LoginPage.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocietyM_LoginPage.this,SocietyM_Login_ForgotPassword.class);
                startActivity(intent);
            }
        });
        SignupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocietyM_LoginPage.this,SocietyM_RegistrationPage.class);
                startActivity(intent);
            }
        });
    }

    private void userAuthentication() {
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

                            Toast.makeText(SocietyM_LoginPage.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}