package com.example.tanvidadu.learnit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Catalog extends AppCompatActivity  {

    private static final String TAG = Catalog.class.getName() ;
    private  ArrayList<RobesForRent>  dressToBeDisplayed = new ArrayList<RobesForRent>();
    private ArrayList<RobesForRent> TraditionalFemaletobeDisplayed = new ArrayList<RobesForRent>();
    private ArrayList<RobesForRent> TraditionalMaletobeDisplayed = new ArrayList<RobesForRent>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        //Extracting dates from Intent


        final ArrayList<String> catalog = new ArrayList<String>();
        catalog.add("dress");
        catalog.add("TRADITIONAL (FEMALE)");
        catalog.add("TRADITIONAL (MALE)");
        catalog.add("FORMALS (MALE)");
        catalog.add("FORMALS (FEMALE)");
        catalog.add("ACCESSORIES");

        final CatalogAdapter catalogAdapter = new CatalogAdapter(Catalog.this , catalog);
        ListView listView = (ListView) findViewById(R.id.catalog_listView);
        listView.setAdapter(catalogAdapter);

        for( int i = 0 ; i < 6 ; i++){
            fetchData(catalog.get(i));
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<RobesForRent> robeToBeDisplayed = null;
                Object item = parent.getItemAtPosition(position);
                Log.i("item extracted" , "item extracted" + item);
                Intent intent = new Intent(Catalog.this,TypeWiseList.class);
                //based on item add info to intent
                String temp = (String) item;
                if( catalog.get(position) == "TRADITIONAL (FEMALE)")
                {
                    robeToBeDisplayed = TraditionalFemaletobeDisplayed;
                } else if ( catalog.get(position) == "TRADITIONAL (MALE)"){
                    robeToBeDisplayed = TraditionalMaletobeDisplayed;
                }
                else{
                    robeToBeDisplayed = dressToBeDisplayed;
                }

                intent.putExtra("ROBESLIST" , robeToBeDisplayed);
                startActivity(intent);
            }


        });



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
                Intent i = new Intent(Catalog.this , Settings.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void fetchData(final String catalogItem ){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("robeForRent").child(catalogItem);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                RobesForRent temp = dataSnapshot.getValue(RobesForRent.class);
                temp.setUniqueCode(dataSnapshot.getKey());
                if( catalogItem == "TRADITIONAL (FEMALE)"){
                    addTraditionalFemale(temp);
                } else if ( catalogItem == "TRADITIONAL (MALE)"){
                    addTraditionalMale(temp);
                }
                else {
                    addDress(temp);
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
    }

    private void addTraditionalMale(RobesForRent temp) {
        TraditionalMaletobeDisplayed.add(temp);
    }

    private void addDress(RobesForRent temp) {
        dressToBeDisplayed.add(temp);
        //Log.i("dress extracted" , "brand is : "+ robeToBeDisplayed.isEmpty());
    }

    private void addTraditionalFemale(RobesForRent temp) {
        TraditionalFemaletobeDisplayed.add(temp);
        //Log.i("dress extracted" , "brand is : "+ robeToBeDisplayed.isEmpty());
    }

}
