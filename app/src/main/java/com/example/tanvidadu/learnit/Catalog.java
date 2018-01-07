package com.example.tanvidadu.learnit;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
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
    private ArrayList<RobesForRent> formalMaleToBeDisplayed = new ArrayList<RobesForRent>();
    private ArrayList<RobesForRent> AccessoriesMentobeDisplayed = new ArrayList<RobesForRent>();
    private ArrayList<RobesForRent> AccessoriesWomentobeDisplayed = new ArrayList<RobesForRent>();
    private ArrayList<String> MenOnlyCatalog = new ArrayList<String>();
    private ArrayList<String> WomenOnlyCatalog = new ArrayList<String>();
    private int width = 0;
    private boolean isFemaleSelected = false;
    private boolean isMaleSelected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
         width = size.x;


        Button Hide_1 = (Button) findViewById(R.id.catalog_hide_button_1);
        Button Hide_2 = (Button) findViewById(R.id.catalog_hide_button_2);
        //Extracting dates from Intent


        final ArrayList<String> catalog = new ArrayList<String>();
        catalog.add("dress");
        catalog.add("TRADITIONAL (MALE)");
        catalog.add("TRADITIONAL (FEMALE)");
        catalog.add("FORMALS (MALE)");
        catalog.add("ACCESSORIES (WOMEN)");
        catalog.add("ACCESSORIES (MEN)");
        final ArrayList<String> catalogName = new ArrayList<String>();
        catalogName.add("FORMALS (FEMALE)");
        catalogName.add("TRADITIONAL (MALE)");
        catalogName.add("TRADITIONAL (FEMALE)");
        catalogName.add("FORMALS (MALE)");
        catalogName.add("ACCESSORIES (WOMEN)");
        catalogName.add("ACCESSORIES (MEN)");


        final CatalogAdapter catalogAdapter = new CatalogAdapter(Catalog.this , catalogName);
        GridView listView = (GridView) findViewById(R.id.catalog_listView);
        listView.setColumnWidth(width/2);
        listView.setAdapter(catalogAdapter);

        for( int i = 0 ; i < 6 ; i++){
            fetchData(catalog.get(i));
        }


        Hide_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for( int i = 0 ; i < 6 ; i++){
                    if(i%2 == 0) {
                        WomenOnlyCatalog.add(catalogName.get(i));
                        Log.i("DRESS ADDED", "onClick: " + WomenOnlyCatalog.get(i/2));
                    }
                }
                isFemaleSelected = true;
                isMaleSelected = false;
                final CatalogAdapter catalogAdapterForWomen = new CatalogAdapter(Catalog.this , WomenOnlyCatalog);
                GridView listView = (GridView) findViewById(R.id.catalog_listView);
                listView.setColumnWidth(width);
                listView.setNumColumns(1);
                listView.setAdapter(catalogAdapterForWomen);
            }
        });

        Hide_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < 6; i++) {
                    if (i % 2 == 1) {
                        MenOnlyCatalog.add(catalog.get(i));
                        Log.i("DRESS ADDED", "onClick: " + MenOnlyCatalog.get(i / 2));
                    }
                }
                isMaleSelected = true;
                isFemaleSelected = false;
                final CatalogAdapter catalogAdapterForMen = new CatalogAdapter(Catalog.this, MenOnlyCatalog);
                GridView listView = (GridView) findViewById(R.id.catalog_listView);
                listView.setColumnWidth(width);
                listView.setNumColumns(1);
                listView.setAdapter(catalogAdapterForMen);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<RobesForRent> robeToBeDisplayed = null;
                Object item = parent.getItemAtPosition(position);
                Log.i("item extracted" , "item extracted" + item);
                Intent intent = new Intent(Catalog.this,TypeWiseList.class);
                //based on item add info to intent
                String temp = (String) item;
                if( !isFemaleSelected && !isMaleSelected ) {
                    if (catalog.get(position) == "TRADITIONAL (FEMALE)") {
                        robeToBeDisplayed = TraditionalFemaletobeDisplayed;
                    } else if (catalog.get(position) == "TRADITIONAL (MALE)") {
                        robeToBeDisplayed = TraditionalMaletobeDisplayed;
                    } else if (catalog.get(position) == "FORMALS (MALE)") {
                        robeToBeDisplayed = formalMaleToBeDisplayed;
                    } else if (catalog.get(position) == "ACCESSORIES (MEN)") {
                        robeToBeDisplayed = AccessoriesMentobeDisplayed;
                    } else if (catalog.get(position) == "ACCESSORIES (WOMEN)") {
                        robeToBeDisplayed = AccessoriesWomentobeDisplayed;
                    } else {
                        robeToBeDisplayed = dressToBeDisplayed;
                    }
                } else if ( isFemaleSelected ){
                    if( WomenOnlyCatalog.get(position) == "TRADITIONAL (FEMALE)"){
                        robeToBeDisplayed = TraditionalFemaletobeDisplayed;
                    } else if ( WomenOnlyCatalog.get(position) == "ACCESSORIES (WOMEN)"){
                        robeToBeDisplayed = AccessoriesWomentobeDisplayed;
                    } else {
                        robeToBeDisplayed = dressToBeDisplayed;
                    }

                } else if ( isMaleSelected) {
                    if( MenOnlyCatalog.get(position) == "TRADITIONAL (MALE)"){
                        robeToBeDisplayed = TraditionalMaletobeDisplayed;
                    } else if (MenOnlyCatalog.get(position) == "ACCESSORIES (MEN)"){
                        robeToBeDisplayed = AccessoriesMentobeDisplayed;
                    } else {
                        robeToBeDisplayed = formalMaleToBeDisplayed;
                    }
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
                } else if ( catalogItem == "FORMALS (MALE)"){
                    addFormalMale(temp);
                } else if ( catalogItem == "ACCESSORIES (MEN)"){
                    addAccessoriesMen(temp);
                } else if (catalogItem == "ACCESSORIES (WOMEN)"){
                    addAccessoriesWomen(temp);
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

    private void addAccessoriesWomen(RobesForRent temp) {
        AccessoriesWomentobeDisplayed.add(temp);
    }

    private void addAccessoriesMen(RobesForRent temp) {
        AccessoriesMentobeDisplayed.add(temp);
    }

    private void addFormalMale(RobesForRent temp) {
        formalMaleToBeDisplayed.add(temp);
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
