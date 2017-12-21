package com.example.tanvidadu.learnit;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PickDate extends AppCompatActivity {
    private static final String TAG = " Pick Date" ;
    private int sDate , sMonth , sYear;
    private int eDate , eMonth , eYear;
    private RobesForRent RobeSelected;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private int isAvailable = -1;
    private BookingDate rentedDates = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_date);

        try {
            Bundle data = getIntent().getExtras();
            RobeSelected = (RobesForRent) data.getParcelable("PaymentOfRobe");

            Log.i(TAG, "onCreate: " + RobeSelected.getName());
        }catch (Exception e){
            Log.i(TAG , "No Robes received   :" + e);
        }
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("BookedDates").child(RobeSelected.getUniqueCode());
        Log.i(TAG, "onCreate: "+ RobeSelected.getUniqueCode());

        Button check = (Button) findViewById(R.id.checkAvailablity);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                databaseReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        BookingDate date = dataSnapshot.getValue(BookingDate.class);
                        boolean x = date.Compare(sDate,sMonth,sYear,eDate,eMonth,eYear);
                        Log.i(TAG, "onChildAdded: "+isAvailable);
                        if(isAvailable != 0){
                            if( x){
                                isAvailable =1;
                            } else{
                                isAvailable = 0;
                            }
                        }
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

                if( isAvailable == -1){
                    Toast.makeText(PickDate.this , "Processing" , Toast.LENGTH_LONG).show();
                } else if ( isAvailable == 0) {
                    Toast.makeText( PickDate.this,"NOT AVAILABLE" , Toast.LENGTH_LONG).show();
                    Intent i = new Intent(PickDate.this , Catalog.class);
                    startActivity(i);

                } else if( isAvailable == 1){
                    Toast.makeText(PickDate.this , "AVAILABLE !!" , Toast.LENGTH_LONG).show();
                }
            }
        });


       Button button = (Button)findViewById(R.id.ProceedDates);
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               if(isAvailable == 0){
                   Toast.makeText( PickDate.this,"NOT AVAILABLE" , Toast.LENGTH_LONG).show();
                   Intent i = new Intent(PickDate.this , Catalog.class);
                   startActivity(i);
               }
             if(isAvailable == 1){
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
               if( isAvailable == -1){
                   Toast.makeText(PickDate.this , "Processing" , Toast.LENGTH_LONG).show();
               }

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
