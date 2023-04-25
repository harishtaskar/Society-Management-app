package com.example.newgensociety;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

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
        holder.discount.setText(String.valueOf(maintenance.getDiscount())+"% Discount");

        String Code = maintenance.getMaintenanceCode();

        db = FirebaseFirestore.getInstance();
        db.collection("Maintenance")
                .document(Code)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            boolean Status = (boolean) Objects.requireNonNull(task.getResult().get("isPaid"));
                            if(Status) {
                                Calendar calendar = Calendar.getInstance();
                                String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                                String date;
                                date = currentDate.toString();
                                holder.isPaid.setText("Paid"+date);
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    @Override
    public int getItemCount() {
        return maintenanceArrayList.size();
    }
    public static class MyViewHolder1 extends RecyclerView.ViewHolder{

        TextView flat_no, amount, due_date, discription, discount, PayNow, isPaid;

        public MyViewHolder1(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            flat_no = itemView.findViewById(R.id.Committee_MaintenanceR_FlatNo);
            amount = itemView.findViewById(R.id.Committee_MaintenanceR_MainAmount);
            due_date = itemView.findViewById(R.id.Committee_MaintenanceR_DueDate);
            discription = itemView.findViewById(R.id.Committee_MaintenanceR_Discription);
            discount = itemView.findViewById(R.id.Committee_MaintenanceR_DiscountText);
            PayNow = itemView.findViewById(R.id.Committee_MaintenanceR_PayText);
            isPaid = itemView.findViewById(R.id.Committee_MaintenanceR_DateOfPayment);

            PayNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });

        }
    }
}
