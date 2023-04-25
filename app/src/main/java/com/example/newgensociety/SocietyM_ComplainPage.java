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
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SocietyM_ComplainPage extends AppCompatActivity {

    EditText name, about, description;
    ImageView backarrowc;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    Button send,Complains;
    String SM_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_mcomplain_page);

        about = findViewById(R.id.about);
        description = findViewById(R.id.description);
        send = findViewById(R.id.btnsend);
        backarrowc = findViewById(R.id.imgbackarrowc);
        name = findViewById(R.id.Society_Complain_Name);
        Complains = findViewById(R.id.btnComplains);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        String UserId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        db.collection("Society_Members").whereEqualTo("userId",UserId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + "=>" + document.getData());
                                SM_name = Objects.requireNonNull(document.get("Member_Name")).toString();
                            }
                            name.setText(SM_name);
                        }
                        else{
                            Toast.makeText(SocietyM_ComplainPage.this, "Failed to fetch name", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().length() == 0) {
                    name.setError("This field is required");
                    Toast.makeText(SocietyM_ComplainPage.this, "This field is required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (about.getText().toString().length() == 0) {
                    about.setError("This field is required");
                    Toast.makeText(SocietyM_ComplainPage.this, "This field is required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (description.getText().toString().length() == 0) {
                    description.setError("This field is required");
                    Toast.makeText(SocietyM_ComplainPage.this, "This field is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                Calendar calendar = Calendar.getInstance();
                String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                String date;
                date = currentDate.toString();
                Boolean isRemoved = false;
                String Name = name.getText().toString();
                String About = about.getText().toString();
                String Description = description.getText().toString();

                mAuth = FirebaseAuth.getInstance();
                String UserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                Map<String,Object> complain = new HashMap<>();
                complain.put("name",Name);
                complain.put("about",About);
                complain.put("description",Description);
                complain.put("date",date);
                complain.put("isRemoved",isRemoved);
                complain.put("userId",UserId);
                db.collection("Complains")
                        .add(complain)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(),"Successful", Toast.LENGTH_SHORT).show();
                                Toast.makeText(SocietyM_ComplainPage.this, "Successful", Toast.LENGTH_SHORT).show();
                                about.setText("");
                                description.setText("");
                                name.setText("");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        backarrowc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocietyM_ComplainPage.this,SocietyM_HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        Complains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocietyM_ComplainPage.this,SocietyM_ComplainsRecycleView.class);
                startActivity(intent);
            }
        });

    }

}