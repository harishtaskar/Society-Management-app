package com.example.newgensociety;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class SocietyM_HomePage extends AppCompatActivity {

    private MeowBottomNavigation bottomNavigation;

    CardView complain,maintenance,help,meeting,noticeboard;
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

        bottomNavigation = findViewById(R.id.bottomNavigation);
        profile = findViewById(R.id.profile);
        home = findViewById(R.id.home);
        notice = findViewById(R.id.notice);

        bottomNavigation.show(2,true);
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.profile));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.homek));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.notice));


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
        complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i  = new Intent(smember_homepage.this,complain_box.class);
//                startActivity(i);
            }
        });

        maintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent  = new Intent(smember_homepage.this,smember_maintenance.class);
//                startActivity(intent);
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent  = new Intent(smember_homepage.this,help.class);
//                startActivity(intent);
            }
        });



    }
}