package com.example.newgensociety;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Committee_show_Meetings extends AppCompatActivity {

    FirebaseFirestore db;
    ImageView Back;
    ArrayList<Meetings> meetingsArrayList;
    my_RecycleVA_Meetings myMRVAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_show_meetings);
        recyclerView = findViewById(R.id.Committee_Meetings_RecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        meetingsArrayList = new ArrayList<Meetings>();
        myMRVAdapter = new my_RecycleVA_Meetings(Committee_show_Meetings.this,meetingsArrayList);
        recyclerView.setAdapter(myMRVAdapter);
        EventChangeListener();

    }
    private void EventChangeListener() {
        db.collection("Meetings")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Log.e("FireStore Error",error.getMessage());
                            return;
                        }
                        assert value != null;
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                meetingsArrayList.add(dc.getDocument().toObject(Meetings.class));
                            }
                            myMRVAdapter.notifyDataSetChanged();
                        }

                    }
                });


    }
}