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

public class myRecycleVA_Flats extends RecyclerView.Adapter<myRecycleVA_Flats.MyViewHolder2>{
    Context context;
    ArrayList<Flats> flatsArrayList;

    public myRecycleVA_Flats(Context context, ArrayList<Flats> flatsArrayList) {
        this.context = context;
        this.flatsArrayList = flatsArrayList;
    }


    @Override
    public myRecycleVA_Flats.MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.sm_flats_recycleview,parent,false);
        return new myRecycleVA_Flats.MyViewHolder2(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myRecycleVA_Flats.MyViewHolder2 holder, int position) {
        Flats flats = flatsArrayList.get(position);


        holder.flat_no.setText("Flat Number : "+String.valueOf(flats.getFlat_no()));
        holder.name.setText("Name : "+flats.getName());
        holder.mobile.setText("Mobile : "+flats.getMobile());
        holder.date.setText(flats.getDate());
        holder.status.setText(flats.getStatus());


    }

    @Override
    public int getItemCount() {
        return flatsArrayList.size();
    }
    public static class MyViewHolder2 extends RecyclerView.ViewHolder{

        TextView name, flat_no, date, mobile, status;


        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Society_Flats_RecycleView_Name);
            flat_no = itemView.findViewById(R.id.Society_Flats_RecycleView_FlatNumber);
            date = itemView.findViewById(R.id.Society_Flats_RecycleView_date);
            mobile = itemView.findViewById(R.id.Society_Flats_RecycleView_Mobile);
            status = itemView.findViewById(R.id.Society_Flats_RecycleView_Status);

        }
    }


}
