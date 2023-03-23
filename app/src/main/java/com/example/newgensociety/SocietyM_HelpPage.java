package com.example.newgensociety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SocietyM_HelpPage extends AppCompatActivity {

    EditText name,flatno,helpText;
    ImageView backarrow;
    Button send;
    FirebaseFirestore db;

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
                Integer Flat = Integer.parseInt(flatno.getText().toString());
                String Help = helpText.getText().toString();

                Map<String,Object> help = new HashMap<>();
                help.put("name",Name);
                help.put("help No",Flat);
                help.put("help",Help);
                help.put("date",date);
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