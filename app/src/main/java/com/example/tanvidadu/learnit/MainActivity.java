package com.example.tanvidadu.learnit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView mSellOption = (TextView) findViewById(R.id.SellOption);

        // attach an event Listener to SellOption View
        mSellOption.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //make an Intent to another activity
                Intent i = new Intent(MainActivity.this , SellOption.class);
                startActivity(i);
            }
        });

        TextView mRentOption = (TextView) findViewById(R.id.RentOption);

        //attach an event Listener to RentOption View
        mRentOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , PickDate.class);
                startActivity(i);
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
                Intent i = new Intent(MainActivity.this , Settings.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
