package com.example.newgensociety;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SocietyM_showAddFlat extends AppCompatActivity {
    FirebaseFirestore db;
    ArrayList<Flats> flatsArrayList;
    myRecycleVA_Flats myMRVAdapter;
    RecyclerView recyclerView;

    Button AddFlat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_mshow_add_flat);
        recyclerView = findViewById(R.id.Society_Flats_RecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        flatsArrayList = new ArrayList<Flats>();
        myMRVAdapter = new myRecycleVA_Flats(SocietyM_showAddFlat.this,flatsArrayList);
        recyclerView.setAdapter(myMRVAdapter);

        EventChangeListener();
    }
    private void EventChangeListener() {
        db.collection("Flats")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Log.e("Firestore Error",error.getMessage());
                            return;
                        }
                        assert value != null;
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                flatsArrayList.add(dc.getDocument().toObject(Flats.class));
                            }
                            myMRVAdapter.notifyDataSetChanged();
                        }

                    }
                });

        AddFlat = findViewById(R.id.AddFlat);
        AddFlat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocietyM_showAddFlat.this,SocietyM_AddFlat.class);
                startActivity(intent);
            }
        });


    }
}