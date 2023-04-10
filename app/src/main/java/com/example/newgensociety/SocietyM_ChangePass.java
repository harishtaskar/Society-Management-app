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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class SocietyM_ChangePass extends AppCompatActivity {

    EditText OldPass, NewPass, ConNewPass;
    TextView ForgotPass;
    Button ChangePass;
    String FirebaseOldPass;
    FirebaseFirestore db;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_mchange_pass);

        OldPass = findViewById(R.id.Society_ChangePass_OldPass);
        NewPass = findViewById(R.id.Society_ChangePass_NewPassword);
        ConNewPass = findViewById(R.id.Society_ChangePass_ConfirmNewPass);
        ForgotPass = findViewById(R.id.Forgot_Password);
        ChangePass = findViewById(R.id.Sm_ChangePass);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        ForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocietyM_ChangePass.this,SocietyM_ForgotPassword.class);
                startActivity(intent);
            }
        });

        ChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (NewPass.getText().toString().length() < 8){
                    NewPass.setText("Invalid Password");
                    Toast.makeText(SocietyM_ChangePass.this, "Password Should be minimum 8 digits", Toast.LENGTH_SHORT).show();
                }
                else if(ConNewPass.getText().toString().length() < 8){
                    Toast.makeText(SocietyM_ChangePass.this, "Password Should be minimum 8 digits", Toast.LENGTH_SHORT).show();
                    ConNewPass.setError("Invalid Password");
                } else {
                    getFirebasePass();
                }

            }
        });

    }
    private void getFirebasePass() {


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        assert user != null;
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

        String UserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        String NewPassword = NewPass.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.updatePassword(NewPassword)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(SocietyM_ChangePass.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Password updated");
                            } else {
                                Toast.makeText(SocietyM_ChangePass.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "error occurred when Password updated");
                            }
                        }
                    });
        }else{
            Toast.makeText(this, "User Not Found", Toast.LENGTH_SHORT).show();
        }
//        Map<String,Object> password = new HashMap<>();
//        password.put("Cm_password", NewPassword);
    }
}