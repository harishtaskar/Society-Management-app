package com.example.newgensociety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SocietyM_RegistrationPage extends AppCompatActivity {

    Button signup;
    FirebaseAuth mAuth;
    EditText scode,sname,semail,smobile,spassword,scpasword;
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




        signup.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                String Email = String.valueOf(semail.getText());
                String Password = String.valueOf(spassword.getText());

                isAllFieldsChecked = CheckAllFields();
                if (isAllFieldsChecked) {
                    mAuth.createUserWithEmailAndPassword(Email, Password)
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

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
                            });

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

        // after all validation return true.
        return true;
    }

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

}
