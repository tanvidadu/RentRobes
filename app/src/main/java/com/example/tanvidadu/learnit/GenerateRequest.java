package com.example.tanvidadu.learnit;

import android.app.DialogFragment;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

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

        final ImageButton pickUpDate = (ImageButton) findViewById(R.id.LockPickUPDate);
        pickUpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickUpDate.setImageDrawable(getResources().getDrawable(R.drawable.tick_mark_icon_png_6619));
                Button button = (Button) findViewById(R.id.pickupDatebutton);
                button.setText(DatePickerFragment.getrdayOfMonth() + "/" + (DatePickerFragment.getRmonth()+1) + "/" + DatePickerFragment.getRyear());
                TextView textView = (TextView) findViewById(R.id.LockDateView4);
                textView.setVisibility(View.INVISIBLE);
                Calendar currentDate = Calendar.getInstance();
                int cdate = currentDate.get(Calendar.DATE);
                int cmonth = currentDate.get(Calendar.MONTH);
                int cyear = currentDate.get(Calendar.YEAR);
                Date d = new Date();
                int no_of_days = d.CalculateDays(1,1,DatePickerFragment.getRyear(), DatePickerFragment.getrdayOfMonth() , DatePickerFragment.getRmonth(),DatePickerFragment.getRyear());
                int no_of_days_passed = d.CalculateDays(1,1,cyear,cdate,cmonth,cyear);
                if(no_of_days < no_of_days_passed) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GenerateRequest.this)
                            .setTitle("INVALID DATE")
                            .setMessage("PLEASE SELECT DATE AFTER THE CURRENT DATE ");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    pickUpDate.setImageDrawable(getResources().getDrawable(R.drawable.exclamation_mark_red));
                    button.setText("SElECT SELLING DATE");
                    textView.setVisibility(View.VISIBLE);
                }
            }
        });
        final ImageButton pickUpTime = (ImageButton) findViewById(R.id.LockPickUPTime);
        pickUpTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickUpTime.setImageDrawable(getResources().getDrawable(R.drawable.tick_mark_icon_png_6619));
                Button button = (Button) findViewById(R.id.pickupTimebutton);
                button.setText(TimePickerFragment.getmHourOfDay()+":" + TimePickerFragment.getMminute());
                TextView textView = (TextView) findViewById(R.id.LockTimeView);
                textView.setVisibility(View.INVISIBLE);
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

        Intent i =  new Intent( GenerateRequest.this,RequestSummary.class );
        startActivity(i);

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
