package com.example.newgensociety;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class myRecycleVA_Complains extends RecyclerView.Adapter<myRecycleVA_Complains.MyViewHolder2>{

    @NonNull

    Context context;
    FirebaseFirestore db;
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
    public void onBindViewHolder(@NonNull myRecycleVA_Complains.MyViewHolder2 holder, @SuppressLint("RecyclerView") int position) {

        Complains complains = complainsArrayList.get(position);

        holder.name.setText("~"+complains.getName());
        holder.about.setText("About : "+complains.getAbout());
        holder.description.setText(complains.getDescription());
        holder.date.setText(complains.getDate());

        holder.Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Complains complains1 = complainsArrayList.get(position);
                complainsArrayList.remove(position);
                boolean removed = true;
                String Code = complains1.getAbout();
                UpdateStatus(Code,removed);
            }
        });

    }

    private void UpdateStatus(String Code, boolean removed) {

        db = FirebaseFirestore.getInstance();

        Map<String,Object> complainCode = new HashMap<>();
        complainCode.put("isRemoved",removed);

        db.collection("Complains")
                .whereEqualTo("about",Code)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && !task.getResult().isEmpty()){
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentID = documentSnapshot.getId();
                            db.collection("Complains")
                                    .document(documentID)
                                    .update(complainCode)
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
        return complainsArrayList.size();
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        TextView name, about, description, date, Remove;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Committee_Complain_RecycleView_name);
            about = itemView.findViewById(R.id.Committee_Complain_RecycleView_about);
            date = itemView.findViewById(R.id.Committee_Complain_RecycleView_date);
            description = itemView.findViewById(R.id.Committee_Complain_RecycleView_description);
            Remove = itemView.findViewById(R.id.Committee_Complain_RecycleView_ComplainsRemove);
        }
    }
}
