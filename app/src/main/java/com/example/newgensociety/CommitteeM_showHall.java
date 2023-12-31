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
import android.widget.TextView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CommitteeM_showHall extends AppCompatActivity {

    ImageView AddHall;
    TextView Request;

    FirebaseFirestore db;
    ArrayList<Hall> hallArrayList;
    myRecycleVA_Hall myMRVAdapter;
    RecyclerView recyclerView;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_mshow_hall);
         Request = findViewById(R.id.Committee_Hall_Request);
         AddHall = findViewById(R.id.Committee_Hall_Add);

        recyclerView = findViewById(R.id.Committee_Hall_RecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        hallArrayList = new ArrayList<Hall>();
        myMRVAdapter = new myRecycleVA_Hall(CommitteeM_showHall.this,hallArrayList);
        recyclerView.setAdapter(myMRVAdapter);
        EventChangeListener();

         Request.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(CommitteeM_showHall.this,CommitteeM_Hall_Request.class);
                 startActivity(intent);
             }
         });

         AddHall.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(CommitteeM_showHall.this,CommitteeM_AddHall.class);
                 startActivity(intent);
             }
         });
    }

    private void EventChangeListener() {
        db.collection("Hall")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("FireStore Error", error.getMessage());
                            return;
                        }
                        assert value != null;
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                hallArrayList.add(dc.getDocument().toObject(Hall.class));
                            }
                            myMRVAdapter.notifyDataSetChanged();
                        }

                    }
                });
    }

}