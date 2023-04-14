package com.example.newgensociety;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    CardView SocietyM_login, CommitteeM_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SocietyM_login = findViewById(R.id.btn_asSocietyMember);
        CommitteeM_Login = findViewById(R.id.btn_asComitteeMember);
        CommitteeM_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CommitteeM_LoginPage.class);
                startActivity(intent);
                finish();
            }
        });
        SocietyM_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SocietyM_LoginPage.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        db =  FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {

            String Userid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
            DocumentReference documentReference = db.collection("Users").document(Userid);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    assert value != null;
                    boolean SocietyM_Status = Boolean.TRUE.equals(value.getBoolean("isSociety"));
                    Intent intent;
                    if (SocietyM_Status) {
                        intent = new Intent(getApplicationContext(), SocietyM_HomePage.class);
                    } else {
                        intent = new Intent(getApplicationContext(), CommitteeM_HomePage.class);
                    }
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
}