package com.example.newgensociety;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class myRecycleVA_CM_Hall_Request extends RecyclerView.Adapter<myRecycleVA_CM_Hall_Request.MyViewHolder2>{

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    Context context;
    ArrayList<Hall_Request> cm_hallRequestsArrayList;

    public myRecycleVA_CM_Hall_Request(Context context, ArrayList<Hall_Request> cm_hallRequestsArrayList) {
        this.context = context;
        this.cm_hallRequestsArrayList = cm_hallRequestsArrayList;
    }

    @NonNull
    @Override
    public myRecycleVA_CM_Hall_Request.MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.committee_hall_request_recycleview,parent,false);
        return new myRecycleVA_CM_Hall_Request.MyViewHolder2(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myRecycleVA_CM_Hall_Request.MyViewHolder2 holder, int position) {

        Hall_Request cm_hall_request = cm_hallRequestsArrayList.get(position);

        holder.HallTitle.setText(cm_hall_request.getHall_title());
        holder.Name.setText( cm_hall_request.getName());
        holder.Description.setText(cm_hall_request.getDescription());
        holder.Date.setText(cm_hall_request.getDate());
        holder.Time.setText(cm_hall_request.getTime());
        holder.Approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String book_id = cm_hall_request.getBookingCode();
                    db = FirebaseFirestore.getInstance();
                    boolean status = true;
                    boolean status_false = false;
                    db.collection("Booking Requests")
                            .document(book_id)
                            .update("isApproved",status,"isNotApproved",status_false)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });

            }
        });

        holder.NotApproved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String book_id = cm_hall_request.getBookingCode();
                db = FirebaseFirestore.getInstance();
                boolean status = true;
                boolean status_false = false;
                db.collection("Booking Requests")
                        .document(book_id)
                        .update("isNotApproved",status,"isApproved",status_false)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });


        db = FirebaseFirestore.getInstance();
        String book_id = cm_hall_request.getBookingCode();
        db.collection("Booking Requests")
                .document(book_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            boolean Status = (boolean) Objects.requireNonNull(task.getResult().get("isApproved"));
                            boolean StatusNot = (boolean) Objects.requireNonNull(task.getResult().get("isNotApproved"));
                            if(Status){
                                holder.Approved.setChecked(true);
                            }if(StatusNot){
                                holder.NotApproved.setChecked(true);
                            }

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });



    }

    @Override
    public int getItemCount() {
        return cm_hallRequestsArrayList.size();
    }

    public static class MyViewHolder2 extends RecyclerView.ViewHolder {
        TextView HallTitle, Name, Description, Date, Time;
        RadioButton Approved,NotApproved;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            HallTitle = itemView.findViewById(R.id.Committee_Hall_Requests_HallTitle);
            Name = itemView.findViewById(R.id.Committee_Hall_Requests_Name);
            Description = itemView.findViewById(R.id.Committee_Hall_Requests_Description);
            Date = itemView.findViewById(R.id.Committee_Hall_Requests_Date);
            Time = itemView.findViewById(R.id.Committee_Hall_Requests_Time);
            Approved = itemView.findViewById(R.id.Committee_Hall_Requests_isApproved);
            NotApproved = itemView.findViewById(R.id.Committee_Hall_Requests_isNotApproved);

        }
    }
}
