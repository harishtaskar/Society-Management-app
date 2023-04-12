package com.example.newgensociety;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class SocietyM_Hall_Request extends AppCompatActivity {

    ImageView Back;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    ArrayList<Hall_Request> hallRequestArrayList;
    myRecycleVA_Hall_Request myMRVAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_mhall_request);
        Back = findViewById(R.id.Btn_Hall_Request_Back);

        recyclerView = findViewById(R.id.Society_Hall_Request_RecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        hallRequestArrayList = new ArrayList<Hall_Request>();
        myMRVAdapter = new myRecycleVA_Hall_Request(SocietyM_Hall_Request.this,hallRequestArrayList);
        recyclerView.setAdapter(myMRVAdapter);
        EventChangeListener();

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocietyM_Hall_Request.this,SocietyM_HallShow.class);
                startActivity(intent);
                finish();
            }
        });
        }

    private void EventChangeListener() {

        mAuth = FirebaseAuth.getInstance();
        String UserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        db.collection("Booking Requests").whereEqualTo("userId",UserId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("FireStore Error", error.getMessage());
                            return;
                        }
                        assert value != null;
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                hallRequestArrayList.add(dc.getDocument().toObject(Hall_Request.class));

                            }
                            myMRVAdapter.notifyDataSetChanged();
                        }

                    }
                });
        }
    }