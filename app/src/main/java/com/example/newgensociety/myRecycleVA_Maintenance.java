package com.example.newgensociety;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myRecycleVA_Maintenance extends RecyclerView.Adapter<myRecycleVA_Maintenance.MyViewHolder>{
    Context context;
    ArrayList<Maintenance> maintenanceArrayList;
    public myRecycleVA_Maintenance(Context context, ArrayList<Maintenance> maintenanceArrayList) {
        this.context = context;
        this.maintenanceArrayList = maintenanceArrayList;
    }

    @NonNull
    @Override
    public myRecycleVA_Maintenance.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cm_maintenance_recycle_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myRecycleVA_Maintenance.MyViewHolder holder, int position) {
        Maintenance maintenance = maintenanceArrayList.get(position);

        holder.flat_no.setText(maintenance.getFlat_no());
        holder.amount.setText(maintenance.getAmount());
        holder.due_date.setText((CharSequence) maintenance.getDue_date());
        holder.discription.setText(maintenance.getDiscription());
        holder.discount.setText(maintenance.getDiscount());
    }

    @Override
    public int getItemCount() {
        return maintenanceArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView flat_no, amount, due_date, discription, discount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            flat_no = itemView.findViewById(R.id.Committee_MaintenanceR_FlatNo);
            amount = itemView.findViewById(R.id.Committee_MaintenanceR_MainAmount);
            due_date = itemView.findViewById(R.id.Committee_MaintenanceR_DueDate);
            discription = itemView.findViewById(R.id.Committee_MaintenanceR_Discription);
            discount = itemView.findViewById(R.id.Committee_MaintenanceR_DiscountText);
        }
    }
}
