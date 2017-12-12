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
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class ListItem extends AppCompatActivity {

    private static final String TAG = "ListItemSelected" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);
        Robes RobeSelected = null;
        try {
            Bundle data = getIntent().getExtras();
             RobeSelected = (Robes) data.getParcelable("ListItemSelected");

            Log.i(TAG, "onCreate: " + RobeSelected.getName_of_product());
        }catch (Exception e){
            Log.i(TAG , "No Robes received   :" + e);
        }
        TextView textView = (TextView) findViewById(R.id.list_item_selected_name);
        textView.setText(RobeSelected.getName_of_product());
        textView = (TextView) findViewById(R.id.list_item_selected_brand);
        textView.setText(RobeSelected.getBrand());
        textView = (TextView) findViewById(R.id.list_item_selected_Color);
        textView.setText(RobeSelected.getColour());
        textView = (TextView) findViewById(R.id.list_item_selected_Size);
        textView.setText(Integer.toString(RobeSelected.getSize()));
        textView = (TextView) findViewById(R.id.list_item_selected_Price);
        textView.setText(Float.toString(RobeSelected.getCost_price()));
        ImageView imageView = (ImageView) findViewById(R.id.list_item_selected_imageId);

        try{
            Bitmap bitmap = decodeFromFirebaseBase64(RobeSelected.getImage_url());
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Button RentPayment = (Button) findViewById(R.id.Payment);
        RentPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListItem.this , MerchantActivity.class );
                startActivity(i);
            }
        });

    }

    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
   

}
