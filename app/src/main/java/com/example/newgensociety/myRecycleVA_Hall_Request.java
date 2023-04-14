package com.example.newgensociety;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myRecycleVA_Hall_Request extends RecyclerView.Adapter<myRecycleVA_Hall_Request.MyViewHolder2>{

    Context context;
    ArrayList<Hall_Request> hallRequestsArrayList;

    public myRecycleVA_Hall_Request(Context context, ArrayList<Hall_Request> hallRequestsArrayList) {
        this.context = context;
        this.hallRequestsArrayList = hallRequestsArrayList;
    }
    @NonNull
    @Override
    public myRecycleVA_Hall_Request.MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.society_hall_request_recycleview,parent,false);
        return new MyViewHolder2(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myRecycleVA_Hall_Request.MyViewHolder2 holder, int position) {

        Hall_Request hall_request = hallRequestsArrayList.get(position);

        holder.HallTitle.setText(hall_request.getHall_title());
        holder.Name.setText( hall_request.getName());
        holder.Description.setText(hall_request.getDescription());
        holder.Date.setText(hall_request.getDate());
        holder.Time.setText(hall_request.getTime());
        holder.Approved = hall_request.isApproved();

        Log.i("intent_","==+=="+hall_request.isApproved());
        Log.i("intent_","==+=="+hall_request.getDate());
        if(holder.Approved){
            holder.Status.setTextColor(Color.GREEN);
            holder.Status.setText("Status : Approved");
        }
    }

    @Override
    public int getItemCount() {
        return hallRequestsArrayList.size();
    }

    public static class MyViewHolder2 extends RecyclerView.ViewHolder {

        TextView HallTitle, Name, Description, Date, Time, Status;
        boolean Approved;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            HallTitle = itemView.findViewById(R.id.Society_Hall_Requests_HallTitle);
            Name = itemView.findViewById(R.id.Society_Hall_Requests_Name);
            Description = itemView.findViewById(R.id.Society_Hall_Requests_Description);
            Date = itemView.findViewById(R.id.Society_Hall_Requests_Date);
            Time = itemView.findViewById(R.id.Society_Hall_Requests_Time);
            Status = itemView.findViewById(R.id.Society_Hall_Requests_Status);
        }
    }
}
