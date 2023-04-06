package com.example.newgensociety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Maintenance_Payment extends AppCompatActivity {

    TextView FlatNumber, DueDate, Amount, Discount;

    int payableAmount, discount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_payment);
        FlatNumber = findViewById(R.id.Maintenance_Payment_flat_no);
        DueDate = findViewById(R.id.Maintenance_Payment_DueDate);
        Amount = findViewById(R.id.Maintenance_Payment_Amount);
        Discount = findViewById(R.id.Maintenance_Payment_Dis_Amount);

        Intent intent = this.getIntent();
        int a = 0;

        if (intent != null){
            String Flat_Number = intent.getStringExtra("flat_no");
            String Due_Date = intent.getStringExtra("dueDate");
            String AmountA = intent.getStringExtra("amount");
            Integer DiscountP = intent.getIntExtra("discount",a);

            FlatNumber.setText(Flat_Number);
            DueDate.setText(Due_Date);
            Amount.setText(AmountA);
            Discount.setText(DiscountP);

        }

    }
}