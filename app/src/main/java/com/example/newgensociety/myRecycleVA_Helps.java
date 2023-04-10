package com.example.newgensociety;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

public class myRecycleVA_Helps extends RecyclerView.Adapter<myRecycleVA_Helps.MyViewHolder2>{
    @NonNull
    Context context;
    ArrayList<Helps> helpsArrayList;

    FirebaseFirestore db;

    public myRecycleVA_Helps(Context context, ArrayList<Helps> helpsArrayList) {
        this.context = context;
        this.helpsArrayList = helpsArrayList;
    }

    @Override
    public myRecycleVA_Helps.MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cm_helps_recycleview,parent,false);
        return new MyViewHolder2(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myRecycleVA_Helps.MyViewHolder2 holder, @SuppressLint("RecyclerView") int position) {

        Helps helps = helpsArrayList.get(position);

        holder.name.setText("~"+helps.getName());
        holder.flat_no.setText("Flat Number : "+helps.getFlat_no());
        holder.help.setText(helps.getHelp());
        holder.date.setText(helps.getDate());
        holder.Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helps helps1 = helpsArrayList.get(position);
                helpsArrayList.remove(position);
                boolean removed = true;
                String Code = helps1.getHelp();
                UpdateStatus(Code,removed);
            }
        });

    }

    private void UpdateStatus(String Code, boolean removed) {

        db = FirebaseFirestore.getInstance();

        Map<String,Object> helpCode = new HashMap<>();
        helpCode.put("isRemoved",removed);

        db.collection("HelpRequests")
                .whereEqualTo("help",Code)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && !task.getResult().isEmpty()){
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentID = documentSnapshot.getId();
                            db.collection("HelpRequests")
                                    .document(documentID)
                                    .update(helpCode)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                        }
                                    });
                        }
                    }
                });

    }

    @Override
    public int getItemCount() {
        return helpsArrayList.size();
    }

    public static class MyViewHolder2 extends RecyclerView.ViewHolder{

        TextView name, flat_no, help, date, Remove;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Committee_Help_RecycleView_name);
            flat_no = itemView.findViewById(R.id.Committee_Help_RecycleView_Flat_No);
            date = itemView.findViewById(R.id.Committee_Help_RecycleView_date);
            help = itemView.findViewById(R.id.Committee_Help_RecycleView_Help);
            Remove = itemView.findViewById(R.id.Committee_Help_RecycleView_ComplainsRemove);
        }


    }
}
