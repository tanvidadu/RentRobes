package com.example.tanvidadu.learnit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PickDate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_date);

        Date date = new Date();
        date.setDates(31 , 12,2003 , 16,2,2004);

    }
}
