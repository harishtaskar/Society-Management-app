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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Book_Hall extends AppCompatActivity {

    ImageView  Back;
    RadioGroup RG_AMPM;
    RadioButton am, pm;
    EditText Name, Flat, Time, Description;
    TextView Date, Hall_Title;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    Button BookHall;
    int year, month, day;
    String DateString, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_hall);
        Back = findViewById(R.id.Btn_Book_Amenities_Back);
        Name = findViewById(R.id.Book_Hall_Name);
        Flat = findViewById(R.id.Book_Hall_FlatNo);
        Time = findViewById(R.id.Book_Hall_Time);
        Date = findViewById(R.id.Book_Hall_Date);
        Description = findViewById(R.id.Book_Hall_Description);
        Hall_Title = findViewById(R.id.Hall_RecycleView_Title);
        db = FirebaseFirestore.getInstance();
        BookHall = findViewById(R.id.btn_Book_Hall);
        RG_AMPM = findViewById(R.id.Book_Hall_AMPM);
        am = findViewById(R.id._AM);
        pm = findViewById(R.id._PM);
        Calendar calendar = Calendar.getInstance();

        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Book_Hall.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Date.setText(i2+"/"+(i1+1)+"/"+i);
                        DateString = Date.getText().toString();
                    }
                },year, month, year);
                datePickerDialog.show();
            }
        });

        Intent intent = getIntent();
        String HallTitle = intent.getStringExtra("HallTitle");
        Hall_Title.setText(HallTitle);

        BookHall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Name.getText().toString().length() == 0){
                    Name.setError("Subject can't be Empty");
                    Toast.makeText(getApplicationContext(),"Subject Can't be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Flat.getText().toString().length() == 0){
                    Flat.setError("Field can't be Empty");
                    Toast.makeText(getApplicationContext(),"Field Can't be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Date.getText().toString().length() == 0){
                    Date.setError("Select Date");
                    Toast.makeText(getApplicationContext(),"Select Date", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Time.getText().toString().length() == 0){
                    Time.setError("Time can't be empty");
                    Toast.makeText(getApplicationContext(),"Time can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Description.getText().toString().length() == 0){
                    Description.setError("Description can't be Empty");
                    Toast.makeText(getApplicationContext(),"Description Can't be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(am.isChecked()){
                    status = "AM";
                }
                if(pm.isChecked()){
                    status = "PM";
                }

                int leftLimit = 65; // letter 'a'
                int rightLimit = 90; // letter 'z'
                int targetStringLength = 6;
                Random random = new Random();
                StringBuilder buffer = new StringBuilder(targetStringLength);
                for (int i = 0; i < targetStringLength; i++) {
                    int randomLimitedInt = leftLimit + (int)
                            (random.nextFloat() * (rightLimit - leftLimit + 1));
                    buffer.append((char) randomLimitedInt);
                }
                String bookingCode = buffer.toString();
                mAuth = FirebaseAuth.getInstance();

                String UserId = mAuth.getCurrentUser().getUid();
                Intent intent = getIntent();
                String HallTitle = intent.getStringExtra("HallTitle");
                Hall_Title.setText(HallTitle);
                String subject = Name.getText().toString();
                String Flat_no = Flat.getText().toString();
                String date = Date.getText().toString();
                String time = Time.getText().toString();
                String description = Description.getText().toString();
                boolean approved = false;

                Map<String,Object> booking = new HashMap<>();
                booking.put("subject",subject);
                booking.put("flat_no",Flat_no);
                booking.put("hall_title",HallTitle);
                booking.put("date",date);
                booking.put("time",time+" "+status);
                booking.put("description",description);
                booking.put("bookingCode",bookingCode);
                booking.put("isApproved",approved);
                booking.put("userId",UserId);

                db = FirebaseFirestore.getInstance();
                db.collection("Booking Requests")
                        .add(booking)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(),"Request Send Successful", Toast.LENGTH_SHORT).show();
                                Name.setText(null);
                                Flat.setText("");
                                Date.setText("");
                                Time.setText("");
                                Description.setText("");
                                RG_AMPM.clearCheck();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });




        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Book_Hall.this,SocietyM_HallShow.class);
                startActivity(intent);
                finish();
            }
        });

    }
}