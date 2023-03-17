package com.example.newgensociety;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class CommitteeM_HomePage extends AppCompatActivity {

    private MeowBottomNavigation bottomNavigation;
    RelativeLayout profile,home,notice;

    ImageView commitee_img;
    CardView cardmain1,cardmain2;

    RecyclerView recyclerView;
    ArrayList<Notice> noticeArrayList;
    myRecycleViewAdapter myAdapter;
    FirebaseFirestore db;
//    ProgressDialog progressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_mhome_page);
//        getSupportActionBar().hide();
        commitee_img = findViewById(R.id.commitee_dua_maintainance_img);
        cardmain1 = findViewById(R.id.add1);
        cardmain2 = findViewById(R.id.add2);

//        progressDialog = new ProgressDialog(this);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Fetching Data");
//        progressDialog.show();


        recyclerView = findViewById(R.id.Committee_Notice_RecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bottomNavigation = findViewById(R.id.bottomNavigation);
        profile = findViewById(R.id.profile);
        home = findViewById(R.id.home);
        notice = findViewById(R.id.notice);

        bottomNavigation.show(2,true);
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.profile));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.homek));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.notice));


        db = FirebaseFirestore.getInstance();
        noticeArrayList = new ArrayList<Notice>();
        myAdapter = new myRecycleViewAdapter(CommitteeM_HomePage.this,noticeArrayList);
        recyclerView.setAdapter(myAdapter);
        EventChangeListener();

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES

                switch (model.getId()){

                    case 1:
                        profile.setVisibility(View.VISIBLE);
                        home.setVisibility(View.GONE);
                        notice.setVisibility(View.GONE);

                        break;


                    case 2:

                        profile.setVisibility(View.GONE);
                        home.setVisibility(View.VISIBLE);
                        notice.setVisibility(View.GONE);

                        break;


                    case 3:

                        profile.setVisibility(View.GONE);
                        home.setVisibility(View.GONE);
                        notice.setVisibility(View.VISIBLE);

                        break;
                }


                return null;
            }
        });

        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES

                switch (model.getId()){
                    case 1:
                        profile.setVisibility(View.VISIBLE);
                        home.setVisibility(View.GONE);
                        notice.setVisibility(View.GONE);

                        break;
                }
                return null;
            }
        });

        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES

                switch (model.getId()){
                    case 2:
                        profile.setVisibility(View.GONE);
                        home.setVisibility(View.VISIBLE);
                        notice.setVisibility(View.GONE);

                        break;
                }
                return null;
            }
        });

        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES

                switch (model.getId()){
                    case 3:

                        profile.setVisibility(View.GONE);
                        home.setVisibility(View.GONE);
                        notice.setVisibility(View.VISIBLE);

                        break;
                }
                return null;
            }
        });

        cardmain1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(CommitteeM_HomePage.this,CommitteeM_MaintenancePage.class);
                startActivity(i);
            }
        });
        cardmain2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(CommitteeM_HomePage.this,Committee_NoticePubllishing_Page.class);
                startActivity(i);
            }
        });


    }

    private void EventChangeListener() {

        db.collection("Notice")
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
                        noticeArrayList.add(dc.getDocument().toObject(Notice.class));
                    }

                    myAdapter.notifyDataSetChanged();

                }

            }
        });
    }
}