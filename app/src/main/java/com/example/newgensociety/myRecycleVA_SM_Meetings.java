package com.example.newgensociety;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myRecycleVA_SM_Meetings extends RecyclerView.Adapter<myRecycleVA_SM_Meetings.MyViewHolder2>{

    @NonNull
    Context context;
    ArrayList<Meetings> meetingsArrayList;



    public myRecycleVA_SM_Meetings(@NonNull Context context, ArrayList<Meetings> maintenanceArrayList) {
        this.context = context;
        this.meetingsArrayList = maintenanceArrayList;
    }

    @NonNull
    @Override
    public myRecycleVA_SM_Meetings.MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.sm_meetings_recycleview,parent,false);
        return new myRecycleVA_SM_Meetings.MyViewHolder2(v);
    }



    @Override
    public void onBindViewHolder(@NonNull myRecycleVA_SM_Meetings.MyViewHolder2 holder, int position) {
        Meetings meetings = meetingsArrayList.get(position);

        holder.subject.setText(meetings.getSubject());
        holder.no_of_members.setText("Maximum "+String.valueOf(meetings.getNo_of_members())+" Members are Allowed From 1 Flat");
        holder.date.setText("Date : "+meetings.getDate());
        holder.time.setText(meetings.getTime());
        holder.description.setText(meetings.getDescription());
    }

    @Override
    public int getItemCount() {
        return meetingsArrayList.size();
    }

    public static class MyViewHolder2 extends RecyclerView.ViewHolder {

        TextView subject, no_of_members, date, time, description;


        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.Society_Meetings_Subject);
            no_of_members = itemView.findViewById(R.id.Society_Meetings_No_of_Members);
            date = itemView.findViewById(R.id.Society_Meetings_date);
            time = itemView.findViewById(R.id.Society_Meetings_time);
            description = itemView.findViewById(R.id.Society_Meetings_Description);

        }
    }
}
