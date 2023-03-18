package com.example.newgensociety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class CommitteeM_MaintenancePage extends AppCompatActivity {

    TextView Duedate;
    String duedatestring;
    EditText FlatNo, Discription, Amount;
    Button Send;
    FirebaseFirestore db;
    int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_mmaintenance_page);
        Duedate = findViewById(R.id.Committee_Maintenance_dueDate);
        FlatNo = findViewById(R.id.Committee_Maintenance_FlatNo);
        Discription = findViewById(R.id.Committee_Maintenance_Discription);
        Amount = findViewById(R.id.Committee_Maintenance_Amount);
        Send = findViewById(R.id.Send);
        db = FirebaseFirestore.getInstance();
        Calendar calendar = Calendar.getInstance();
        Duedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(CommitteeM_MaintenancePage.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Duedate.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                        duedatestring = Duedate.getText().toString();
                    }
                },year, month, year);
                datePickerDialog.show();
            }
        });
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String flatno = FlatNo.getText().toString();
                String discription = Discription.getText().toString();
                String duedate = duedatestring;
                Integer amount = Integer.parseInt(Amount.getText().toString());

                Map<String,Object> maintenance = new HashMap<>();
                maintenance.put("FlatNo", flatno);
                maintenance.put("Discription", discription);
                maintenance.put("DueDate", duedate);
                maintenance.put("Amount",amount);

                db.collection("Maintainance")
                        .add(maintenance)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(),"Successful", Toast.LENGTH_SHORT).show();
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