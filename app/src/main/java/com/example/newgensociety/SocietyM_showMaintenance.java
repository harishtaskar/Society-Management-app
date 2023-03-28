package com.example.newgensociety;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SocietyM_showMaintenance extends AppCompatActivity {

    FirebaseFirestore db;
    ArrayList<Maintenance> maintenanceArrayList;
    myRecycleVA_SM_Maintenance myMRVAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_mshow_maintenance);
        recyclerView = findViewById(R.id.Society_Maintenance_RecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        maintenanceArrayList = new ArrayList<Maintenance>();
        myMRVAdapter = new myRecycleVA_SM_Maintenance(SocietyM_showMaintenance.this,maintenanceArrayList);
        recyclerView.setAdapter(myMRVAdapter);
        EventChangeListener();
    }
    private void EventChangeListener() {
        db.collection("Maintenance").whereEqualTo("flat_no","101")
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