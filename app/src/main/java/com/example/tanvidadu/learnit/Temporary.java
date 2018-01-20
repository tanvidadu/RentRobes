package com.example.tanvidadu.learnit;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Temporary extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference robeToBeSoldDatabaseReference;
    private DatabaseReference dateReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temporary);

        firebaseDatabase = FirebaseDatabase.getInstance();
        robeToBeSoldDatabaseReference = firebaseDatabase.getReference().child("robeForRent").child("dress");
        /*robeToBeSoldDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                RobesForRent robesForRent = dataSnapshot.getValue(RobesForRent.class);
                AddBookedValue( dataSnapshot.getKey());
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
        });*/
        //DatabaseReference databaseReference = firebaseDatabase.getReference().child("robeForRent").child("");
        RobesForRent robesForRent = new RobesForRent();
        robesForRent.setName("Dress Suit");
        robesForRent.setBrand("Dhgate");
        robesForRent.setSize(14);
        robesForRent.setPrice(2300);
        robesForRent.setColor("Grey");
        robesForRent.setUrl("https://ae01.alicdn.com/kf/HTB1_GJILpXXXXa9XVXXq6xXFXXXn/Spring-Autumn-Women-Business-Suits-Formal-Office-Suits-Work-Gray-Skirt-and-Blazer-Sets-Slim-Ladies.jpg_640x640.jpg");
        robeToBeSoldDatabaseReference.push().setValue(robesForRent);
    }

   /* private void AddBookedValue(String key) {
        BookingDate bookingDate = new BookingDate();
        bookingDate.setsDate(12);
        bookingDate.setsMonth(1);
        bookingDate.setsYear(2018);
        bookingDate.seteDate(14);
        bookingDate.seteMonth(1);
        bookingDate.setEyear(2018);
        dateReference = firebaseDatabase.getReference().child("BookedDates").child(key);
        dateReference.push().setValue(bookingDate);
    }*/
    }

