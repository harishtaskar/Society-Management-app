package com.example.newgensociety;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myRecycleVA_FlatSpinner extends RecyclerView.Adapter<myRecycleVA_FlatSpinner.MyViewHolder2>  {

    @NonNull

    Context context;
    ArrayList<FlatsSpinner> flatsSpinnerArrayList;
    ArrayList<Integer> flatArrayList;

    public myRecycleVA_FlatSpinner(Context context, ArrayList<FlatsSpinner> flatsSpinnerArrayList) {
        this.context = context;
        this.flatsSpinnerArrayList = flatsSpinnerArrayList;
    }

    public myRecycleVA_FlatSpinner(SocietyM_showMaintenance context, ArrayList<Integer> flatsArrayList, int simple_spinner_item) {
        this.context = context;
        this.flatArrayList = flatsArrayList;
    }

    @Override
    public myRecycleVA_FlatSpinner.MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.sm_flats_recycleview,parent,false);
        return new myRecycleVA_FlatSpinner.MyViewHolder2(v,flatArrayList);
    }

    @Override
    public void onBindViewHolder(@NonNull myRecycleVA_FlatSpinner.MyViewHolder2 holder, int position) {
        FlatsSpinner flats = flatsSpinnerArrayList.get(position);
        holder.flat_no.setText(String.valueOf(flats.getFlat_no()));
//        holder.flat_no.setText(String.valueOf(flats.getFlat_no()));
    }

    @Override
    public int getItemCount() {
        return flatsSpinnerArrayList.size();
    }


    public class MyViewHolder2 extends RecyclerView.ViewHolder{
        TextView flat_no;


        public MyViewHolder2(@NonNull View itemView, ArrayList<Integer> flatArrayList) {
            super(itemView);
            flat_no = itemView.findViewById(R.id.Society_Flats_RecycleView_FlatNumber);
            Integer flat = Integer.parseInt(flat_no.getText().toString());
            flatArrayList.add(flat);
        }
    }
}
