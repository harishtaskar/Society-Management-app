package com.example.newgensociety;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class SocietyM_HomePage extends AppCompatActivity {

    private MeowBottomNavigation bottomNavigation;
    RecyclerView recyclerView;
    ArrayList<Notice> NoticeArrayList;
    myRecycleVA_Society_Notice myAdapter;
    FirebaseFirestore db;
    Button EditProfile;
    CardView complain,maintenance,help,meeting,noticeboard,profileHelp,Addflat;
    RelativeLayout profile,home,notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_mhome_page);

        complain = findViewById(R.id.Society_Complain);
        maintenance = findViewById(R.id.Society_Maintenance);
        help = findViewById(R.id.Society_Help);
        meeting = findViewById(R.id.Society_Meeting);
        noticeboard = findViewById(R.id.Society_Notice);
        EditProfile = findViewById(R.id.Society_profile_edit_button);
        profileHelp = findViewById(R.id.Society_profile_help);
        Addflat = findViewById(R.id.Society_AddFlat);

        bottomNavigation = findViewById(R.id.bottomNavigation);
        profile = findViewById(R.id.profile);
        home = findViewById(R.id.home);
        notice = findViewById(R.id.notice);
        recyclerView = findViewById(R.id.Society_MainNotice_RecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bottomNavigation.show(2,true);
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.profile));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.homek));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.notice));


        db = FirebaseFirestore.getInstance();
        NoticeArrayList = new ArrayList<Notice>();
        myAdapter = new myRecycleVA_Society_Notice(SocietyM_HomePage.this,NoticeArrayList);
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

        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocietyM_HomePage.this,SocietyM_EditProfile.class);
                startActivity(intent);
            }
        });
        profileHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocietyM_HomePage.this,SocietyM_HelpPage.class);
                startActivity(intent);
            }
        });


        complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(SocietyM_HomePage.this,SocietyM_ComplainPage.class);
                startActivity(i);
            }
        });



        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(SocietyM_HomePage.this,SocietyM_HelpPage.class);
                startActivity(intent);
            }
        });

        maintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(SocietyM_HomePage.this,SocietyM_showMaintenance.class);
                startActivity(intent);
            }
        });

        meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocietyM_HomePage.this,SocietyM_Meetings_ShowPage.class);
                startActivity(intent);
            }
        });

        Addflat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocietyM_HomePage.this,SocietyM_showAddFlat.class);
                  startActivity(intent);
            }
        });



    }

    private void EventChangeListener() {

        db.collection("Notice").orderBy("date", Query.Direction.DESCENDING)
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
                                NoticeArrayList.add(dc.getDocument().toObject(Notice.class));
                            }
                            myAdapter.notifyDataSetChanged();
                        }

                    }
                });
    }
}