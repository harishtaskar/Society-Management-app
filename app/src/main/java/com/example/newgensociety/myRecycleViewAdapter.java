package com.example.newgensociety;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.security.auth.Subject;

public class myRecycleViewAdapter extends RecyclerView.Adapter<myRecycleViewAdapter.MyViewHolder>{

    Context context;
    ArrayList<Notice> noticesArrayList;

    public myRecycleViewAdapter(Context context, ArrayList<Notice> noticesArrayList) {
        this.context = context;
        this.noticesArrayList = noticesArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cm_notice_recycleview,parent,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Notice notice = noticesArrayList.get(position);

        holder.Subject.setText(notice.getSubject());
        holder.Notice.setText(notice.getNotice());
        holder.CM_name.setText(notice.getCm_name());
        holder.Date.setText(notice.getDate());
    }



    @Override
    public int getItemCount() {
        return noticesArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Subject, Notice, CM_name, Date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Subject = itemView.findViewById(R.id.Committee_Notice_RecycleView_Subject);
            Notice = itemView.findViewById(R.id.Committee_Notice_RecycleView_Notice);
            CM_name = itemView.findViewById(R.id.Committee_Notice_RecycleView_CM_name);
            Date = itemView.findViewById(R.id.Committee_Notice_RecycleView_date);
        }
    }
}
