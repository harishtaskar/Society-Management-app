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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class SocietyM_ComplainsRecycleView extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    ImageView Back;
    ArrayList<Complains> SM_complainsArrayList;
    myRecycleVA_SocietyComplains myMRVAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_mcomplains_recycle_view);

        Back = findViewById(R.id.Btn_Sm_Complain_Back);
        recyclerView = findViewById(R.id.SocietyM_ComplainsRecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        SM_complainsArrayList = new ArrayList<Complains>();
        myMRVAdapter = new myRecycleVA_SocietyComplains(SocietyM_ComplainsRecycleView.this,SM_complainsArrayList);
        recyclerView.setAdapter(myMRVAdapter);
        EventChangeListener();

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocietyM_ComplainsRecycleView.this,SocietyM_ComplainPage.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void EventChangeListener() {

        mAuth = FirebaseAuth.getInstance();
        String UserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        db = FirebaseFirestore.getInstance();
        db.collection("Complains").whereEqualTo("userId", UserId).orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("FireStore Error", error.getMessage());
                            return;
                        }
                        assert value != null;
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                SM_complainsArrayList.add(dc.getDocument().toObject(Complains.class));
                            }
                            myMRVAdapter.notifyDataSetChanged();
                        }

                    }
                });

    }

}