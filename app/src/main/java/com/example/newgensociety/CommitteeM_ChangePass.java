package com.example.newgensociety;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CommitteeM_ChangePass extends AppCompatActivity {

    EditText OldPass, NewPass, ConNewPass;
    TextView ForgotPass;
    Button ChangePass;
    ImageView Back;
    FirebaseFirestore db;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_mchange_pass);

        Back = findViewById(R.id.CommitteeM_ChangePass_Back_Btn);
        OldPass = findViewById(R.id.Committee_ChangePass_OldPass);
        NewPass = findViewById(R.id.Committee_ChangePass_NewPassword);
        ConNewPass = findViewById(R.id.Committee_ChangePass_ConfirmNewPass);
        ForgotPass = findViewById(R.id.Forgot_Password);
        ChangePass = findViewById(R.id.Cm_ChangePass);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        ForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommitteeM_ChangePass.this,CommitteeM_ForgotPassword.class);
                startActivity(intent);
            }
        });

        ChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NewPass.getText().toString().length() < 8){
                    NewPass.setText("Invalid Password");
                    Toast.makeText(CommitteeM_ChangePass.this, "Password Should be minimum 8 digits", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(ConNewPass.getText().toString().length() < 8){
                    Toast.makeText(CommitteeM_ChangePass.this, "Password Should be minimum 8 digits", Toast.LENGTH_SHORT).show();
                    ConNewPass.setError("Invalid Password");
                    return;
                } else {
                    getFirebasePass();
                }

            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CommitteeM_HomePage.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private void getFirebasePass() {

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        String currentEmail = user.getEmail();
        String currentPassword = OldPass.getText().toString();
        assert currentEmail != null;
        AuthCredential credential = EmailAuthProvider.getCredential(currentEmail, currentPassword);
        user.reauthenticate(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        getChangePass();
                        OldPass.setText("");
                        NewPass.setText("");
                        ConNewPass.setText("");
                    } else {
                        OldPass.setError("Password is wrong");
                    }
                });



    }
    private void getChangePass() {

        String NewPassword = NewPass.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.updatePassword(NewPassword)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(CommitteeM_ChangePass.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Password updated");
                            } else {
                                Toast.makeText(CommitteeM_ChangePass.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "error occurred when Password updated");
                            }
                        }
                    });
        }else{
            Toast.makeText(this, "User Not Found", Toast.LENGTH_SHORT).show();
        }
    }

}