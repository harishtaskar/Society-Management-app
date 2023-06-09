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

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CommitteeM_showHelp extends AppCompatActivity {

    FirebaseFirestore db;
    ImageView Back;
    ArrayList<Helps> helpsArrayList;
    myRecycleVA_Helps myMRVAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_mshow_help);
        Back = findViewById(R.id.Btn_help_Back);
        recyclerView = findViewById(R.id.Committee_Helps_RecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        helpsArrayList = new ArrayList<Helps>();
        myMRVAdapter = new myRecycleVA_Helps(CommitteeM_showHelp.this,helpsArrayList);
        recyclerView.setAdapter(myMRVAdapter);
        EventChangeListener();

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommitteeM_showHelp.this,CommitteeM_HomePage.class);
                startActivity(intent);
            }
        });

    }
    private void EventChangeListener() {

        db = FirebaseFirestore.getInstance();

        db.collection("HelpRequests").whereEqualTo("isRemoved",false)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Log.e("FireStore Error",error.getMessage());
                            return;
                        }
                        assert value != null;
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                helpsArrayList.add(dc.getDocument().toObject(Helps.class));
                            }
                            myMRVAdapter.notifyDataSetChanged();
                        }

                    }
                });

    }
}