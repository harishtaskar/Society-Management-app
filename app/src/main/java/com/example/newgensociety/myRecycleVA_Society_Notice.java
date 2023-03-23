package com.example.newgensociety;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myRecycleVA_Society_Notice extends RecyclerView.Adapter<myRecycleVA_Society_Notice.MyViewHolder>{
    Context context;
    ArrayList<Notice> NoticesArrayList;

    public myRecycleVA_Society_Notice(Context context, ArrayList<Notice> NoticesArrayList) {
        this.context = context;
        this.NoticesArrayList = NoticesArrayList;
    }

    @NonNull
    @Override
    public myRecycleVA_Society_Notice.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.societym_notice_recycleview, parent, false);
        return new myRecycleVA_Society_Notice.MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull myRecycleVA_Society_Notice.MyViewHolder holder, int position) {

        Notice notice = NoticesArrayList.get(position);

        holder.Subject.setText(notice.getSubject());
        holder.Notice.setText(notice.getNotice());
        holder.CM_name.setText(notice.getCm_name());
        holder.Date.setText(notice.getDate());
    }


    @Override
    public int getItemCount() {
        return NoticesArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Subject, Notice, CM_name, Date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Subject = itemView.findViewById(R.id.Society_Notice_RecycleView_Subject);
            Notice = itemView.findViewById(R.id.Society_Notice_RecycleView_Notice);
            CM_name = itemView.findViewById(R.id.Society_Notice_RecycleView_CM_name);
            Date = itemView.findViewById(R.id.Society_Notice_RecycleView_date);
        }
    }
}
