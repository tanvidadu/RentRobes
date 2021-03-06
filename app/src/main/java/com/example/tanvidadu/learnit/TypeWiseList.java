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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.*;

public class TypeWiseList extends AppCompatActivity implements TypeWiseListFragment.OnFragmentInteractionListener {

    private static final String TAG = TypeWiseList.class.getName();
    private FirebaseDatabase firebaseDatabase ;
    private DatabaseReference databaseReference;
    ArrayList<RobesForRent> robeToBeDisplayed= new ArrayList<>();

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

            CatalogSelected = data.getString("CatalogSelected");

            robeToBeDisplayed = data.getParcelableArrayList("ROBESLIST");

        }catch (Exception e){
            Log.i(TAG , "No Category received   :" + e);
        }



          // update TextView here!

         TypeWiseListFragment typeWiseListFragment = new TypeWiseListFragment();
         typeWiseListFragment.setRobeToBeDisplayed(robeToBeDisplayed);
         android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
         fragmentManager.beginTransaction().add(R.id.List_container, typeWiseListFragment).commit();


    }





    @Override
    protected void onResume() {
        super.onResume();
    }
}
