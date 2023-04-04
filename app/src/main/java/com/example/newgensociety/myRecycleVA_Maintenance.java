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

public class myRecycleVA_Maintenance extends RecyclerView.Adapter<myRecycleVA_Maintenance.MyViewHolder1>{
    Context context;
    ArrayList<Maintenance> maintenanceArrayList;
    private myRecycleVA_Maintenance.OnItemClickListener listener;
    static FirebaseFirestore db;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(myRecycleVA_Maintenance.OnItemClickListener clickListener){
        listener = clickListener;
    }

    public myRecycleVA_Maintenance(Context context, ArrayList<Maintenance> maintenanceArrayList) {
        this.context = context;
        this.maintenanceArrayList = maintenanceArrayList;
    }

    @NonNull
    @Override
    public myRecycleVA_Maintenance.MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cm_maintenance_recycle_layout,parent,false);
        return new MyViewHolder1(v,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull myRecycleVA_Maintenance.MyViewHolder1 holder, int position) {
        Maintenance maintenance = maintenanceArrayList.get(position);

        holder.flat_no.setText("Flat Number : "+maintenance.getFlat_no());
        holder.amount.setText("Amount : "+String.valueOf(maintenance.getAmount())+"/-");
        holder.due_date.setText("Due On : "+maintenance.getDue_date());
        holder.discription.setText(maintenance.getDiscription());
        holder.discount.setText("Pay Before Due and Get "+String.valueOf(maintenance.getDiscount())+"% Discount");
    }

    @Override
    public int getItemCount() {
        return maintenanceArrayList.size();
    }
    public static class MyViewHolder1 extends RecyclerView.ViewHolder{

        TextView flat_no, amount, due_date, discription, discount, PayNow;

        public MyViewHolder1(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            flat_no = itemView.findViewById(R.id.Committee_MaintenanceR_FlatNo);
            amount = itemView.findViewById(R.id.Committee_MaintenanceR_MainAmount);
            due_date = itemView.findViewById(R.id.Committee_MaintenanceR_DueDate);
            discription = itemView.findViewById(R.id.Committee_MaintenanceR_Discription);
            discount = itemView.findViewById(R.id.Committee_MaintenanceR_DiscountText);
            PayNow = itemView.findViewById(R.id.Committee_MaintenanceR_PayText);

            PayNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });

        }
    }
}
