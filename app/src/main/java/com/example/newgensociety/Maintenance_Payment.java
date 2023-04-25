package com.example.newgensociety;

import static android.content.ContentValues.TAG;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public class Maintenance_Payment extends AppCompatActivity implements PaymentResultListener {

    TextView FlatNumber, DueDate, Amount, Discount, PayableAmount,PayeeName;
    Button Pay;
    ImageView Back;
    FirebaseFirestore db;
    String PayerName, PayerMobile, PayerEmail, MaintenanceCode;
    FirebaseAuth mAuth;

    int Payableamount, discount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_payment);

        Checkout.preload(getApplicationContext());
        Checkout checkout = new Checkout();
        // ...
        checkout.setKeyID("rzp_test_K0HCOn28gs21j7");

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
                                PayerMobile = Objects.requireNonNull(document.get("Mobile")).toString();
                                PayerEmail = Objects.requireNonNull(document.get("Email")).toString();

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
            MaintenanceCode = intent.getStringExtra("Code");

            if(currentDate.after(date1)){
                DiscountP = 0;
                DueDate.setTextColor(Color.RED);
            }
                int DiscountA = (AmountA*DiscountP)/100;
                Payableamount = AmountA-DiscountA;

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
                finish();
            }
        });

        Pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });

    }

    public void startPayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_K0HCOn28gs21j7");
        /**
         * Instantiate Checkout
         */
        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.iconmain);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "New Generation Society");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
//            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", Payableamount * 100 );//pass amount in currency subunits
            options.put("prefill.email", PayerEmail);
            options.put("prefill.contact",PayerMobile);
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);
            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Successful "+s, Toast.LENGTH_SHORT).show();
        boolean Status = true;
        db = FirebaseFirestore.getInstance();
        db.collection("Maintenance")
                .document(MaintenanceCode)
                .update("isPaid",Status)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Maintenance_Payment.this, "Successful", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Maintenance_Payment.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed "+s, Toast.LENGTH_SHORT).show();
    }

}