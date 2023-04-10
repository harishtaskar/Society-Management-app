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

public class SocietyM_Meetings_ShowPage extends AppCompatActivity {

    FirebaseFirestore db;
    ImageView Back;
    ArrayList<Meetings> meetingsArrayList;
    myRecycleVA_SM_Meetings myMRVAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_mmeetings_show_page);

        Back = findViewById(R.id.Btn_Meetings_Back);
        recyclerView = findViewById(R.id.SocietyM_Meetings_Recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        meetingsArrayList = new ArrayList<Meetings>();
        myMRVAdapter = new myRecycleVA_SM_Meetings(SocietyM_Meetings_ShowPage.this,meetingsArrayList);
        recyclerView.setAdapter(myMRVAdapter);
        EventChangeListener();

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocietyM_Meetings_ShowPage.this,SocietyM_HomePage.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void EventChangeListener() {
        db.collection("Meetings").whereEqualTo("removed",false)
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