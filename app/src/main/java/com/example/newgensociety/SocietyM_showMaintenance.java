package com.example.newgensociety;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class SocietyM_showMaintenance extends AppCompatActivity {

    FirebaseFirestore db;
    String Userid;
    long flat_no;
    FirebaseAuth mAuth;
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
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        maintenanceArrayList = new ArrayList<Maintenance>();
        myMRVAdapter = new myRecycleVA_SM_Maintenance(SocietyM_showMaintenance.this,maintenanceArrayList);
        recyclerView.setAdapter(myMRVAdapter);
        myMRVAdapter.setOnItemClickListener(new myRecycleVA_SM_Maintenance.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(SocietyM_showMaintenance.this,Maintenance_Payment.class);
                startActivity(intent);
            }
        });
        EventChangeListener();
    }
    private void EventChangeListener() {

        Userid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        DocumentReference documentReference = db.collection("Flats").document(Userid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                assert value != null;
//                flat_no = value.getLong("flat_no");
            }
        });


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