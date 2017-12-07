package com.example.tanvidadu.learnit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

public class Catalog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

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

                Intent intent = new Intent(Catalog.this,TypeWiseList.class);
                //based on item add info to intent
                startActivity(intent);
            }


        });
    }


}
