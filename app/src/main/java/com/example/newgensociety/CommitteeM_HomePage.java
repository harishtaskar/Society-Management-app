package com.example.newgensociety;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class CommitteeM_HomePage extends AppCompatActivity {

    private MeowBottomNavigation bottomNavigation;
    RelativeLayout profile,home,notice;

//    home page
    ImageView commitee_img, Profile;
    CardView cardmain1,cardmain2,Society_meetings,Complains,Helps,Helps2,allFlats,requests;
    TextView cm_name;
    RecyclerView recyclerView;
    ArrayList<Notice> noticeArrayList;
    myRecycleViewAdapter myAdapter;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    StorageReference storageRef;
//    ProgressDialog progressDialog;
    //profile page
    TextView cpsocietyname,cpsocietyaddress,cpmembername,cpmemberemail,cpabout,cpfeedback,cplogout,cptermsorcondition,cpversion;
    Button cpeditbtn;
    CardView addfalt,visitor;
    ImageView cpmemberimage;
    String S_name, Address, CM_Name, C_Email;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_mhome_page);
//        getSupportActionBar().hide();
        commitee_img = findViewById(R.id.commitee_dua_maintainance_img);
        cardmain1 = findViewById(R.id.add1);
        cardmain2 = findViewById(R.id.add2);
        Society_meetings = findViewById(R.id.Society_Meetings);
        Complains = findViewById(R.id.Committee_home_Complains);
        Helps = findViewById(R.id.Committee_home_Helps);
        cm_name = findViewById(R.id.Committee_Home_Cm_name);
        allFlats = findViewById(R.id.Committee_AddFlat);
        requests = findViewById(R.id.Committee_Requests);

        //profile page
        cpmemberimage = findViewById(R.id.committee_profile_img);
        addfalt = findViewById(R.id.committee_profile_add_flat);
        Helps2 = findViewById(R.id.committee_profile_help);
        addfalt = findViewById(R.id.committee_profile_add_flat);
        cpsocietyname = findViewById(R.id.committee_profile_society_name);
        cpsocietyaddress = findViewById(R.id.committee_profile_society_address);
        cpmembername = findViewById(R.id.committee_profile_member_name);
        cpmemberemail = findViewById(R.id.committee_profile_member_email);
        cpmembername = findViewById(R.id.committee_profile_member_name);
        cpabout = findViewById(R.id.committee_profile_about);
        cpfeedback = findViewById(R.id.committee_profile_support_feedback);
        cplogout = findViewById(R.id.committee_profile_logout);
        cptermsorcondition = findViewById(R.id.committee_profile_terms_condition);
        cpversion = findViewById(R.id.committee_profile_version);
        cpeditbtn = findViewById(R.id.committee_profile_edit_button);
        Profile = findViewById(R.id.home_btnprofile);
        mAuth = FirebaseAuth.getInstance();
        cplogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(CommitteeM_HomePage.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Helps2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommitteeM_HomePage.this,CommitteeM_show_Meetings.class);
                startActivity(intent);
            }
        });

        allFlats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommitteeM_HomePage.this,CommitteeM_AllFlats.class);
                startActivity(intent);
            }
        });
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommitteeM_HomePage.this,CommitteeM_EditProfile.class);
                startActivity(intent);
            }
        });

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

        String CM_Email = getIntent().getStringExtra("key_cm_email");
        String CM_name = getIntent().getStringExtra("key_cm_name");
        cm_name.setText("Hi, "+CM_name);
        cpmemberemail.setText(CM_Email);





        noticeArrayList = new ArrayList<Notice>();
        myAdapter = new myRecycleViewAdapter(CommitteeM_HomePage.this,noticeArrayList);
        recyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new myRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Notice notice = noticeArrayList.get(position);
                noticeArrayList.remove(position);
                myAdapter.notifyItemRemoved(position);
                boolean removed = true;
                String Code = notice.getNotice_code();
                UpdateStatus(Code,removed);
            }
        });
        EventChangeListener();
        mAuth = FirebaseAuth.getInstance();
        String UserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        db = FirebaseFirestore.getInstance();
        db.collection("Society").whereEqualTo("userId",UserId)
                                .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()){
                                                    Toast.makeText(CommitteeM_HomePage.this, "Successful",
                                                            Toast.LENGTH_SHORT).show();
                                                    for ( QueryDocumentSnapshot document : task.getResult()){
                                                        Log.d(TAG, document.getId()+ "=>"+ document.getData());
                                                        S_name = Objects.requireNonNull(document.get("Society_name")).toString();
                                                        cpsocietyname.setText(S_name);
                                                        Address = Objects.requireNonNull(document.get("Address")).toString();
                                                        cpsocietyaddress.setText(Address);
                                                        CM_Name = Objects.requireNonNull(document.get("Cm_name")).toString();
                                                        cpmembername.setText(CM_Name);
                                                        cm_name.setText("Hi, "+CM_Name);
                                                        C_Email = Objects.requireNonNull(document.get("Cm_email")).toString();
                                                        cpmemberemail.setText(C_Email);
                                                    }

                                                }else{
                                                    Toast.makeText(CommitteeM_HomePage.this, "Failed",
                                                            Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });

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

        cpeditbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommitteeM_HomePage.this,CommitteeM_EditProfile.class);
                startActivity(intent);
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
        cpmemberimage.setClipToOutline(true);
        storageRef = FirebaseStorage.getInstance().getReference("image/"+UserId);
        try {
            File localFile = File.createTempFile("tempfile",".jpeg");
            storageRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            cpmemberimage.setImageBitmap(bitmap);

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
                Intent i  = new Intent(CommitteeM_HomePage.this, CommitteeM_NoticePubllishing_Page.class);
                startActivity(i);
            }
        });
        Society_meetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommitteeM_HomePage.this,CommitteeM_MeetingsPage.class);
                startActivity(intent);
            }
        });
        Complains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommitteeM_HomePage.this,CommitteeM_showComplains.class);
                startActivity(intent);
            }
        });

        Helps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommitteeM_HomePage.this,CommitteeM_showHelp.class);
                startActivity(intent);
            }
        });

        requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommitteeM_HomePage.this,CommitteeM_showHall.class);
                startActivity(intent);
            }
        });


    }

     private void UpdateStatus(String Code, boolean removed) {
        db = FirebaseFirestore.getInstance();

        Map<String,Object> notice_code = new HashMap<>();
        notice_code.put("removed",removed);

        db.collection("Notice")
                .whereEqualTo("notice_code",Code)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && !task.getResult().isEmpty()){

                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentID = documentSnapshot.getId();
                            db.collection("Notice")
                                    .document(documentID)
                                    .update(notice_code)
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
                        noticeArrayList.add(dc.getDocument().toObject(Notice.class));
                    }
                    myAdapter.notifyDataSetChanged();
                }

            }
        });
    }
}