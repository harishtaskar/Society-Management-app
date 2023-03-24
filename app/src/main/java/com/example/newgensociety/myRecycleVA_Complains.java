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

public class myRecycleVA_Complains extends RecyclerView.Adapter<myRecycleVA_Complains.MyViewHolder2>{

    @NonNull

    Context context;
    ArrayList<Complains> complainsArrayList;

    public myRecycleVA_Complains(Context context, ArrayList<Complains> complainsArrayList) {
        this.context = context;
        this.complainsArrayList = complainsArrayList;
    }

    @Override
    public myRecycleVA_Complains.MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cm_complains_recyleview,parent,false);
        return new myRecycleVA_Complains.MyViewHolder2(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myRecycleVA_Complains.MyViewHolder2 holder, int position) {

        Complains complains = complainsArrayList.get(position);

        holder.name.setText("~"+complains.getName());
        holder.about.setText("About : "+complains.getAbout());
        holder.description.setText(complains.getDescription());
        holder.date.setText(complains.getDate());
    }

    @Override
    public int getItemCount() {
        return complainsArrayList.size();
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        TextView name, about, description, date;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Committee_Complain_RecycleView_name);
            about = itemView.findViewById(R.id.Committee_Complain_RecycleView_about);
            date = itemView.findViewById(R.id.Committee_Complain_RecycleView_date);
            description = itemView.findViewById(R.id.Committee_Complain_RecycleView_description);
        }
    }
}
