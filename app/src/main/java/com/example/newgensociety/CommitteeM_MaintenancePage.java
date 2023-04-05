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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CommitteeM_MaintenancePage extends AppCompatActivity {

    TextView Duedate;
    ImageView Back;
    String DueDateString;
    EditText FlatNo, Discription, Amount, upiId;
    Button Send, Maintainance_Status;
    RadioGroup RG_Discount;
    RadioButton _5per, _10per, _15per;
    FirebaseFirestore db;
    int year, month, day;
    int discount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_mmaintenance_page);
        RG_Discount = findViewById(R.id.Committee_Maintenance_RG_discount);
        Duedate = findViewById(R.id.Committee_Maintenance_dueDate);
        FlatNo = findViewById(R.id.Committee_Maintenance_FlatNo);
        Discription = findViewById(R.id.Committee_Maintenance_Discription);
        Amount = findViewById(R.id.Committee_Maintenance_Amount);
        Send = findViewById(R.id.Send);
        _5per = findViewById(R.id._5percent);
        _10per = findViewById(R.id._10percent);
        Back = findViewById(R.id.Maintenance_btn_back);
        upiId = findViewById(R.id.Committee_Maintenance_Upi);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommitteeM_MaintenancePage.this,CommitteeM_HomePage.class);
                startActivity(intent);
                finish();
            }
        });
        _15per = findViewById(R.id._15percent);
        db = FirebaseFirestore.getInstance();
        Maintainance_Status = findViewById(R.id.maintainance_Status);
        final Calendar calendar = Calendar.getInstance();
        Duedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(CommitteeM_MaintenancePage.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Duedate.setText(i2+"/"+(i1+1)+"/"+i);
                    }
                },year, month, year);
                datePickerDialog.show();
            }
        });




        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FlatNo.getText().toString().length()==0){
                    FlatNo.setError("FlatNo Can't be Empty");
                    Toast.makeText(getApplicationContext(),"FlatNo Can't be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Amount.getText().toString().length()==0){
                    Amount.setError("Amount Can't be Empty");
                    Toast.makeText(getApplicationContext(),"Amount Can't be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Discription.getText().toString().length()==0){
                    Discription.setError("Discription Can't be Empty");
                    Toast.makeText(getApplicationContext(),"Discription Can't be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Duedate.getText().toString().length()==0){
                    Duedate.setError("DueDate Can't be Empty");
                    Toast.makeText(getApplicationContext(),"DueDate Can't be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(_5per.isChecked()){
                    discount = 5;
                }
                if(_10per.isChecked()){
                    discount = 10;
                }
                if(_15per.isChecked()){
                    discount = 15;
                }

                boolean removed = false;
                boolean isPaid = false;
                String UpiId = upiId.getText().toString();
                String flatNo = FlatNo.getText().toString();
                String description = Discription.getText().toString();
                String dueDate = DueDateString;
                Integer amount = Integer.parseInt(Amount.getText().toString());
                Integer Discount = discount;

                Map<String,Object> maintenance = new HashMap<>();
                maintenance.put("flat_no", flatNo);
                maintenance.put("discription", description);
                maintenance.put("due_date", dueDate);
                maintenance.put("amount",amount);
                maintenance.put("discount",Discount);
                maintenance.put("MaintenanceCode",generatedString);
                maintenance.put("isPaid",isPaid);
                maintenance.put("isRemoved",removed);
                maintenance.put("upiId", UpiId);

                db.collection("Maintenance")
                        .add(maintenance)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(),"Successful", Toast.LENGTH_SHORT).show();
                                FlatNo.setText("");
                                Discription.setText("");
                                Duedate.setText("");
                                Amount.setText("");
                                RG_Discount.clearCheck();
                                upiId.setText("");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        Maintainance_Status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommitteeM_MaintenancePage.this, CommitteeM_show_maintainance.class);
                startActivity(intent);
            }
        });



    }
}