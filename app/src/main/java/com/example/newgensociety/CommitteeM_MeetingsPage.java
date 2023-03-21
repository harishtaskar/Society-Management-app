package com.example.newgensociety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CommitteeM_MeetingsPage extends AppCompatActivity {

    ImageView Back;
    EditText Subject, No_of_Members, Time, Description;
    TextView Date;
    Button Schedule_Meetings;



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
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommitteeM_MeetingsPage.this,CommitteeM_HomePage.class);
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

            }
        });

    }
}