package com.example.newgensociety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.checkerframework.checker.units.qual.A;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Committee_NoticePubllishing_Page extends AppCompatActivity {

    EditText CM_name, Notice_Subject, Notice;
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


                String dash = "~";
                String cm_name = dash+CM_name.getText().toString();
                String subject = Notice_Subject.getText().toString();
                String notice = Notice.getText().toString();

                //Firebase FireStore
                Map<String,Object> notices = new HashMap<>();
                notices.put("cm_name",cm_name);
                notices.put("subject",subject);
                notices.put("notice",notice);
                notices.put("date",date);
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

                //Firebase Realtime database
//                Notice notice1 = new Notice(cm_name, subject, notice);
//
//                db = FirebaseDatabase.getInstance("https://new-generation-society-default-rtdb.asia-southeast1.firebasedatabase.app/");
//                rootDatabaseRef = db.getReference("Notice");
//                rootDatabaseRef.child(subject).setValue(notice1).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        CM_name.setText("");
//                        Notice_Subject.setText("");
//                        Notice.setText("");
//                        Toast.makeText(getApplicationContext(),"Successfully Published", Toast.LENGTH_SHORT).show();
//                    }
//                });

            }
        });

    }
}