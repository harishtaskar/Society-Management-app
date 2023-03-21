package com.example.newgensociety;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AdditionalUserInfo;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class committee_registration_page extends AppCompatActivity {

    EditText Email, Password, ConPassword, SocietyName, Area, Location, PinCode, Country, CMemberName, Contact;
    Button btnRegister;
    FirebaseAuth mAuth;
    ProgressBar progressbar;
    TextView logintext;

    private String selectedState, selectedCity;
    int Cm_id = 1;
    private TextView tvState, tvCity;
    private Spinner stateSpinner, citySpinner;
    private ArrayAdapter<CharSequence> stateAdapter, cityAdapter;

    FirebaseFirestore db;
    DatabaseReference rootDatabaseRef;

    // to check whether the user is already registered or not
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(),CommitteeM_HomePage.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_registration_page);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        CMemberName = findViewById(R.id.Committee_Reg_editT_CMname);
        logintext = findViewById(R.id.RegloginText);
        Email = findViewById(R.id.Committee_Reg_editT_Email);
        Password = findViewById(R.id.Committee_Reg_editT_Pass);
        ConPassword = findViewById(R.id.Committee_Reg_editT_conPass);
        btnRegister = findViewById(R.id.btn_RegCommitteeMember);
        progressbar = findViewById(R.id.Progressbar);
        SocietyName = findViewById(R.id.Committee_Reg_editT_Sname);
        Area = findViewById(R.id.Committee_Reg_editT_Area);
        Location = findViewById(R.id.Committee_Reg_editT_Location);
        Country = findViewById(R.id.Committee_Registration_editT_country);
        PinCode = findViewById(R.id.Committee_Reg_editT_Pincode);
        Contact = findViewById(R.id.Committee_Reg_editT_Contact);
        tvState = findViewById(R.id.Committee_Registration_Textview_state);
        tvCity = findViewById(R.id.Committee_Registration_Textview_city);


        stateSpinner = findViewById(R.id.Committee_Reg_spinner_state);
        citySpinner = findViewById(R.id.Committee_Reg_spinner_city);
        stateAdapter = ArrayAdapter.createFromResource(this,R.array.array_indian_states, R.layout.spinner_layout);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateAdapter);

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                citySpinner = findViewById(R.id.Committee_Reg_spinner_city);

                selectedState = stateSpinner.getSelectedItem().toString();

                int parentID = parent.getId();
                if (parentID == R.id.Committee_Reg_spinner_state){
                    switch (selectedState){
                        case "Select Your State": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_default_districts, R.layout.spinner_layout);
                            break;
                        case "Andhra Pradesh": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_andhra_pradesh_districts, R.layout.spinner_layout);
                            break;
                        case "Arunachal Pradesh": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_arunachal_pradesh_districts, R.layout.spinner_layout);
                            break;
                        case "Assam": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_assam_districts, R.layout.spinner_layout);
                            break;
                        case "Bihar": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_bihar_districts, R.layout.spinner_layout);
                            break;
                        case "Chhattisgarh": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_chhattisgarh_districts, R.layout.spinner_layout);
                            break;
                        case "Goa": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_goa_districts, R.layout.spinner_layout);
                            break;
                        case "Gujarat": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_gujarat_districts, R.layout.spinner_layout);
                            break;
                        case "Haryana": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_haryana_districts, R.layout.spinner_layout);
                            break;
                        case "Himachal Pradesh": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_himachal_pradesh_districts, R.layout.spinner_layout);
                            break;
                        case "Jharkhand": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_jharkhand_districts, R.layout.spinner_layout);
                            break;
                        case "Karnataka": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_karnataka_districts, R.layout.spinner_layout);
                            break;
                        case "Kerala": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_kerala_districts, R.layout.spinner_layout);
                            break;
                        case "Madhya Pradesh": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_madhya_pradesh_districts, R.layout.spinner_layout);
                            break;
                        case "Maharashtra": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_maharashtra_districts, R.layout.spinner_layout);
                            break;
                        case "Manipur": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_manipur_districts, R.layout.spinner_layout);
                            break;
                        case "Meghalaya": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_meghalaya_districts, R.layout.spinner_layout);
                            break;
                        case "Mizoram": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_mizoram_districts, R.layout.spinner_layout);
                            break;
                        case "Nagaland": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_nagaland_districts, R.layout.spinner_layout);
                            break;
                        case "Odisha": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_odisha_districts, R.layout.spinner_layout);
                            break;
                        case "Punjab": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_punjab_districts, R.layout.spinner_layout);
                            break;
                        case "Rajasthan": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_rajasthan_districts, R.layout.spinner_layout);
                            break;
                        case "Sikkim": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_sikkim_districts, R.layout.spinner_layout);
                            break;
                        case "Tamil Nadu": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_tamil_nadu_districts, R.layout.spinner_layout);
                            break;
                        case "Telangana": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_telangana_districts, R.layout.spinner_layout);
                            break;
                        case "Tripura": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_tripura_districts, R.layout.spinner_layout);
                            break;
                        case "Uttar Pradesh": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_uttar_pradesh_districts, R.layout.spinner_layout);
                            break;
                        case "Uttarakhand": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_uttarakhand_districts, R.layout.spinner_layout);
                            break;
                        case "West Bengal": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_west_bengal_districts, R.layout.spinner_layout);
                            break;
                        case "Andaman and Nicobar Islands": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_andaman_nicobar_districts, R.layout.spinner_layout);
                            break;
                        case "Chandigarh": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_chandigarh_districts, R.layout.spinner_layout);
                            break;
                        case "Dadra and Nagar Haveli": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_dadra_nagar_haveli_districts, R.layout.spinner_layout);
                            break;
                        case "Daman and Diu": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_daman_diu_districts, R.layout.spinner_layout);
                            break;
                        case "Delhi": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_delhi_districts, R.layout.spinner_layout);
                            break;
                        case "Jammu and Kashmir": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_jammu_kashmir_districts, R.layout.spinner_layout);
                            break;
                        case "Lakshadweep": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_lakshadweep_districts, R.layout.spinner_layout);
                            break;
                        case "Ladakh": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_ladakh_districts, R.layout.spinner_layout);
                            break;
                        case "Puducherry": cityAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_puducherry_districts, R.layout.spinner_layout);
                            break;
                        default:  break;
                    }
                    cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     // Specify the layout to use when the list of choices appears
                    citySpinner.setAdapter(cityAdapter);        //Populate the list of Districts in respect of the State selected

                    //To obtain the selected District from the spinner
                    citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedCity = citySpinner.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressbar.setVisibility(view.VISIBLE);
                String email, password, conpassword;
                email = String.valueOf(Email.getText());
                password = String.valueOf(Password.getText());
                conpassword = String.valueOf(ConPassword.getText());

                Intent intent2 = new Intent(getApplicationContext(),CommitteeM_HomePage.class);
                intent2.putExtra("CM_email",email);

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(committee_registration_page.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(committee_registration_page.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!conpassword.equals(password)) {
                    Toast.makeText(committee_registration_page.this, "Password Doesn't Matched", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (CMemberName.getText().toString().length()==0){
                    CMemberName.setError("Member name can't be Blank");
                    Toast.makeText(committee_registration_page.this, "Member name can't be Blank", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (SocietyName.getText().toString().length()==0){
                    SocietyName.setError("Society name can't be Blank");
                    Toast.makeText(committee_registration_page.this, "Society name can't be Blank", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Area.getText().toString().length()==0){
                    Area.setError("Area name can't be Blank");
                    Toast.makeText(committee_registration_page.this, "Area name can't be Blank", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Location.getText().toString().length()==0){
                    Location.setError("Location name can't be Blank");
                    Toast.makeText(committee_registration_page.this, "Location name can't be Blank", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(PinCode.getText().toString().length()<6 || PinCode.getText().toString().length()>6){
                    PinCode.setError("Pin-Code Must be 6 Digit");
                    Toast.makeText(committee_registration_page.this, "Pin-Code Must be 6 Digit", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Contact.getText().toString().length()<10 || Contact.getText().toString().length()>10){
                    Contact.setError("Contact is not Valid");
                    Toast.makeText(committee_registration_page.this, "Contact is not Valid", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(selectedState.equals("Select Your State")){
                    tvState.setError("Select State");
                    Toast.makeText(committee_registration_page.this, "Select State", Toast.LENGTH_SHORT).show();
                    stateSpinner.requestFocus();
                    return;
                }
                if(selectedCity.equals("Select Your District")){
                    tvCity.setError("Select District");
                    Toast.makeText(committee_registration_page.this, "Contact is not Valid", Toast.LENGTH_SHORT).show();
                    citySpinner.requestFocus();
                    return;
                }
                String area = Area.getText().toString();
                String location = Location.getText().toString();
                String state = selectedState;
                String city = selectedCity;
                String pincode = PinCode.getText().toString();
                String country = Country.getText().toString();
                String Address = area+", "+location+", "+city+"-"+pincode+", "+state+", "+country;
                String societyName = SocietyName.getText().toString();
                String cMemberName = CMemberName.getText().toString();
                String contact = Contact.getText().toString();
                Integer cm_id = new Integer(Cm_id);



                Map<String,Object> cm_member = new HashMap<>();
                cm_member.put("Society_name", societyName);
                cm_member.put("Address", Address);
                cm_member.put("Cm_name", cMemberName);
                cm_member.put("Cm_email",email);
                cm_member.put("Cm_password",password);
                cm_member.put("cm_contact",contact);
                cm_member.put("Cm_id", cm_id);

                db.collection("C_Members")
                                .add(cm_member)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(getApplicationContext(),"Successful", Toast.LENGTH_SHORT).show();
                                                Cm_id++;
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
//                rootDatabaseRef = FirebaseDatabase.getInstance().getReference().child("CM_name");
//                String CM_name = CMemberName.getText().toString();
//                rootDatabaseRef.setValue(CM_name);

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressbar.setVisibility(view.GONE);
                                if (task.isSuccessful()) {

                                    Toast.makeText(committee_registration_page.this, "Account Created",
                                            Toast.LENGTH_SHORT).show();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(committee_registration_page.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });

        logintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(committee_registration_page.this,Committee_LoginPage.class);
                startActivity(intent);
                finish();
            }
        });
    }


}