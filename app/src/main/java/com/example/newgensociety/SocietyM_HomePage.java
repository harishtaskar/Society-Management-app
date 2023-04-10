package com.example.newgensociety;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class SocietyM_HomePage extends AppCompatActivity {

    private MeowBottomNavigation bottomNavigation;
    RecyclerView recyclerView;
    ArrayList<Notice> NoticeArrayList;
    myRecycleVA_Society_Notice myAdapter;
    FirebaseFirestore db;
    Button EditProfile;
    CardView complain,maintenance,help,meeting,amenity,profileHelp,Addflat,Addflatp;
    TextView logout,SocietyM_name,SocietyM_email,SocietyName,SocietyAddress,SM_Name,SM_Share;
    RelativeLayout profile,home,notice;
    FirebaseAuth mAuth;
    String S_name,Address;
    ImageView imageView, Profile;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_mhome_page);

        complain = findViewById(R.id.Society_Complain);
        maintenance = findViewById(R.id.Society_Maintenance);
        help = findViewById(R.id.Society_Help);
        meeting = findViewById(R.id.Society_Meeting);
        amenity = findViewById(R.id.Society_Amenity);
        EditProfile = findViewById(R.id.Society_profile_edit_button);
        profileHelp = findViewById(R.id.Society_profile_help);
        Addflat = findViewById(R.id.Society_AddFlat);
        Addflatp = findViewById(R.id.SocietyM_profile_add_flat);
        logout = findViewById(R.id.SocietyM_profile_logout);
        SocietyM_name = findViewById(R.id.SocietyM_profile_member_name);
        SocietyM_email = findViewById(R.id.SocietyM_profile_member_email);
        SocietyName = findViewById(R.id.SocietyM_profile_society_name);
        SocietyAddress = findViewById(R.id.SocietyM_profile_society_address);
        imageView = findViewById(R.id.SocietyM_profile_img);
        SM_Name = findViewById(R.id.Society_Home_sm_name);
        Profile = findViewById(R.id.home_btnprofile);
        SM_Share = findViewById(R.id.Society_profile_share);
        mAuth = FirebaseAuth.getInstance();

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


        mDatabase = FirebaseDatabase.getInstance().getReference();
        db = FirebaseFirestore.getInstance();
        NoticeArrayList = new ArrayList<Notice>();
        myAdapter = new myRecycleVA_Society_Notice(SocietyM_HomePage.this,NoticeArrayList);
        recyclerView.setAdapter(myAdapter);

        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocietyM_HomePage.this,SocietyM_EditProfile.class);
                startActivity(intent);
            }
        });

        String UserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        db.collection("Society_Members").whereEqualTo("userId",UserId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SocietyM_HomePage.this, "Successful",
                                    Toast.LENGTH_SHORT).show();
                            for ( QueryDocumentSnapshot document : task.getResult()){
                                Log.d(TAG, document.getId()+ "=>"+ document.getData());
                                String SM_name = Objects.requireNonNull(document.get("Member_Name")).toString();
                                SocietyM_name.setText(SM_name);
                                SM_Name.setText("Hi , "+ SM_name);
                                String SM_Email = Objects.requireNonNull(document.get("Email")).toString();
                                SocietyM_email.setText(SM_Email);
                            }


                        }else{
                            Toast.makeText(SocietyM_HomePage.this, "Failed",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        db.collection("C_Members")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SocietyM_HomePage.this, "Successful",
                                    Toast.LENGTH_SHORT).show();
                            for ( QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + "=>" + document.getData());
                                S_name = Objects.requireNonNull(document.get("Society_name")).toString();
                                SocietyName.setText(S_name);
                                Address = Objects.requireNonNull(document.get("Address")).toString();
                                SocietyAddress.setText(Address);
                            }

                        }else{
                            Toast.makeText(SocietyM_HomePage.this, "Failed",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

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

        imageView.setClipToOutline(true);
        StorageReference storageRef = FirebaseStorage.getInstance().getReference("image/" + UserId);
        try {
            File localFile = File.createTempFile("tempfile",".jpeg");
            storageRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            imageView.setImageBitmap(bitmap);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
        Addflatp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocietyM_HomePage.this,SocietyM_AddFlat.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(SocietyM_HomePage.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        String appUrl = getString(R.string.app_url);
        SM_Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, appUrl);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
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

        amenity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocietyM_HomePage.this,SocietyM_HallShow.class);
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
        db = FirebaseFirestore.getInstance();

                db.collection("Notice").orderBy("date", Query.Direction.DESCENDING).whereEqualTo("removed",false)
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
                                NoticeArrayList.add(dc.getDocument().toObject(Notice.class));
                            }
                            myAdapter.notifyDataSetChanged();
                        }

                    }
                });
    }
}