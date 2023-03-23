package com.example.newgensociety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SocietyM_RegistrationPage extends AppCompatActivity {

    Button signup;
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



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAllFieldsChecked = CheckAllFields();
                if (isAllFieldsChecked) {
                    Intent i = new Intent(SocietyM_RegistrationPage.this,SocietyM_HomePage.class);
                    startActivity(i);
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

    }
