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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CommitteeM_EditProfile extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    ImageView member_img;
    Button select_img_btn, Update;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    StorageReference storageReference;
    Uri imageUri;
    TextInputEditText SocietyName, CM_name, Mobile, Email, Password;
    String S_name, CM_Contact, CM_Name, C_Email, FirebasePassword, Address, Cm_Id, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_medit_profile);

        member_img = findViewById(R.id.society_profile_edit_profile_member_img);
        select_img_btn = findViewById(R.id.society_profile_edit_profile_member_btn);
        SocietyName = findViewById(R.id.Society_name);
        CM_name = findViewById(R.id.Cm_name);
        Mobile = findViewById(R.id.Cm_mobile);
        Email = findViewById(R.id.Cm_email);
        Password = findViewById(R.id.Cm_password);
        Update = findViewById(R.id.Cm_update);

        mAuth = FirebaseAuth.getInstance();
        String UserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        db = FirebaseFirestore.getInstance();
        db.collection("C_Members").whereEqualTo("userId",UserId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(CommitteeM_EditProfile.this, "Successful",
                                    Toast.LENGTH_SHORT).show();
                            for ( QueryDocumentSnapshot document : task.getResult()){
                                Log.d(TAG, document.getId()+ "=>"+ document.getData());
                                S_name = Objects.requireNonNull(document.get("Society_name")).toString();
                                SocietyName.setText(S_name);
                                CM_Contact = Objects.requireNonNull(document.get("cm_contact")).toString();
                                Mobile.setText(CM_Contact);
                                CM_Name = Objects.requireNonNull(document.get("Cm_name")).toString();
                                CM_name.setText(CM_Name);
                                C_Email = Objects.requireNonNull(document.get("Cm_email")).toString();
                                Email.setText(C_Email);
                                FirebasePassword = Objects.requireNonNull(document.get("Cm_password")).toString();
                                userId = Objects.requireNonNull(document.get("userId")).toString();
                                Address = Objects.requireNonNull(document.get("Address")).toString();
                                Cm_Id = Objects.requireNonNull(document.get("Cm_id")).toString();
                            }

                        }else{
                            Toast.makeText(CommitteeM_EditProfile.this, "Failed",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newSociety_Name = Objects.requireNonNull(SocietyName.getText()).toString();
                String newCM_Name = Objects.requireNonNull(CM_name.getText()).toString();
                String newCM_Email = Objects.requireNonNull(Email.getText()).toString();
                String newCM_Mobile = Objects.requireNonNull(Mobile.getText()).toString();
                String CM_Password = Objects.requireNonNull(Password.getText()).toString();

                Map<String,Object> new_cm_member = new HashMap<>();
                new_cm_member.put("Society_name", newSociety_Name);
                new_cm_member.put("Cm_name", newCM_Name);
                new_cm_member.put("Cm_email", newCM_Email);
                new_cm_member.put("cm_contact",newCM_Mobile);
                new_cm_member.put("userId",UserId);
                new_cm_member.put("Cm_id",Cm_Id);
                new_cm_member.put("Address",Address);
                new_cm_member.put("Cm_password",FirebasePassword);


                if(CM_Password.equals(FirebasePassword)) {
                    db.collection("C_Members").document(UserId)
                            .set(new_cm_member)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Password.setText("");
                                }
                            })
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });

                    boolean Status = false;

                    Map<String, Object> new_user = new HashMap<>();
                    new_user.put("Email", newCM_Email);
                    new_user.put("isSociety",Status);
                    new_user.put("userId",UserId);

                    db.collection("Users").document(UserId)
                            .set(new_user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Password.setText("");
                                }
                            })
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });

                    storageReference = FirebaseStorage.getInstance().getReference("image/"+UserId);
                    storageReference.putFile(imageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });

                }else {
                    Password.setError("Password Doesn't Matched");
                    Toast.makeText(getApplicationContext(), "Password Doesn't Matched", Toast.LENGTH_SHORT).show();
                }

            }
        });


        select_img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


    }
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null && data.getData() != null){
            imageUri = data.getData();
            member_img.setImageURI(imageUri);
        }
    }
}