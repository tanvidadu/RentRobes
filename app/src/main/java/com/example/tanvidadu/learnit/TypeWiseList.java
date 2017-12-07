package com.example.tanvidadu.learnit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class TypeWiseList extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase ;
    private DatabaseReference databaseReference;
    ArrayList<Robes> robeToBeDisplayed= new ArrayList<Robes>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_wise_list);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("robeToBeSold");



        Query clothToBeDisplayed = databaseReference.orderByChild("name_of_product").equalTo("gloves");
        clothToBeDisplayed.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Robes temp = dataSnapshot.getValue(Robes.class);
                addRobes(temp);

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

        RobesAdapter robesAdapter = new RobesAdapter(TypeWiseList.this , robeToBeDisplayed);
        ListView listView = (ListView) findViewById(R.id.List_View_items);
        listView.setAdapter(robesAdapter);

    }

    private void addRobes(Robes temp) {
        robeToBeDisplayed.add(temp);
        Log.i("dress extracted" , "brand is : "+ robeToBeDisplayed.isEmpty());
    }
}
