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
import com.google.firebase.FirebaseOptions;
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
    String FirebaseOldPass;
    FirebaseFirestore db;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_mchange_pass);

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
    }


    private void getFirebasePass() {

        mAuth = FirebaseAuth.getInstance();
        String UserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        db = FirebaseFirestore.getInstance();
        db.collection("C_Members").whereEqualTo("userId",UserId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for ( QueryDocumentSnapshot document : task.getResult()){
                                Log.d(TAG, document.getId()+ "=>"+ document.getData());

                                FirebaseOldPass = Objects.requireNonNull(document.get("Cm_password")).toString();

                                if(FirebaseOldPass.equals(OldPass.getText().toString())){
                                    if (!NewPass.getText().toString().equals(ConNewPass.getText().toString())) {
                                        ConNewPass.setError("Password Doesn't Matched");
                                        return;
                                    }
                                    else {
                                        getChangePass();
                                    }
                                }
                                else {
                                    Toast.makeText(CommitteeM_ChangePass.this, "Old Password Not Matched", Toast.LENGTH_SHORT).show();
                                    OldPass.setError("Old Password is wrong");
                                    return;
                                }
                            }

                        }else{
                            Toast.makeText(CommitteeM_ChangePass.this, "Failed",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }
    private void getChangePass() {
        String UserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        String NewPassword = NewPass.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        Map<String,Object> password = new HashMap<>();
//        password.put("Cm_password", NewPassword);
        if (user != null) {
            user.updatePassword(NewPassword)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Password updated");
                            } else {
                                Log.d(TAG, "error occurred when Password updated");
                            }
                        }
                    });
        }else{
            Toast.makeText(this, "User Not Found", Toast.LENGTH_SHORT).show();
        }
    }

}