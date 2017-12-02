package com.example.tanvidadu.learnit;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GenerateRequest extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_request);

        Button AddressRequest = (Button) findViewById(R.id.requestAddress);
        AddressRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GenerateRequest.this , generate_request_address.class);
                startActivity(i);
            }
        });
    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();

        newFragment.show(getFragmentManager(), "datePicker");
    }
    public void showTimePickerDialog(View v) {
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }


}
