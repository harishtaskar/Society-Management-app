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

public class SocietyM_HelpPage extends AppCompatActivity {

    EditText name,flatno,helpText;
    ImageView backarrow;
    Button send;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    String SM_name;

    boolean isAllFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_mhelp_page);
        name = findViewById(R.id.society_home_cardview_help_name);
        flatno = findViewById(R.id.society_home_cardview_help_flatno);
        helpText = findViewById(R.id.society_home_cardview_help_help);
        backarrow = findViewById(R.id.imgbackarrowh);
        send = findViewById(R.id.society_home_cardview_help_btnsend);
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
                            Toast.makeText(SocietyM_HelpPage.this, "Failed to fetch name", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocietyM_HelpPage.this,SocietyM_HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAllFieldsChecked = CheckAllFields();
                Calendar calendar = Calendar.getInstance();
                String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                String date;
                date = currentDate.toString();

                String Name = name.getText().toString();
                String Flat = flatno.getText().toString();
                String Help = helpText.getText().toString();
                boolean isRemoved = false;


                Map<String,Object> help = new HashMap<>();
                help.put("name",Name);
                help.put("flat_no",Flat);
                help.put("help",Help);
                help.put("date",date);
                help.put("isRemoved",isRemoved);
                db.collection("HelpRequests")
                        .add(help)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(),"Successful", Toast.LENGTH_SHORT).show();
                                Toast.makeText(SocietyM_HelpPage.this, "Successful", Toast.LENGTH_SHORT).show();
                                name.setText("");
                                flatno.setText("");
                                helpText.setText("");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"failed", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });


    }
    private boolean CheckAllFields() {
        if (name.getText().toString().length() == 0) {
            name.setError("This field is required");
            Toast.makeText(SocietyM_HelpPage.this, "This field is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (flatno.length() == 0) {
            flatno.setError("This field is required");
            return false;
        }

        if (helpText.length() == 0) {
            helpText.setError("This field is required");
            return false;
        }

        return true;

    }
}