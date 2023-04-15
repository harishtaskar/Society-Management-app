package com.example.newgensociety;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SocietyM_EditProfile extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    ImageView member_img;
    TextView email, name, mobile, ChangePass, ForgotPass;
    Button select_img_btn, update;
    Uri imageUri;
    String SM_Contact, SM_Name, SM_Email, userId;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    boolean Status = true;

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
        ChangePass = findViewById(R.id.Sm_Change_Password);
        ForgotPass = findViewById(R.id.Sm_Forgot_Password);

        ChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocietyM_EditProfile.this,SocietyM_ChangePass.class);
                startActivity(intent);
            }
        });

        ForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocietyM_EditProfile.this,SocietyM_ForgotPassword.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        String UserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        db = FirebaseFirestore.getInstance();
        db.collection("Society_Members").whereEqualTo("userId", UserId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SocietyM_EditProfile.this, "Successful",
                                    Toast.LENGTH_SHORT).show();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + "=>" + document.getData());
                                SM_Contact = Objects.requireNonNull(document.get("Mobile")).toString();
                                mobile.setText(SM_Contact);
                                SM_Name = Objects.requireNonNull(document.get("Member_Name")).toString();
                                name.setText(SM_Name);
                                SM_Email = Objects.requireNonNull(document.get("Email")).toString();
                                email.setText(SM_Email);
                                userId = Objects.requireNonNull(document.get("userId")).toString();
                            }

                        } else {
                            Toast.makeText(SocietyM_EditProfile.this, "Failed",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sm_Email = email.getText().toString();
                String sm_Name = name.getText().toString();
                String sm_Mobile = mobile.getText().toString();

                String oldEmail = mAuth.getCurrentUser().getEmail();
                assert oldEmail != null;
                if(oldEmail.equals(sm_Email)) {
                    Map<String, Object> new_s_member = new HashMap<>();
                    new_s_member.put("Email", sm_Email);
                    new_s_member.put("Member_Name", sm_Name);
                    new_s_member.put("Mobile", sm_Mobile);
                    new_s_member.put("userId", UserId);
                    new_s_member.put("Status", Status);

                    db.collection("Society_Members").document(UserId)
                            .set(new_s_member)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                }
                            })
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SocietyM_EditProfile.this, SocietyM_HomePage.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });

                    Map<String, Object> new_user = new HashMap<>();
                    new_user.put("Email", sm_Email);
                    new_user.put("isSociety", Status);
                    new_user.put("userId", UserId);

                    db.collection("Users").document(UserId)
                            .set(new_user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

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

                    member_img.setImageURI(imageUri);
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference("image/" + UserId);
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
                    Toast.makeText(SocietyM_EditProfile.this, "Email Cannot Change", Toast.LENGTH_SHORT).show();
                }

            }
        });

        StorageReference storageRef = FirebaseStorage.getInstance().getReference("image/" + UserId);
        try {
            File localFile = File.createTempFile("tempfile",".jpeg");
            storageRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            member_img.setImageBitmap(bitmap);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


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
