package com.example.newgensociety;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myRecycleVA_Hall extends RecyclerView.Adapter<myRecycleVA_Hall.MyViewHolder2>{

    Context context;
    ArrayList<Hall> hallArraylist;

    public myRecycleVA_Hall(Context context, ArrayList<Hall> hallArraylist) {
        this.context = context;
        this.hallArraylist = hallArraylist;
    }


    @NonNull
    @Override
    public myRecycleVA_Hall.MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.hall_recycleview_layout,parent,false);
        return new myRecycleVA_Hall.MyViewHolder2(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myRecycleVA_Hall.MyViewHolder2 holder, @SuppressLint("RecyclerView") int position) {
        Hall hall = hallArraylist.get(position);

        holder.HallTitle.setText(hall.hall_title);
        holder.HallSize.setText("Hall Size : "+ hall.hall_size );
        holder.Description.setText(hall.getDescription());
        holder.btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Book_Hall.class);
                intent.putExtra("HallTitle",hallArraylist.get(position).getHall_title());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hallArraylist.size();
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder{
        TextView HallTitle, HallSize, Description;
        Button btnBook;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            HallTitle = itemView.findViewById(R.id.Hall_RecycleView_Title);
            HallSize = itemView.findViewById(R.id.Hall_RecycleView_Size);
            Description = itemView.findViewById(R.id.Hall_RecycleView_Description);
            btnBook = itemView.findViewById(R.id.btn_book_hall);
        }
    }
}
