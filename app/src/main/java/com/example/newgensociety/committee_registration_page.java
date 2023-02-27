package com.example.newgensociety;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class committee_registration_page extends AppCompatActivity {

    EditText Email, Password, ConPassword;
    Button btnRegister;
    FirebaseAuth mAuth;
    ProgressBar progressbar;
    TextView logintext;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(),Committee_homePage.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_registration_page);

        mAuth = FirebaseAuth.getInstance();
        logintext = findViewById(R.id.RegloginText);
        Email = findViewById(R.id.Committee_Registration_editT_CMemail);
        Password = findViewById(R.id.Committee_Registration_editT_password);
        ConPassword = findViewById(R.id.Committee_Registration_editT_confirmpass);
        btnRegister = findViewById(R.id.btn_RegCommitteeMember);
        progressbar = findViewById(R.id.Progressbar);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressbar.setVisibility(view.VISIBLE);
                String email, password, conpassword;
                email = String.valueOf(Email.getText());
                password = String.valueOf(Password.getText());
                conpassword = String.valueOf(ConPassword.getText());

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(committee_registration_page.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(committee_registration_page.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!conpassword.equals(password)) {
                    Toast.makeText(committee_registration_page.this, "Password Doesn't Matched", Toast.LENGTH_SHORT).show();
                    return;
                }

                logintext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(committee_registration_page.this,Committee_LoginPage.class);
                        startActivity(intent);
                        finish();
                    }
                });

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressbar.setVisibility(view.GONE);
                                if (task.isSuccessful()) {

                                    Toast.makeText(committee_registration_page.this, "Account Created",
                                            Toast.LENGTH_SHORT).show();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(committee_registration_page.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


            }
        });
    }


}