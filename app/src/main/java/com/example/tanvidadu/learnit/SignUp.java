package com.example.tanvidadu.learnit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private static final String TAG = " Customer Info";
    private CustomerInfo customerInfo;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference().child("user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        try {
            Bundle data = getIntent().getExtras();
            customerInfo = data.getParcelable("CustomerInfo");
        }catch (Exception e){
            Log.i(TAG , "No Category received   :" + e);
        }
    }

    public void SubmitUserInfo( View view){
        //Getting info for CustomerInfo obj
        EditText et = (EditText) findViewById(R.id.customerPhoneno);
        customerInfo.setPhoneNumber(et.getText().toString());
        et = (EditText) findViewById(R.id.customerAge);
        customerInfo.setAge(Integer.valueOf(et.getText().toString()));
        et = (EditText) findViewById(R.id.StreetAddress1);
        Address address = new Address();
        address.setStreet_Address_1(et.getText().toString());
        et = (EditText) findViewById(R.id.Address2);
        address.setStreet_Address_2(et.getText().toString());
        et = (EditText) findViewById(R.id.Landmark);
        address.setLandmark(et.getText().toString());
        et = (EditText) findViewById(R.id.City);
        address.setCity(et.getText().toString());
        et = (EditText) findViewById(R.id.State);
        address.setState(et.getText().toString());
        et = (EditText) findViewById(R.id.Country);
        address.setCountry(et.getText().toString());
        et = (EditText) findViewById(R.id.Pinode);
        address.setPincode(et.getText().toString());


        // Loading Information in Firebase
        databaseReference.push().setValue(customerInfo);

        Intent i = new Intent(SignUp.this , MainActivity.class);
        startActivity(i);
    }
}
