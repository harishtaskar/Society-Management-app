package com.example.newgensociety;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class my_RecycleVA_Meetings extends RecyclerView.Adapter<my_RecycleVA_Meetings.MyViewHolder2> {
    @NonNull

    Context context;
    ArrayList<Meetings> meetingsArrayList;
    private my_RecycleVA_Meetings.OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(my_RecycleVA_Meetings.OnItemClickListener clickListener){
        listener = clickListener;
    }

    public my_RecycleVA_Meetings(Context context, ArrayList<Meetings> maintenanceArrayList) {
        this.context = context;
        this.meetingsArrayList = maintenanceArrayList;
    }


    @Override
    public my_RecycleVA_Meetings.MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cm_meetings_recyle_layout,parent,false);
        return new my_RecycleVA_Meetings.MyViewHolder2(v,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull my_RecycleVA_Meetings.MyViewHolder2 holder, int position) {
        Meetings meetings = meetingsArrayList.get(position);

        holder.subject.setText(meetings.getSubject());
        holder.no_of_members.setText("Maximum "+String.valueOf(meetings.getNo_of_members())+" Members are Allowed From 1 Flat");
        holder.date.setText("Date : "+meetings.getDate());
        holder.time.setText(meetings.getTime());
        holder.description.setText(meetings.getDescription());
        holder.code.setText("Notice Code : "+meetings.getMeetingCode());
        holder.removed = meetings.getRemoved();

    }

    @Override
    public int getItemCount() {
        return meetingsArrayList.size();
    }
    public static class MyViewHolder2 extends RecyclerView.ViewHolder{

        TextView subject, no_of_members, date, time, description, code;
        ImageView delete;
        boolean removed = true;

        public MyViewHolder2(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            subject = itemView.findViewById(R.id.Committee_Meetings_Subject);
            no_of_members = itemView.findViewById(R.id.Committee_Meetings_No_of_Members);
            date = itemView.findViewById(R.id.Committee_Meetings_date);
            time = itemView.findViewById(R.id.Committee_Meetings_time);
            description = itemView.findViewById(R.id.Committee_Meetings_Description);
            delete = itemView.findViewById(R.id.btn_delete);
            code = itemView.findViewById(R.id.Committee_Meeting_RecycleView_code);
            String Code = code.toString();

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());

                }
            });
        }
    }

}
