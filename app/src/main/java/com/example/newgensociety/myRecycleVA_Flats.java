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

        holder.Name.setText("Name : "+flats.getName());
        holder.Flat_no.setText("Flat Number : "+String.valueOf(flats.getFlat_No()));
        holder.Mobile.setText("Mobile : "+flats.getMobile());
        holder.Status.setText(flats.getStatus());
        holder.Date.setText(flats.getDate());

    }

    @Override
    public int getItemCount() {
        return flatsArrayList.size();
    }
    public static class MyViewHolder2 extends RecyclerView.ViewHolder{

        TextView Name, Flat_no, Date, Mobile, Status;


        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.Society_Flats_RecycleView_Name);
            Flat_no = itemView.findViewById(R.id.Society_Flats_RecycleView_FlatNumber);
            Date = itemView.findViewById(R.id.Society_Flats_RecycleView_date);
            Mobile = itemView.findViewById(R.id.Society_Flats_RecycleView_Mobile);
            Status = itemView.findViewById(R.id.Society_Flats_RecycleView_Status);

        }
    }


}
