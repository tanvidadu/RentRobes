package com.example.tanvidadu.learnit;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class PickDate extends AppCompatActivity {
    private static final String TAG = " Pick Date" ;
    private int sDate , sMonth , sYear;
    private int eDate , eMonth , eYear;
    private RobesForRent RobeSelected;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private int isAvailable = -1;
    private boolean isProcessed = false;
    private BookingDate rentedDates = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_date);
        isProcessed = false;
        try {
            Bundle data = getIntent().getExtras();
            RobeSelected = (RobesForRent) data.getParcelable("PaymentOfRobe");

            Log.i(TAG, "onCreate: " + RobeSelected.getName());
        }catch (Exception e){
            Log.i(TAG , "No Robes received   :" + e);
        }
        try {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference().child("BookedDates").child(RobeSelected.getUniqueCode());
            Log.i(TAG, "onCreate: "+ RobeSelected.getUniqueCode());
        } catch ( NullPointerException e){

        }

        final ImageButton lockStartDate = (ImageButton) findViewById(R.id.LockStartDate);
        final ImageButton lockEndDate = (ImageButton) findViewById(R.id.LockEndDate);
        final Button button = (Button)findViewById(R.id.ProceedDates);
        final Button buttonStartDate = (Button) findViewById(R.id.StartDate);
        final Button buttonEndDate = (Button) findViewById(R.id.EndDate);
        final View lockDateView1 = (TextView) findViewById(R.id.LockDateView1);
        final View lockDateView2 = (TextView) findViewById(R.id.LockDateView2);
        final Button textView = (Button) findViewById(R.id.LockDateMessage);
        lockStartDate.setVisibility(View.INVISIBLE);
        lockEndDate.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);
        buttonStartDate.setVisibility(View.INVISIBLE);
        buttonEndDate.setVisibility(View.INVISIBLE);
        lockDateView1.setVisibility(View.INVISIBLE);
        lockDateView2.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
        final Button check = (Button) findViewById(R.id.checkAvailablity);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lockStartDate.setVisibility(View.VISIBLE);
                lockEndDate.setVisibility(View.VISIBLE);
                buttonStartDate.setVisibility(View.VISIBLE);
                buttonEndDate.setVisibility(View.VISIBLE);
                lockDateView1.setVisibility(View.VISIBLE);
                lockDateView2.setVisibility(View.VISIBLE);
                check.setEnabled(false);
            }
        });



       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {



               if(isAvailable == 0){
                   Toast.makeText( PickDate.this,"NOT AVAILABLE" , Toast.LENGTH_LONG).show();
                   Intent i = new Intent(PickDate.this , Catalog.class);
                   startActivity(i);
               }

             if(isAvailable == 1 || isProcessed == false){
                   try {
                       rentedDates.setsDate(sDate);
                       rentedDates.setsMonth(sMonth);
                       rentedDates.setsYear(sYear);
                       rentedDates.setEyear(eYear);
                       rentedDates.seteMonth(eMonth);
                       rentedDates.seteDate(eDate);
                   } catch (NullPointerException e){

                   }
                 Intent i = new Intent(PickDate.this, MerchantActivity.class);
                 i.putExtra("RobeFinalized" ,RobeSelected );
                 i.putExtra("BookingDates" ,  rentedDates);
                 startActivity(i);
             }
               if( isAvailable == -1 && isProcessed == true){
                   Toast.makeText(PickDate.this , "Processing" , Toast.LENGTH_LONG).show();
               }

           }
       });


       lockStartDate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               sDate = DatePickerFragment.getrdayOfMonth();
               sMonth = DatePickerFragment.getRmonth() +1;
               sYear = DatePickerFragment.getRyear();
               lockStartDate.setImageDrawable(getResources().getDrawable(R.drawable.tick_mark_icon_png_6619));
               buttonStartDate.setText(sDate+"/" + sMonth+ "/"+sYear);
               lockDateView1.setVisibility(View.INVISIBLE);
               Log.i("date", "onClick: " + sDate + " " + sMonth + " " +sYear );
               Calendar currentDate = Calendar.getInstance();
               int cdate = currentDate.get(Calendar.DATE);
               int cmonth = currentDate.get(Calendar.MONTH);
               int cyear = currentDate.get(Calendar.YEAR);
               Date d = new Date();
               int no_of_days = d.CalculateDays(1,1,DatePickerFragment.getRyear(), DatePickerFragment.getrdayOfMonth() , DatePickerFragment.getRmonth(),DatePickerFragment.getRyear());
               int no_of_days_passed = d.CalculateDays(1,1,cyear,cdate,cmonth,cyear);
               if(no_of_days < no_of_days_passed) {
                   AlertDialog.Builder builder = new AlertDialog.Builder(PickDate.this)
                           .setTitle("INVALID DATE")
                           .setMessage("PLEASE SELECT DATE AFTER THE CURRENT DATE ");
                   AlertDialog dialog = builder.create();
                   dialog.show();
                   lockStartDate.setImageDrawable(getResources().getDrawable(R.drawable.exclamation_mark_red));
                   buttonStartDate.setText("STARTING DATE");
                   lockDateView1.setVisibility(View.VISIBLE);
               }
           }
       });



        lockEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eDate = DatePickerFragment.getrdayOfMonth();
                eMonth= DatePickerFragment.getRmonth() +1;
                eYear = DatePickerFragment.getRyear();
                lockEndDate.setImageDrawable(getResources().getDrawable(R.drawable.tick_mark_icon_png_6619));
                buttonEndDate.setText(eDate+"/"+eMonth+"/"+eYear);
                lockDateView2.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
                Log.i("date", "onClick: " + eDate + " " + eMonth + " " +eYear );
                Calendar currentDate = Calendar.getInstance();
                int cdate = currentDate.get(Calendar.DATE);
                int cmonth = currentDate.get(Calendar.MONTH);
                int cyear = currentDate.get(Calendar.YEAR);
                Date d = new Date();
                int no_of_days = d.CalculateDays(1,1,DatePickerFragment.getRyear(), DatePickerFragment.getrdayOfMonth() , DatePickerFragment.getRmonth(),DatePickerFragment.getRyear());
                int no_of_days_passed = d.CalculateDays(1,1,cyear,cdate,cmonth,cyear);
                if(no_of_days < no_of_days_passed) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PickDate.this)
                            .setTitle("INVALID DATE")
                            .setMessage("PLEASE SELECT DATE AFTER THE CURRENT DATE ");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    lockEndDate.setImageDrawable(getResources().getDrawable(R.drawable.exclamation_mark_red));
                    buttonEndDate.setText("ENDING DATE");
                    lockDateView2.setVisibility(View.VISIBLE);
                }

                no_of_days = d.CalculateDays(1,1,eYear, eDate , eMonth,eYear);
                no_of_days_passed = d.CalculateDays(1,1,sYear,sDate,sMonth,sYear);
                if(no_of_days < no_of_days_passed) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PickDate.this)
                            .setTitle("INVALID DATE")
                            .setMessage("PLEASE SELECT DATE AFTER THE STARTING DATE ");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    lockEndDate.setImageDrawable(getResources().getDrawable(R.drawable.exclamation_mark_red));
                    buttonEndDate.setText("ENDING DATE");
                    lockDateView2.setVisibility(View.VISIBLE);
                }


            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        BookingDate date = dataSnapshot.getValue(BookingDate.class);
                        boolean x = date.Compare(sDate,sMonth,sYear,eDate,eMonth,eYear);
                        Log.i(TAG, "onChildAdded: "+isAvailable);
                        isProcessed = true;
                        if(isAvailable != 0){
                            if( x){
                                isAvailable =1;
                            } else{
                                isAvailable = 0;
                            }
                        }
                        Toast.makeText(PickDate.this , "Processing" , Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                if(isAvailable == 0){
                    Toast.makeText( PickDate.this,"SELECTED DRESS NOT AVAILABLE! PLEASE CLICK PROCEED" , Toast.LENGTH_LONG).show();
                }
                if( isAvailable == -1 && isProcessed == true){
                    Toast.makeText(PickDate.this , "CHECKING AVAILABILITY ! PLEASE WAIT" , Toast.LENGTH_LONG).show();
                }
                if(isAvailable == 1 || isProcessed == false){
                    Toast.makeText(PickDate.this , " DRESS AVAILABE!! PLEASE CLICK PROCEED" , Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
}
