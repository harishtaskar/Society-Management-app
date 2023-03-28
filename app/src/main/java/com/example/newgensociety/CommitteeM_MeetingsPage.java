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

public class CommitteeM_MeetingsPage extends AppCompatActivity {

    ImageView Back;
    RadioGroup RG_AMPM;
    RadioButton am, pm;
    EditText Subject, No_of_Members, Time, Description;
    TextView Date;
    FirebaseFirestore db;
    Button Schedule_Meetings, Meetings;
    int year, month, day;
    String DateString, status;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_mmeetings_page);
        Back = findViewById(R.id.meeting_btnback);
        Subject = findViewById(R.id.Committee_Meetings_Subject);
        No_of_Members = findViewById(R.id.Committee_Meetings_No_of_Members);
        Time = findViewById(R.id.Committee_Meetings_time);
        Date = findViewById(R.id.Committee_Meetings_date);
        Description = findViewById(R.id.Committee_Meetings_Description);
        Schedule_Meetings = findViewById(R.id.Schedule_Meeting);
        Meetings = findViewById(R.id.Meetings_Status);
        RG_AMPM = findViewById(R.id.Committee_Meetings_AMPM);
        am = findViewById(R.id._AM);
        pm = findViewById(R.id._PM);
        Calendar calendar = Calendar.getInstance();

        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(CommitteeM_MeetingsPage.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Date.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                        DateString = Date.getText().toString();
                    }
                },year, month, year);
                datePickerDialog.show();
            }
        });


        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommitteeM_MeetingsPage.this,CommitteeM_HomePage.class);
                startActivity(intent);
                finish();
            }
        });
        Meetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommitteeM_MeetingsPage.this, CommitteeM_show_Meetings.class);
                startActivity(intent);
            }
        });

        Schedule_Meetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Subject.getText().toString().length() == 0){
                    Subject.setError("Subject can't be Empty");
                    Toast.makeText(getApplicationContext(),"Subject Can't be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(No_of_Members.getText().toString().length() == 0){
                    No_of_Members.setError("Field can't be Empty");
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


                String subject = Subject.getText().toString();
                Integer no_of_Members = Integer.parseInt(No_of_Members.getText().toString());
                String date = Date.getText().toString();
                String time = Time.getText().toString();
                String description = Description.getText().toString();

                Map<String,Object> meetings = new HashMap<>();
                meetings.put("subject",subject);
                meetings.put("no_of_members",no_of_Members);
                meetings.put("date",date);
                meetings.put("time",time+" "+status);
                meetings.put("description",description);
                db = FirebaseFirestore.getInstance();
                db.collection("Meetings")
                        .add(meetings)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(),"Successful", Toast.LENGTH_SHORT).show();
                                Subject.setText(null);
                                No_of_Members.setText("");
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

    }
}