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
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CommitteeM_showComplains extends AppCompatActivity {

    FirebaseFirestore db;
    ImageView Back;
    ArrayList<Complains> complainsArrayList;
    myRecycleVA_Complains myMRVAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_mshow_complains);
        Back = findViewById(R.id.Btn_Complain_Back);
        recyclerView = findViewById(R.id.Committee_ComplainRecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        complainsArrayList = new ArrayList<Complains>();
        myMRVAdapter = new myRecycleVA_Complains(CommitteeM_showComplains.this,complainsArrayList);
        recyclerView.setAdapter(myMRVAdapter);
        EventChangeListener();

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommitteeM_showComplains.this,CommitteeM_HomePage.class);
                startActivity(intent);
            }
        });

    }
    private void EventChangeListener() {
        db.collection("Complains").whereEqualTo("isRemoved",false)
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
                                complainsArrayList.add(dc.getDocument().toObject(Complains.class));
                            }
                            myMRVAdapter.notifyDataSetChanged();
                        }

                    }
                });
    }
}