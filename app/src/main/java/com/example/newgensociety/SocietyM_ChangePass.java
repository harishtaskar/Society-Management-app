package com.example.newgensociety;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.FirebaseAuth;
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

        ChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OldPass.getText().toString().length() == 0){
                    OldPass.setError("Please Enter this field");
                    return;
                }
                else if (NewPass.getText().toString().length() < 8){
                    NewPass.setText("Invalid Password");
                    Toast.makeText(SocietyM_ChangePass.this, "Password Should be minimum 8 digits", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(ConNewPass.getText().toString().length() < 8){
                    Toast.makeText(SocietyM_ChangePass.this, "Password Should be minimum 8 digits", Toast.LENGTH_SHORT).show();
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
        db.collection("Society_Members").whereEqualTo("userId",UserId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SocietyM_ChangePass.this, "Successful",
                                    Toast.LENGTH_SHORT).show();
                            for ( QueryDocumentSnapshot document : task.getResult()){
                                Log.d(TAG, document.getId()+ "=>"+ document.getData());
                                FirebaseOldPass = Objects.requireNonNull(document.get("Password")).toString();

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
                                    Toast.makeText(SocietyM_ChangePass.this, "Old Password Not Matched", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }else{
                            Toast.makeText(SocietyM_ChangePass.this, "Failed",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }
    private void getChangePass() {
        String UserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        String NewPassword = NewPass.getText().toString();

//        Map<String,Object> password = new HashMap<>();
//        password.put("Cm_password", NewPassword);

        db.collection("Society_Members").document(UserId)
                .update("Password",NewPassword)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                    }
                }).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"Successful", Toast.LENGTH_SHORT).show();
                        OldPass.setText("");
                        NewPass.setText("");
                        ConNewPass.setText("");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}