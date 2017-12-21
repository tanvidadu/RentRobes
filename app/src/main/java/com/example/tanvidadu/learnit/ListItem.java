package com.example.tanvidadu.learnit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

public class ListItem extends AppCompatActivity {

    private static final String TAG = "ListItemSelected" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);
        RobesForRent RobeSelected = null;
        try {
            Bundle data = getIntent().getExtras();
             RobeSelected = (RobesForRent) data.getParcelable("ListItemSelected");


            Log.i(TAG, "onCreate: " + RobeSelected.getName());
        }catch (Exception e){
            Log.i(TAG , "No Robes received   :" + e);
        }
        TextView textView = (TextView) findViewById(R.id.list_item_selected_name);
        textView.setText(RobeSelected.getName());
        textView = (TextView) findViewById(R.id.list_item_selected_brand);
        textView.setText(RobeSelected.getBrand());
        textView = (TextView) findViewById(R.id.list_item_selected_Color);
        textView.setText(RobeSelected.getColor());
        textView = (TextView) findViewById(R.id.list_item_selected_Size);
        textView.setText(Integer.toString(RobeSelected.getSize()));
        textView = (TextView) findViewById(R.id.list_item_selected_Price);
        textView.setText(Float.toString(RobeSelected.getPrice()));
        ImageView imageView = (ImageView) findViewById(R.id.list_item_selected_imageId);

        Picasso.with(this).load(RobeSelected.getUrl()).into(imageView);
        Button RentPayment = (Button) findViewById(R.id.Payment);
        final RobesForRent finalRobeSelected = RobeSelected;
        RentPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListItem.this , PickDate.class );
                i.putExtra("PaymentOfRobe" , finalRobeSelected);
                startActivity(i);
            }
        });

    }


   

}
