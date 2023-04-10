package com.example.newgensociety;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public class Maintenance_Payment extends AppCompatActivity {

    TextView FlatNumber, DueDate, Amount, Discount, PayableAmount,PayeeName;
    Button Pay;
    ImageView Back;
    FirebaseFirestore db;
    String PayerName;
    FirebaseAuth mAuth;

    int payableAmount, discount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_payment);
        FlatNumber = findViewById(R.id.Maintenance_Payment_flat_no);
        DueDate = findViewById(R.id.Maintenance_Payment_DueDate);
        Amount = findViewById(R.id.Maintenance_Payment_Amount);
        Discount = findViewById(R.id.Maintenance_Payment_Dis_Amount);
        PayableAmount = findViewById(R.id.Maintenance_Payment_Payable_Amount);
        PayeeName = findViewById(R.id.Maintenance_Payment_Name);
        Pay = findViewById(R.id.Maintenance_Payment_btn_pay);
        Back = findViewById(R.id.Back_btn);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        String UserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        db.collection("Society_Members").whereEqualTo("userId",UserId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + "=>" + document.getData());
                                PayerName = Objects.requireNonNull(document.get("Member_Name")).toString();
                            }
                            PayeeName.setText(PayerName);
                        }
                        else{
                            Toast.makeText(Maintenance_Payment.this, "Failed to fetch name", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        Intent intent = this.getIntent();
        if (intent != null){
            String Flat_Number = intent.getStringExtra("falt_no");
            String Due_Date = intent.getStringExtra("dueDate");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = null;
            try{
                date1 = simpleDateFormat.parse(Due_Date);
                Log.i("intent_","==date==-"+date1);
            }catch (ParseException e){
                e.printStackTrace();
            }
            String duedate = simpleDateFormat.format(date1);

            Date currentDate = new Date();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-5"));
            int AmountA = Integer.parseInt(intent.getStringExtra("amount"));
            int DiscountP = Integer.parseInt(intent.getStringExtra("discount"));
            if(currentDate.after(date1)){
                DiscountP = 0;
                DueDate.setTextColor(Color.RED);
            }
                int DiscountA = (AmountA*DiscountP)/100;
                int Payableamount = AmountA-DiscountA;

                    Pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            PaymentGatewayStart();
                        }
                    });
                    thread.start();

                }
            });

            Log.i("intent_","==a=="+AmountA);
            Log.i("intent_","==f=="+Flat_Number);
            FlatNumber.setText("Flat Number: "+Flat_Number);
            DueDate.setText(duedate);
            Amount.setText(String.valueOf(AmountA));
            Discount.setText(String.valueOf(DiscountA));
            PayableAmount.setText(String.valueOf(Payableamount));
        }

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Maintenance_Payment.this,SocietyM_showMaintenance.class);
                startActivity(intent1);
            }
        });

    }

    private void PaymentGatewayStart() {
        String Payee = "HarishTaskar";
        EasyUpiPayment.Builder builder = new EasyUpiPayment.Builder()
                .setPayeeName(Payee)
                .setPayeeVpa("harishtaskar@ybl")
                .setDescription(FlatNumber.getText().toString())
                .setAmount(Amount.getText().toString())
                .setTransactionId("1234")
                .setTransactionRefId("1234");

        EasyUpiPayment easyUpiPayment = builder.build();
        easyUpiPayment.startPayment();

    }
}