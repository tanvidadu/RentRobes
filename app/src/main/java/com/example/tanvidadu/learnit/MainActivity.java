package com.example.tanvidadu.learnit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView mSellOption = (TextView) findViewById(R.id.SellOption);

        // attach an event Listener to SellOption View
        mSellOption.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //make an Intent to another activity
                Intent i = new Intent(MainActivity.this , SellOption.class);
                startActivity(i);
            }
        });
    }






}
