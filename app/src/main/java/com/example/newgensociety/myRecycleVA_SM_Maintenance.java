package com.example.newgensociety;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class myRecycleVA_SM_Maintenance extends RecyclerView.Adapter<myRecycleVA_SM_Maintenance.MyViewHolder1>{
    Context context;
    ArrayList<Maintenance> maintenanceArrayList;


//    public class myRecycleViewAdapter extends ArrayAdapter<Maintenance>  {
//
//        public myRecycleViewAdapter(Context context, ArrayList<Maintenance> maintenanceArrayList2){
//            super(context,R.layout.sm_maintenance_recycle_layout,maintenanceArrayList2);
//        }
//
//        @NonNull
//        @Override
//        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//
//            Maintenance maintenance = getItem(position);
//            if(convertView == null){
//                convertView = LayoutInflater.from(getContext()).inflate(R.layout.sm_maintenance_recycle_layout,parent,false);
//            }
//
//            TextView flat_no = convertView.findViewById(R.id.Society_MaintenanceR_FlatNo);
//            TextView amount = convertView.findViewById(R.id.Society_MaintenanceR_MainAmount);
//            TextView due_date = convertView.findViewById(R.id.Society_MaintenanceR_DueDate);
//            TextView discount = convertView.findViewById(R.id.Society_MaintenanceR_DiscountText);
//
//            flat_no.setText("Flat Number : "+maintenance.getFlat_no());
//            amount.setText("Amount : "+String.valueOf(maintenance.getAmount())+"/-");
//            due_date.setText("Due On : "+maintenance.getDue_date());
//            discount.setText("Pay Before Due and Get "+String.valueOf(maintenance.getDiscount())+"% Discount");
//
//
//            return super.getView(position, convertView, parent);
//        }
//    }
    private myRecycleVA_SM_Maintenance.OnItemClickListener listener;
    static FirebaseFirestore db;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(myRecycleVA_SM_Maintenance.OnItemClickListener clickListener){
        listener = clickListener;
    }


    public myRecycleVA_SM_Maintenance(Context context, ArrayList<Maintenance> maintenanceArrayList) {

        this.context = context;
        this.maintenanceArrayList = maintenanceArrayList;
    }



    @NonNull
    @Override
    public myRecycleVA_SM_Maintenance.MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.sm_maintenance_recycle_layout,parent,false);
        return new myRecycleVA_SM_Maintenance.MyViewHolder1(v,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull myRecycleVA_SM_Maintenance.MyViewHolder1 holder, @SuppressLint("RecyclerView") int position) {
        Maintenance maintenance = maintenanceArrayList.get(position);

        holder.flat_no.setText("Flat Number : "+maintenance.getFlat_no());
        holder.amount.setText("Amount : "+String.valueOf(maintenance.getAmount())+"/-");
        holder.due_date.setText("Due On : "+maintenance.getDue_date());
        holder.discription.setText(maintenance.getDiscription());
        holder.discount.setText("Pay Before Due and Get "+String.valueOf(maintenance.getDiscount())+"% Discount");
        String Code = maintenance.getMaintenanceCode();

        db = FirebaseFirestore.getInstance();
        db.collection("Maintenance").document(Code)
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            boolean Status = (boolean) Objects.requireNonNull(task.getResult().get("isPaid"));
                            if(Status) {
                                Calendar calendar = Calendar.getInstance();
                                String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                                String date;
                                date = currentDate.toString();
                                holder.isPaid.setText("Paid : "+date);
                                holder.PayNow.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed To Get isPaid Status", Toast.LENGTH_SHORT).show();
                    }
                });

        holder.PayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,Maintenance_Payment.class);
                i.putExtra("falt_no",maintenanceArrayList.get(position).getFlat_no());
                i.putExtra("amount",String.valueOf(maintenanceArrayList.get(position).getAmount()));
                i.putExtra("dueDate",maintenanceArrayList.get(position).getDue_date());
                i.putExtra("discount",String.valueOf(maintenanceArrayList.get(position).getDiscount()));
                i.putExtra("Code",String.valueOf(maintenanceArrayList.get(position).getMaintenanceCode()));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return maintenanceArrayList.size();
    }
    public static class MyViewHolder1 extends RecyclerView.ViewHolder{

        TextView flat_no, amount, due_date, discription, discount, isPaid;
        Button PayNow;

        public MyViewHolder1(@NonNull View itemView,OnItemClickListener listener) {
            super(itemView);
            flat_no = itemView.findViewById(R.id.Society_MaintenanceR_FlatNo);
            amount = itemView.findViewById(R.id.Society_MaintenanceR_MainAmount);
            due_date = itemView.findViewById(R.id.Society_MaintenanceR_DueDate);
            discription = itemView.findViewById(R.id.Society_MaintenanceR_Discription);
            discount = itemView.findViewById(R.id.Society_MaintenanceR_DiscountText);
            PayNow = itemView.findViewById(R.id.Society_MaintenanceR_PayText);
            isPaid = itemView.findViewById(R.id.Society_MaintenanceR_DateOfPayment);
//            String FlatNumber = flat_no.getText().toString();
//            String Amount = amount.getText().toString();
//            String DueDate = due_date.getText().toString();
//            Integer Discount = Integer.parseInt(discount.getText().toString());
            PayNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    listener.onItemClick(getAdapterPosition());
                }
            });
        }
    }

}
