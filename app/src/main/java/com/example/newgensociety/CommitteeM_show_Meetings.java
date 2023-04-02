package com.example.newgensociety;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommitteeM_show_Meetings extends AppCompatActivity {

    FirebaseFirestore db;


    ImageView Back;
    ArrayList<Meetings> meetingsArrayList;
    my_RecycleVA_Meetings myMRVAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_show_meetings);
        recyclerView = findViewById(R.id.Committee_Meetings_RecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        meetingsArrayList = new ArrayList<Meetings>();
        myMRVAdapter = new my_RecycleVA_Meetings(CommitteeM_show_Meetings.this, meetingsArrayList);
        recyclerView.setAdapter(myMRVAdapter);
        myMRVAdapter.setOnItemClickListener(new my_RecycleVA_Meetings.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Meetings meetings = meetingsArrayList.get(position);
                meetingsArrayList.remove(position);
                myMRVAdapter.notifyItemRemoved(position);
                boolean removed = true;
                String Code = meetings.getMeetingCode();
                UpdateStatus(Code, removed);
            }
        });
        EventChangeListener();
    }

    private void UpdateStatus(String Code, boolean removed) {
        db = FirebaseFirestore.getInstance();

        Map<String,Object> meeting_code = new HashMap<>();
        meeting_code.put("removed",removed);

        db.collection("Meetings")
                .whereEqualTo("meetingCode",Code)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && !task.getResult().isEmpty()){

                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentID = documentSnapshot.getId();
                            db.collection("Meetings")
                                    .document(documentID)
                                    .update(meeting_code)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                        }
                                    });
                        }
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