package com.example.newgensociety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SocietyM_RegistrationPage extends AppCompatActivity {

    Button signup;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseDatabase dbf;
    DatabaseReference rootDatabaseRef;
    EditText scode, sname, semail, smobile, spassword, scpasword;
    boolean isAllFieldsChecked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_mregistration_page);

        scode = findViewById(R.id.soccode);
        sname = findViewById(R.id.sname);
        semail = findViewById(R.id.semail);
        smobile = findViewById(R.id.smobile);
        spassword = findViewById(R.id.spassword);
        scpasword = findViewById(R.id.sconfirmpassword);
        signup = findViewById(R.id.ssignup);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        signup.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                String Email = String.valueOf(semail.getText());
                String Password = String.valueOf(spassword.getText());
                String Society_code = String.valueOf(scode.getText());
                String Name = String.valueOf(sname.getText());
                String Mobile = String.valueOf(smobile.getText());
                Boolean Status = true;
                //Authentication
                mAuth.createUserWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String userId = Objects.requireNonNull(task.getResult().getUser()).getUid();
                                    boolean is_Society_M = true;
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("userId", userId);
                                    user.put("email", Email);
                                    user.put("isSociety", is_Society_M);

                                    db.collection("Users").document(userId).set(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {

                                                }
                                            })
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

                                                }
                                            });


                                    Map<String, Object> s_member = new HashMap<>();
                                    s_member.put("Society_code", Society_code);
                                    s_member.put("Email", Email);
                                    s_member.put("Password", Password);
                                    s_member.put("Member_Name", Name);
                                    s_member.put("Mobile", Mobile);
                                    s_member.put("Status", Status);

                                    isAllFieldsChecked = CheckAllFields();
                                    if (isAllFieldsChecked) {

                                        //Firebase FireStore
                                        db.collection("Society_Members").document(userId)
                                                .set(s_member)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {

                                                    }
                                                })
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(SocietyM_RegistrationPage.this, CommitteeM_LoginPage.class);
                                                        scode.setText("");
                                                        sname.setText("");
                                                        semail.setText("");
                                                        spassword.setText("");
                                                        scpasword.setText("");
                                                        smobile.setText("");
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                                                    }
                                                });


                                        //Realtime Database
                                        boolean SM_status = true;

                                        S_Member sMember = new S_Member(Name, Mobile, Email, Password, SM_status);

                                        dbf = FirebaseDatabase.getInstance("https://new-generation-society-default-rtdb.asia-southeast1.firebasedatabase.app/");
                                        rootDatabaseRef = dbf.getReference("S_member");
                                        rootDatabaseRef.child(Mobile).setValue(sMember).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });


                                        Toast.makeText(SocietyM_RegistrationPage.this, "Account Created",
                                                Toast.LENGTH_SHORT).show();
//                                        Intent i = new Intent(SocietyM_RegistrationPage.this,SocietyM_HomePage.class);
//                                        startActivity(i);

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(SocietyM_RegistrationPage.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }
                        });


            }

            private boolean CheckAllFields() {
                if (scode.getText().toString().length() == 0) {
                    scode.setError("This field is required");
                    Toast.makeText(SocietyM_RegistrationPage.this, "This field is required", Toast.LENGTH_SHORT).show();
                    return false;
                }

                if (sname.length() == 0) {
                    sname.setError("This field is required");
                    return false;
                }

                if (smobile.length() == 0) {
                    smobile.setError("Email is required");
                    return false;
                }
                if (semail.length() == 0) {
                    semail.setError("Email is required");
                    return false;
                }

                if (spassword.length() == 0) {
                    spassword.setError("Password is required");
                    return false;
                } else if (spassword.length() < 8) {
                    spassword.setError("Password must be minimum 8 characters");
                    return false;
                }

                if (scpasword.length() == 0) {
                    scpasword.setError("Password is required");
                    return false;
                } else if (scpasword.length() < 8) {
                    scpasword.setError("Password must be minimum 8 characters");
                    return false;
                }
                if (!spassword.getText().toString().equals(scpasword.getText().toString())) {
                    spassword.setError("Password not matched");
                    return false;
                }

                // after all validation return true.
                return true;
            }


        });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser CurrentUser = mAuth.getCurrentUser();
        if (CurrentUser != null) {
            Intent intent = new Intent(getApplicationContext(), SocietyM_HomePage.class);
            startActivity(intent);
            finish();
        }
    }
}
