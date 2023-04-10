package com.example.newgensociety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CommitteeM_AddHall extends AppCompatActivity {

    ImageView Back;
    EditText Title, Size, Description;
    FirebaseAuth mAuth;
    Button AddHall;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_madd_hall);

        Title = findViewById(R.id.Committee_Hall_Title);
        Size = findViewById(R.id.Committee_Hall_Size);
        Description = findViewById(R.id.Committee_Hall_Description);
        AddHall = findViewById(R.id.Btn_Add_Hall);
        Back = findViewById(R.id.Btn_Hall_Back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommitteeM_AddHall.this,CommitteeM_showHall.class);
                startActivity(intent);
            }
        });

        AddHall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Title.getText().toString().length()==0){
                    Title.setError("Enter Title");
                    return;
                }
                if(Size.getText().toString().length()==0){
                    Size.setError("Enter Size");
                    return;
                }
                if(Description.getText().toString().length()==0){
                    Size.setError("Enter Description");
                    return;
                }



                mAuth = FirebaseAuth.getInstance();
                db = FirebaseFirestore.getInstance();

                String HallTitle = Title.getText().toString();
                String HallSize = Size.getText().toString();
                String HallDescription = Description.getText().toString();

                Map<String,Object> addHall = new HashMap<>();
                addHall.put("hall_title", HallTitle);
                addHall.put("hall_size", HallSize);
                addHall.put("Description", HallDescription);

                db.collection("Hall").document()
                        .set(addHall)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(),"Successful", Toast.LENGTH_SHORT).show();
                                Title.setText("");
                                Size.setText("");
                                Description.setText("");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


    }
}