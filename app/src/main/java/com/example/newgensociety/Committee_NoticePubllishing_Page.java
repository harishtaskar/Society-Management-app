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
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Committee_NoticePubllishing_Page extends AppCompatActivity {

    EditText CM_name, Notice_Subject, Notice;
    Button btnPublish;
    int number = 0;
    FirebaseDatabase db;
    DatabaseReference rootDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_notice_publlishing_page);

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

                String cm_name = CM_name.getText().toString();
                String subject = number+Notice_Subject.getText().toString();
                String notice_dic = Notice.getText().toString();

                Notice notice = new Notice(cm_name, subject, notice_dic);

                db = FirebaseDatabase.getInstance("https://new-generation-society-default-rtdb.asia-southeast1.firebasedatabase.app/");
                rootDatabaseRef = db.getReference("Notice");
                rootDatabaseRef.child(subject).setValue(notice).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        number++;
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