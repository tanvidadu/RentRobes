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

import java.util.ArrayList;

public class Catalog extends AppCompatActivity  {

    private static final String TAG = Catalog.class.getName() ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        //Extracting dates from Intent


        ArrayList<String> catalog = new ArrayList<String>();
        catalog.add("DRESS");
        catalog.add("TRADITIONAL (FEMALE)");
        catalog.add("TRADITIONAL (MALE)");
        catalog.add("FORMALS (MALE)");
        catalog.add("FORMALS (FEMALE)");
        catalog.add("ACCESSORIES");

        final CatalogAdapter catalogAdapter = new CatalogAdapter(Catalog.this , catalog);
        ListView listView = (ListView) findViewById(R.id.catalog_listView);
        listView.setAdapter(catalogAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                Log.i("item extracted" , "item extracted" + item);
                Intent intent = new Intent(Catalog.this,TypeWiseList.class);
                //based on item add info to intent
                String temp = (String) item;

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



}
