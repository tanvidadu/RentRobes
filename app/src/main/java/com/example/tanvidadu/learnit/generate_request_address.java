package com.example.tanvidadu.learnit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class generate_request_address extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_request_address);
    }
    public void submitSellAddress(View v){
        EditText et = (EditText) findViewById(R.id.sellStreetAddress1);
        String[] temp = new String[7];
        temp[0] = et.getText().toString();
        et = (EditText) findViewById(R.id.SellAddress2);
        temp[1] = et.getText().toString();
        et = (EditText) findViewById(R.id.SellLandmark);
        temp[2] = et.getText().toString();
        et = (EditText) findViewById(R.id.SellCity);
        temp[3] = et.getText().toString();
        et = (EditText) findViewById(R.id.SellState);
        temp[4] = et.getText().toString();
        et = (EditText) findViewById(R.id.SellCountry);
        temp[5] = et.getText().toString();
        et = (EditText) findViewById(R.id.SellPinode);
        temp[6] = et.getText().toString();
        Intent i = new Intent(generate_request_address.this , GenerateRequest.class);
        i.putExtra("SellAddress" ,new Address(temp[0] , temp[1] , temp[2] , temp[3] , temp[4],temp[5] , temp[6]) );
        startActivity(i);
    }
}
