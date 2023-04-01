package com.example.newgensociety;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SocietyM_EditProfile extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    ImageView member_img;
    TextView email, name, mobile, password;
    Button select_img_btn, update;
    Uri imageUri;
    FirebaseFirestore db;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_medit_profile);

        member_img = findViewById(R.id.society_profile_edit_profile_member_img);
        select_img_btn = findViewById(R.id.society_profile_edit_profile_member_btn);
        update = findViewById(R.id.update);
        email = findViewById(R.id.semail);
        name = findViewById(R.id.sname);
        mobile = findViewById(R.id.smobile);
        password = findViewById(R.id.spassword);
        db = FirebaseFirestore.getInstance();
        String Mobile = "8238795994";

        mDatabase = FirebaseDatabase.getInstance().getReference().child("S_member").child(Mobile).child("s_name");

        String AAA = mDatabase.getRef().toString();
        email.setText(AAA);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot snapshot = (DataSnapshot) dataSnapshot.getChildren();
                    String Society_Name = Objects.requireNonNull(snapshot.getValue()).toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        select_img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        mDatabase.child("S_member").child(Mobile).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));

                }
            }
        });



        String S_Email = email.getText().toString();
        String S_Name = name.getText().toString();
        String S_Mobile = mobile.getText().toString();
        String S_Password = password.getText().toString();

        Map<String,Object> s_member = new HashMap<>();
        s_member.put("Email", S_Email);
        s_member.put("Member_Name",S_Name);
        s_member.put("Mobile",S_Mobile);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            member_img.setImageURI(imageUri);
        }
    }
    }
