package com.example.newgensociety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CommitteeM_NoticePubllishing_Page extends AppCompatActivity {

    EditText CM_name, Notice_Subject, Notice;
    ImageView Back;
    Button btnPublish;
    FirebaseDatabase db;
    FirebaseFirestore dbf;

    DatabaseReference rootDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_notice_publlishing_page);

        dbf = FirebaseFirestore.getInstance();
        CM_name = findViewById(R.id.Committee_NoticePublish_CMname);
        Notice_Subject = findViewById(R.id.Committee_NoticePublish_Subject);
        Notice = findViewById(R.id.Committee_NoticePublish_Notice);
        btnPublish = findViewById(R.id.Committee_Notice_btnPublish);
        Back = findViewById(R.id.Notice_btnback);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommitteeM_NoticePubllishing_Page.this,CommitteeM_HomePage.class);
                startActivity(intent);
                finish();
            }
        });


        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(CM_name.getText().toString().length()==0){
                    CM_name.setError("Enter Name");
                    Toast.makeText(getApplicationContext(),"Enter name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Notice_Subject.getText().toString().length()==0){
                    Notice_Subject.setError("Subject can not be Blank");
                    Toast.makeText(getApplicationContext(),"Subject can not be Blank", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Notice.getText().toString().length()==0){
                    Notice.setError("Notice can not be blank");
                    Toast.makeText(getApplicationContext(),"Notice can not be blank", Toast.LENGTH_SHORT).show();
                    return;
                }
                Calendar calendar = Calendar.getInstance();
                String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                String date;
                date = currentDate.toString();

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

                String dash = "~";
                String cm_name = dash+CM_name.getText().toString();
                String subject = Notice_Subject.getText().toString();
                String notice = Notice.getText().toString();
                boolean removed = false;
                //Firebase FireStore
                Map<String,Object> notices = new HashMap<>();
                notices.put("cm_name",cm_name);
                notices.put("subject",subject);
                notices.put("notice",notice);
                notices.put("date",date);
                notices.put("removed",removed);
                notices.put("notice_code",generatedString);

                dbf.collection("Notice")
                        .add(notices)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(),"Successful", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"failed", Toast.LENGTH_SHORT).show();
                            }
                        });

                Notice notice1 = new Notice(cm_name, subject, notice, date, generatedString, removed);

                db = FirebaseDatabase.getInstance("https://new-generation-society-default-rtdb.asia-southeast1.firebasedatabase.app/");
                rootDatabaseRef = db.getReference("Notice");
                rootDatabaseRef.child(subject).setValue(notice1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        CM_name.setText("");
                        Notice_Subject.setText("");
                        Notice.setText("");
                        Toast.makeText(getApplicationContext(),"Successfully Published", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}