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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Committee_show_maintainance extends AppCompatActivity {

    FirebaseFirestore db;

    ArrayList<Maintenance> maintenanceArrayList;
    myRecycleVA_Maintenance myMRVAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_show_maintainance);
        recyclerView = findViewById(R.id.Committee_Maintenance_RecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        maintenanceArrayList = new ArrayList<Maintenance>();
        myMRVAdapter = new myRecycleVA_Maintenance(Committee_show_maintainance.this,maintenanceArrayList);
        recyclerView.setAdapter(myMRVAdapter);

        EventChangeListener();
    }
    private void EventChangeListener() {
        db.collection("Maintenance").orderBy("flat_no", Query.Direction.ASCENDING)
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
                                maintenanceArrayList.add(dc.getDocument().toObject(Maintenance.class));
                            }
                            myMRVAdapter.notifyDataSetChanged();
                        }

                    }
                });
    }
}