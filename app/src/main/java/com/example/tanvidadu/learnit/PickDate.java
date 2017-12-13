package com.example.tanvidadu.learnit;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class PickDate extends AppCompatActivity {
    private int sDate , sMonth , sYear;
    private int eDate , eMonth , eYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_date);



       Button button = (Button)findViewById(R.id.ProceedDates);
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Date date = new Date();
               date.setDates(sDate , sMonth, sYear , eDate,eMonth,eYear);
           }
       });

       Button lockStartDate = (Button) findViewById(R.id.LockStartDate);
       lockStartDate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               sDate = DatePickerFragment.getrdayOfMonth();
               sMonth = DatePickerFragment.getRmonth() +1;
               sYear = DatePickerFragment.getRyear();
               Log.i("date", "onClick: " + sDate + " " + sMonth + " " +sYear );
           }
       });


        Button lockEndDate = (Button) findViewById(R.id.LockEndDate);
        lockEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eDate = DatePickerFragment.getrdayOfMonth();
                eMonth= DatePickerFragment.getRmonth() +1;
                eYear = DatePickerFragment.getRyear();
                Log.i("date", "onClick: " + eDate + " " + eMonth + " " +eYear );
            }
        });

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();

        newFragment.show(getFragmentManager(), "datePicker");


    }
}
