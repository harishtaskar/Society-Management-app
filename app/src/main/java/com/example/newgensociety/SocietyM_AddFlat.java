package com.example.newgensociety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SocietyM_AddFlat extends AppCompatActivity {

    EditText Flat_No, MobileN, Name;
    RadioGroup RG;
    String Status;
    int year, month, day;
    String DueDateString;
    FirebaseFirestore db;
    FirebaseDatabase dbf;

    RadioButton Flat_Owner, RentedFlat, Rented_Others;
    Button AddFlat;
    ImageView Back;
    DatabaseReference rootDatabaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_madd_flat);

        Flat_No = findViewById(R.id.Society_AddFlat_flat_no);
        MobileN = findViewById(R.id.Society_AddFlat_Mobile);
        Name = findViewById(R.id.Society_AddFlat_Name);
        RG = findViewById(R.id.Society_AddFlat_RG_Status);
        Flat_Owner = findViewById(R.id.Flat_Owner);
        RentedFlat = findViewById(R.id.Rented_Flat);
        Rented_Others = findViewById(R.id.Rented_with_Others);
        Back = findViewById(R.id.Btn_Flat_Back);
        AddFlat = findViewById(R.id.Btn_Add_Flat);
        db = FirebaseFirestore.getInstance();
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        DueDateString = SimpleDateFormat.getDateInstance().format(calendar.getTime());


        AddFlat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Flat_No.getText().toString().length() != 3){
                    Flat_No.setError("Flat Number must be 3 digit");
                    Toast.makeText(SocietyM_AddFlat.this, "Flat Number must be 3 digit", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Name.getText().toString().length() == 0){
                    Name.setError("This Field Can't be Empty");
                    Toast.makeText(SocietyM_AddFlat.this, "This Field Can't be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(MobileN.getText().toString().length() != 10){
                    MobileN.setError("Invalid Mobile Number");
                    Toast.makeText(SocietyM_AddFlat.this, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Flat_Owner.isChecked() && !RentedFlat.isChecked() && !Rented_Others.isChecked()){
                    Toast.makeText(SocietyM_AddFlat.this, "Please Check One of this Field", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Flat_Owner.isChecked()){
                    Status = "Flat Owner";
                }
                if(RentedFlat.isChecked()){
                    Status = "Rented Flat";
                }
                if(Rented_Others.isChecked()){
                    Status = "Rented with Other Flatmates";
                }


                Integer flatno = Integer.parseInt(Flat_No.getText().toString());
                String mobile = MobileN.getText().toString();
                String duedate = DueDateString;
                String name = Name.getText().toString();
                String status = Status;

                Map<String,Object> flat = new HashMap<>();
                flat.put("flat_no", flatno);
                flat.put("name", name);
                flat.put("mobile", mobile);
                flat.put("date",duedate);
                flat.put("status",status);

                db.collection("Flats")
                        .add(flat)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(),"Successful", Toast.LENGTH_SHORT).show();
                                Flat_No.setText("");
                                Name.setText("");
                                MobileN.setText("");
                                RG.clearCheck();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Failed", Toast.LENGTH_SHORT).show();
                            }
                        });

                //Realtime Database
                Flats flats = new Flats(flatno, name, mobile, duedate, status);

                dbf = FirebaseDatabase.getInstance("https://new-generation-society-default-rtdb.asia-southeast1.firebasedatabase.app/");
                rootDatabaseRef = dbf.getReference("Flats");
                rootDatabaseRef.child(String.valueOf(flatno)).setValue(flats).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocietyM_AddFlat.this,SocietyM_HomePage.class);
                startActivity(intent);
            }
        });

    }
}