package com.example.newgensociety;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class CommitteeM_LoginPage extends AppCompatActivity {

    EditText emailogin;
    EditText passwordlogin,societycode;
    Button loginButton;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String Userid, status;
    ProgressBar progressBar;
    boolean SocietyM_Status = false;
    TextView signup, ForgotPassword;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_login_page);
        mAuth = FirebaseAuth.getInstance();
        emailogin = findViewById(R.id.Committee_Login_editT_Email);
        passwordlogin = findViewById(R.id.Committee_Login_editT_Pass);
        loginButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.Progressbar);
        signup = findViewById(R.id.signupText);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        ForgotPassword = findViewById(R.id.Committee_Login_forgot);
        societycode = findViewById(R.id.Committee_Login_editT_Scode);

//        Intent intent = getIntent();
//        status = intent.getStringExtra("statusKey");



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(view.VISIBLE);
                db.collection("Society")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for ( QueryDocumentSnapshot document : task.getResult()){
                                        Log.d(TAG, document.getId()+ "=>"+ document.getData());
                                        String societyCode = Objects.requireNonNull(document.get("Society_Code")).toString();
                                        String userS_code = societycode.getText().toString();
                                        String societyEmail = Objects.requireNonNull(document.get("Cm_email")).toString();
                                        String enteredEmail = emailogin.getText().toString();
                                        if(!societyEmail.equals(enteredEmail)){
                                            progressBar.setVisibility(View.INVISIBLE);
                                            emailogin.setError("Email is invalid");
                                            return;
                                        }
                                        else if(userS_code.equals(societyCode)){
                                            userAuthentication();
                                        }
                                        else{
                                            progressBar.setVisibility(View.INVISIBLE);
                                            societycode.setError("invalid society code");
                                        }
                                    }

                                }else{
                                    Toast.makeText(CommitteeM_LoginPage.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommitteeM_LoginPage.this,SocietyM_Login_ForgotPassword.class);
                startActivity(intent);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommitteeM_LoginPage.this, committeeM_registration_page.class);
                startActivity(intent);
            }
        });

    }

    private void userAuthentication() {
        progressBar.setVisibility(View.VISIBLE);
        String email, password ;
        email = String.valueOf(emailogin.getText());
        password = String.valueOf(passwordlogin.getText());

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(CommitteeM_LoginPage.this, "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(CommitteeM_LoginPage.this, "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Login Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),CommitteeM_HomePage.class);
                            startActivity(intent);
                            finish();
                        } else {

                            Toast.makeText(CommitteeM_LoginPage.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {

            Userid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
            DocumentReference documentReference = db.collection("Users").document(Userid);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    assert value != null;
                    SocietyM_Status = Boolean.TRUE.equals(value.getBoolean("isSociety"));
                    Intent intent;
                    if(SocietyM_Status){
                        intent = new Intent(getApplicationContext(), Exaption.class);
                    }else {
                        intent = new Intent(getApplicationContext(), CommitteeM_HomePage.class);
                    }
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

}