package com.example.newgensociety;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class myRecycleVA_SocietyComplains extends RecyclerView.Adapter<myRecycleVA_SocietyComplains.MyViewHolder2>{


    Context context;
    FirebaseFirestore db;
    ArrayList<Complains> SM_complainsArrayList;

    public myRecycleVA_SocietyComplains(Context context, ArrayList<Complains> SM_complainsArrayList) {
        this.context = context;
        this.SM_complainsArrayList = SM_complainsArrayList;
    }

    @NonNull
    @Override
    public myRecycleVA_SocietyComplains.MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.sm_complains_recycleview,parent,false);
        return new myRecycleVA_SocietyComplains.MyViewHolder2(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myRecycleVA_SocietyComplains.MyViewHolder2 holder, int position) {

        Complains complains = SM_complainsArrayList.get(position);

        holder.name.setText("~"+complains.getName());
        holder.about.setText("About : "+complains.getAbout());
        holder.description.setText(complains.getDescription());
        holder.date.setText(complains.getDate());

    }

    @Override
    public int getItemCount() {
        return SM_complainsArrayList.size();
    }

    public static class MyViewHolder2 extends RecyclerView.ViewHolder {

        TextView name, about, description, date;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Society_Complain_RecycleView_name);
            about = itemView.findViewById(R.id.Society_Complain_RecycleView_about);
            date = itemView.findViewById(R.id.Society_Complain_RecycleView_date);
            description = itemView.findViewById(R.id.Society_Complain_RecycleView_description);
        }
    }
}
