package com.example.newgensociety;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Objects;

public class SocietyM_showMaintenance extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    FirebaseFirestore db;
    String UserId;
    FirebaseAuth mAuth;
    ArrayList<Maintenance> maintenanceArrayList;
    ImageView Back;
    myRecycleVA_SM_Maintenance myMRVAdapter;
    int SM_getFlats;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_mshow_maintenance);
        recyclerView = findViewById(R.id.Society_Maintenance_RecycleView);
        Back = findViewById(R.id.Back_btn_new);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        maintenanceArrayList = new ArrayList<Maintenance>();
        myMRVAdapter = new myRecycleVA_SM_Maintenance(SocietyM_showMaintenance.this,maintenanceArrayList);
        recyclerView.setAdapter(myMRVAdapter);
        EventChangeListener();

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocietyM_showMaintenance.this,SocietyM_HomePage.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void EventChangeListener() {

        UserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        DocumentReference documentReference = db.collection("Flats").document(UserId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                assert value != null;
            }
        });

        db.collection("Flats").whereEqualTo("userId",UserId)
                        .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(SocietyM_showMaintenance.this, "Successful",
                                                    Toast.LENGTH_SHORT).show();
                                            for ( QueryDocumentSnapshot document : task.getResult()){
                                                Log.d(TAG, document.getId()+ "=>"+ document.getData());
                                                SM_getFlats = Integer.parseInt(Objects.requireNonNull(document.get("flat_no")).toString());
                                                String SM_FlatNumber = String.valueOf(SM_getFlats);

                                                db.collection("Maintenance").whereEqualTo("flat_no",SM_FlatNumber)
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
                                                                        maintenanceArrayList.add(dc.getDocument().toObject(Maintenance.class));
                                                                    }
                                                                    myMRVAdapter.notifyDataSetChanged();
                                                                }

                                                            }
                                                        });
                                            }
                                        }else{
                                            Toast.makeText(SocietyM_showMaintenance.this, "Failed",
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}