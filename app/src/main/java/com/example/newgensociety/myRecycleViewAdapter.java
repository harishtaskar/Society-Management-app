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

        holder.Subject.setText(notice.subject);
        holder.Notice.setText(notice.notice);
        holder.CM_name.setText(notice.cm_name);

    }



    @Override
    public int getItemCount() {
        return noticesArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Subject, Notice, CM_name, date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Subject = itemView.findViewById(R.id.Committee_Notice_RecycleView_Subject);
            Notice = itemView.findViewById(R.id.Committee_Notice_RecycleView_Notice);
            CM_name = itemView.findViewById(R.id.Committee_Notice_RecycleView_CM_name);
            date = itemView.findViewById(R.id.Committee_Notice_RecycleView_date);

            Calendar calendar = Calendar.getInstance();
            String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

            date.setText(currentDate);

        }
    }
}
