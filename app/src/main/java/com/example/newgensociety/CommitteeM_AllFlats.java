package com.example.newgensociety;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CommitteeM_AllFlats extends AppCompatActivity {

    FirebaseFirestore db;
    ArrayList<Flats> flatsArrayList;
    myRecycleVA_Flats myMRVAdapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_mall_flats);

        recyclerView = findViewById(R.id.Committee_Flats_RecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        flatsArrayList = new ArrayList<Flats>();
        myMRVAdapter = new myRecycleVA_Flats(CommitteeM_AllFlats.this,flatsArrayList);
        recyclerView.setAdapter(myMRVAdapter);
        EventChangeListener();

    }
    private void EventChangeListener() {
        db.collection("Flats")
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
                                flatsArrayList.add(dc.getDocument().toObject(Flats.class));
                            }
                            myMRVAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}