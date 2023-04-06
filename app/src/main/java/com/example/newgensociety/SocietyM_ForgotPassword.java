package com.example.newgensociety;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SocietyM_ForgotPassword extends AppCompatActivity {

    EditText Email;
    Button ForgotPass;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    String generatedPassword, emailSubject, FirebaseEmail, StringSenderEmail, StringSenderEmailPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_mforgot_password);
        Email = findViewById(R.id.Society_ForgotPass_Email);
        ForgotPass = findViewById(R.id.Sm_ForgotPass);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        ForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int leftLimit = 97; // letter 'a'
                int rightLimit = 122; // letter 'z'
                int targetStringLength = 8;
                Random random = new Random();
                StringBuilder buffer = new StringBuilder(targetStringLength);
                for (int i = 0; i < targetStringLength; i++) {
                    int randomLimitedInt = leftLimit + (int)
                            (random.nextFloat() * (rightLimit - leftLimit + 1));
                    buffer.append((char) randomLimitedInt);
                }
                generatedPassword = buffer.toString();

                mAuth = FirebaseAuth.getInstance();
                String UserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                db = FirebaseFirestore.getInstance();
                db.collection("Society_Members").whereEqualTo("userId",UserId)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                    for ( QueryDocumentSnapshot document : task.getResult()){
                                        Log.d(TAG, document.getId()+ "=>"+ document.getData());
                                        FirebaseEmail = Objects.requireNonNull(document.get("Email")).toString();

                                        if (FirebaseEmail.equals(Email.getText().toString())) {
                                            try {
                                                StringSenderEmail = "harishtaskar002@gmail.com";
                                                StringSenderEmailPassword = "rhuodrekoqwuipuk";
                                                emailSubject = "New Password from new generation Society";

                                                String StringRecieverEmail = Email.getText().toString();

                                                String stringHost = "smtp.gmail.com";

                                                Properties properties = System.getProperties();

                                                properties.put("mail.smtp.host", stringHost);
                                                properties.put("mail.smtp.port", "465");
                                                properties.put("mail.smtp.ssl.enable", "true");
                                                properties.put("mail.smtp.auth", "true");

                                                Session session = Session.getInstance(properties, new Authenticator() {
                                                    @Override
                                                    protected PasswordAuthentication getPasswordAuthentication() {
                                                        return new PasswordAuthentication(StringSenderEmail, StringSenderEmailPassword);
                                                    }
                                                });

                                                MimeMessage mimeMessage = new MimeMessage(session);

                                                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(StringRecieverEmail));

                                                mimeMessage.setSubject("New Password");
                                                mimeMessage.setText("Your New Password For New Generation Society is " + generatedPassword);

                                                Thread thread = new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            Transport.send(mimeMessage);
                                                        } catch (MessagingException e) {
                                                            throw new RuntimeException(e);
                                                        }
                                                    }
                                                });

                                                thread.start();
                                                setChangePass();
                                                Toast.makeText(SocietyM_ForgotPassword.this, "New Password has been send to your registered mail", Toast.LENGTH_SHORT).show();
                                                Email.setText("");


                                            } catch (MessagingException e) {
                                                throw new RuntimeException(e);
                                            }

                                        }
                                        else{
                                            Email.setError("Invalid Email Address");
                                            Toast.makeText(SocietyM_ForgotPassword.this, "Invalid Email Address",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }else{
                                    Toast.makeText(SocietyM_ForgotPassword.this, "Failed",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            }
        });


    }

    private void setChangePass() {
        String UserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();


        db.collection("Society_Members").document(UserId)
                .update("Password",generatedPassword)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                    }
                }).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}