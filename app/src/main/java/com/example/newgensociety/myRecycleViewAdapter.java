package com.example.newgensociety;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class myRecycleViewAdapter extends RecyclerView.Adapter<myRecycleViewAdapter.MyViewHolder>{

    Context context;
    ArrayList<Notice> noticesArrayList;
    private OnItemClickListener listener;
    static FirebaseFirestore db;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener){
        listener = clickListener;
    }

    public myRecycleViewAdapter(Context context, ArrayList<Notice> noticesArrayList) {
        this.context = context;
        this.noticesArrayList = noticesArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cm_notice_recycleview,parent,false);
        return new MyViewHolder(v,listener);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Notice notice = noticesArrayList.get(position);

        holder.Subject.setText(notice.getSubject());
        holder.Notice.setText(notice.getNotice());
        holder.CM_name.setText(notice.getCm_name());
        holder.Date.setText(notice.getDate());
        holder.code.setText("Notice Code : "+notice.getNotice_code());
        holder.removed = notice.isRemoved();
    }



    @Override
    public int getItemCount() {
        return noticesArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Subject, Notice, CM_name, Date, code;
        ImageView delete;

        boolean removed = true;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            Subject = itemView.findViewById(R.id.Committee_Notice_RecycleView_Subject);
            Notice = itemView.findViewById(R.id.Committee_Notice_RecycleView_Notice);
            CM_name = itemView.findViewById(R.id.Committee_Notice_RecycleView_CM_name);
            Date = itemView.findViewById(R.id.Committee_Notice_RecycleView_date);
            code = itemView.findViewById(R.id.Committee_Notice_RecycleView_code);
            delete = itemView.findViewById(R.id.btn_delete);
            String Code = code.toString();

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());

                }
            });
        }


    }
}
