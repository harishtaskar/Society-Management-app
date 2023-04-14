package com.example.newgensociety;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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

    }

    @Override
    public int getItemCount() {
        return cm_hallRequestsArrayList.size();
    }

    public static class MyViewHolder2 extends RecyclerView.ViewHolder {
        TextView HallTitle, Name, Description, Date, Time;
        CheckBox Approved;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            HallTitle = itemView.findViewById(R.id.Committee_Hall_Requests_HallTitle);
            Name = itemView.findViewById(R.id.Committee_Hall_Requests_Name);
            Description = itemView.findViewById(R.id.Committee_Hall_Requests_Description);
            Date = itemView.findViewById(R.id.Committee_Hall_Requests_Date);
            Time = itemView.findViewById(R.id.Committee_Hall_Requests_Time);
            Approved = itemView.findViewById(R.id.Committee_Hall_Requests_isApproved);


        }
    }
}
