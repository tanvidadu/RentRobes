package com.example.tanvidadu.learnit;

import android.app.DialogFragment;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GenerateRequest extends AppCompatActivity {


    private static final String TAG ="adress" ;
    private Address address;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference RequestDatabaseReference;
    ///private long unique_pdt_id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_request);

        firebaseDatabase = FirebaseDatabase.getInstance();
        RequestDatabaseReference = firebaseDatabase.getReference().child("RequestGenerated");

        Button AddressRequest = (Button) findViewById(R.id.requestAddress);
        AddressRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GenerateRequest.this , generate_request_address.class);
                startActivity(i);
            }
        });

        try {
            Bundle data = getIntent().getExtras();
             address = (Address) data.getParcelable("SellAddress");

            Log.i(TAG, "onCreate: " + address.getPincode());
        }catch (Exception e){
            Log.i(TAG , "No ADdress received   :" + e);
        }
    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();

        newFragment.show(getFragmentManager(), "datePicker");
    }
    public void showTimePickerDialog(View v) {
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void GenerateRequest(View v){
        /// Extracting information for request from Xml elements and activities
        SellRequest sellRequest = new SellRequest();
        sellRequest.setAddress(address);
        sellRequest.setDayOfMonth(DatePickerFragment.getrdayOfMonth());
        sellRequest.setMonth(DatePickerFragment.getRmonth());
        sellRequest.setYear(DatePickerFragment.getRyear());
        sellRequest.setHourOfDay(TimePickerFragment.getmHourOfDay());
        sellRequest.setMinute(TimePickerFragment.getMminute());
        EditText et = (EditText)findViewById(R.id.sellExpectedPrice);
        sellRequest.setExpectedPrice(Integer.valueOf(et.getText().toString()));
        sellRequest.setUniqueId(Robes.getUnique_pdt_id());

        ///Loading Request into FirebaseDatabase
        RequestDatabaseReference.push().setValue(sellRequest);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.main_menu_settings:
                Intent i = new Intent(GenerateRequest.this , Settings.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
