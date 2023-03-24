package com.example.newgensociety;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myRecycleVA_Helps extends RecyclerView.Adapter<myRecycleVA_Helps.MyViewHolder2>{
    @NonNull
    Context context;
    ArrayList<Helps> helpsArrayList;

    public myRecycleVA_Helps(Context context, ArrayList<Helps> helpsArrayList) {
        this.context = context;
        this.helpsArrayList = helpsArrayList;
    }

    @Override
    public myRecycleVA_Helps.MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cm_helps_recycleview,parent,false);
        return new myRecycleVA_Helps.MyViewHolder2(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myRecycleVA_Helps.MyViewHolder2 holder, int position) {

        Helps helps = helpsArrayList.get(position);

        holder.name.setText("~"+helps.getName());
        holder.flat_no.setText("Flat Number : "+helps.getFlat_no());
        holder.help.setText(helps.getHelp());
        holder.date.setText(helps.getDate());

    }

    @Override
    public int getItemCount() {
        return helpsArrayList.size();
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder{

        TextView name, flat_no, help, date;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Committee_Help_RecycleView_name);
            flat_no = itemView.findViewById(R.id.Committee_Help_RecycleView_Flat_No);
            date = itemView.findViewById(R.id.Committee_Help_RecycleView_date);
            help = itemView.findViewById(R.id.Committee_Help_RecycleView_Help);
        }


    }
}
