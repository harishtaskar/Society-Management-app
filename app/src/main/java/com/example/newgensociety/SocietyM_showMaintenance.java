package com.example.newgensociety;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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

import java.util.ArrayList;
import java.util.Objects;

public class SocietyM_showMaintenance extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    FirebaseFirestore db;
    String UserId;
    FirebaseAuth mAuth;
    ArrayList<Maintenance> maintenanceArrayList;
    ArrayList<FlatsSpinner> flatsArrayList;
    myRecycleVA_SM_Maintenance myMRVAdapter;
    myRecycleVA_FlatSpinner myRVFSAdapter;
    RecyclerView recyclerView;
    Spinner Flat_Spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_mshow_maintenance);
        recyclerView = findViewById(R.id.Society_Maintenance_RecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAuth = FirebaseAuth.getInstance();
        Flat_Spinner = findViewById(R.id.Flat_No_Spinner);
        db = FirebaseFirestore.getInstance();
        maintenanceArrayList = new ArrayList<Maintenance>();
        flatsArrayList = new ArrayList<FlatsSpinner>();
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
//        EventChangeListener2();
        myRVFSAdapter = new myRecycleVA_FlatSpinner(getApplicationContext(),flatsArrayList);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.flatsArrayList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Flat_Spinner.setAdapter(adapter);
        Flat_Spinner.setOnItemSelectedListener(this);




    }

//    private void EventChangeListener2() {
//        UserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
//        db.collection("Flats").whereEqualTo("userId",UserId)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()){
//                            Toast.makeText(SocietyM_showMaintenance.this, "Successful",
//                                    Toast.LENGTH_SHORT).show();
//
//                            for ( QueryDocumentSnapshot document : task.getResult()){
//                                Log.d(TAG, document.getId()+ "=>"+ document.getData());
//                                Integer integer = Integer.parseInt(document.get("flat_no").toString());
//                                flatsArrayList.add(document.toObject(FlatsSpinner.class));
//                            }
////                            myRVFSAdapter.notifyDataSetChanged();
//
//                        }else{
//                            Toast.makeText(SocietyM_showMaintenance.this, "Failed",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(SocietyM_showMaintenance.this, "Failed",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

    private void EventChangeListener() {

        UserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        DocumentReference documentReference = db.collection("Flats").document(UserId);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Integer integer = Integer.getInteger(parent.getItemAtPosition(position).toString());
        Toast.makeText(this, integer, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}