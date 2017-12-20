package com.example.tanvidadu.learnit;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class TypeWiseList extends AppCompatActivity implements TypeWiseListFragment.OnFragmentInteractionListener {

    private static final String TAG = TypeWiseList.class.getName();
    private FirebaseDatabase firebaseDatabase ;
    private DatabaseReference databaseReference;
    ArrayList<RobesForRent> robeToBeDisplayed= new ArrayList<>();
    private int sDate , sMonth , sYear;
    private int eDate , eMonth , eYear;
    private String CatalogSelected;

    @Override
    public void onFragmentInteraction(int position) {
        Toast.makeText(TypeWiseList.this,"position " + position  , Toast.LENGTH_SHORT).show();
        Intent i = new Intent(TypeWiseList.this , ListItem.class);
        i.putExtra("ListItemSelected" , robeToBeDisplayed.get(position));
        startActivity(i);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_wise_list);
        
       // Extract Information from Intent
        try {
            Bundle data = getIntent().getExtras();
            sDate = data.getInt("StartDate");
            sMonth = data.getInt("StartMonth");
            sYear = data.getInt("StartYear");
            eDate = data.getInt("EndDate");
            eMonth = data.getInt("EndMonth");
            eYear = data.getInt("EndYear");
            CatalogSelected = data.getString("CatalogSelected");
            Log.i("data" , sDate + " " + sMonth + " " + sYear);
        }catch (Exception e){
            Log.i(TAG , "No Date received   :" + e);
        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("robeForRent").child("dress");

        


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                RobesForRent temp = dataSnapshot.getValue(RobesForRent.class);
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


                                // update TextView here!
        TypeWiseListFragment typeWiseListFragment = new TypeWiseListFragment();
        typeWiseListFragment.setRobeToBeDisplayed(robeToBeDisplayed);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.List_container ,typeWiseListFragment ).commit();




    }

    private void addRobes(RobesForRent temp) {
        robeToBeDisplayed.add(temp);
        Log.i("dress extracted" , "brand is : "+ robeToBeDisplayed.isEmpty());
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
                Intent i = new Intent(TypeWiseList.this , Settings.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
