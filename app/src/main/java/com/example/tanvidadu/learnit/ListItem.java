package com.example.tanvidadu.learnit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
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
import com.squareup.picasso.Target;

import java.text.DecimalFormat;

import uk.co.senab.photoview.PhotoViewAttacher;


public class ListItem extends AppCompatActivity {

    private static final String TAG = "ListItemSelected" ;
    private  int colourCode = 0;
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
        DecimalFormat form = new DecimalFormat("0.00");
        textView.setText(form.format(RobeSelected.getPrice()));
         final ImageView imageView = (ImageView) findViewById(R.id.list_item_selected_imageId);
        final Button RentPayment = (Button) findViewById(R.id.Payment);

        Picasso.with(this)
                .load(RobeSelected.getUrl())
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        // loaded bitmap is here (bitmap)
                        imageView.setImageBitmap(bitmap);
                        colourCode =  getDominantColor(bitmap);
                        if( colourCode == 255){
                            colourCode = 0;
                        }
                        RentPayment.setBackgroundColor(colourCode);

                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });




        final RobesForRent finalRobeSelected = RobeSelected;
        RentPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListItem.this , PickDate.class );
                i.putExtra("PaymentOfRobe" , finalRobeSelected);
                startActivity(i);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoViewAttacher pAttacher;
                pAttacher = new PhotoViewAttacher(imageView);
                pAttacher.update();
            }
        });

    }
    public static int getDominantColor(Bitmap bitmap) {
        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, 1, 1, true);
        final int color = newBitmap.getPixel(0, 0);
        newBitmap.recycle();
        return color;
    }
   

}
