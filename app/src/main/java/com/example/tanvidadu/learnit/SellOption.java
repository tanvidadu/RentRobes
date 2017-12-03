package com.example.tanvidadu.learnit;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Base64;
import android.view.View;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;


public class SellOption extends AppCompatActivity {
    private static final int CAMERA_CLOTH_REQUEST = 1888;
    private static final int CAMERA_BILL_REQUEST = 1889;

    private ImageView imageView;
    private ImageView billView;

    private Robes robeInfoObj = new Robes();

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference robeToBeSoldDatabaseReference;
    ///public long UNIQUE_PDT_ID = robeInfoObj.getUnique_pdt_id();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_option);

        firebaseDatabase = FirebaseDatabase.getInstance();
        robeToBeSoldDatabaseReference = firebaseDatabase.getReference().child("robeToBeSold");

        this.imageView = (ImageView)this.findViewById(R.id.Sell_cloth_image);
        Button photoClothButton = (Button) this.findViewById(R.id.button_sell_upload_image);
        Button photoBillButton = (Button) this.findViewById(R.id.button_sell_upload_bill);
        this.billView = (ImageView) this.findViewById(R.id.Sell_cloth_bill);

        photoClothButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_CLOTH_REQUEST);
            }
        });

        photoBillButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_BILL_REQUEST);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_CLOTH_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            robeInfoObj.setImage_url(encodeBitmap(photo));
        }
        if (requestCode == CAMERA_BILL_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            billView.setImageBitmap(photo);
            robeInfoObj.setBill_url(encodeBitmap(photo));
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();

        newFragment.show(getFragmentManager(), "datePicker");
        }




    public void ProceedFunctionality (View view){

        //Extracting Information and tranferring it to object
            EditText et = (EditText) findViewById(R.id.NameOfCloth);
            robeInfoObj.setName_of_product(et.getText().toString());
            et = (EditText) findViewById(R.id.BrandName);
            robeInfoObj.setBrand(et.getText().toString());
            et = (EditText) findViewById(R.id.CostPrice);
            robeInfoObj.setCost_price(Float.valueOf(et.getText().toString()));
            et = (EditText) findViewById(R.id.Size);
            robeInfoObj.setSize(Integer.valueOf(et.getText().toString()));
            et = (EditText)findViewById(R.id.Color);
            robeInfoObj.setColour(et.getText().toString());
            robeInfoObj.setDay_of_month(DatePickerFragment.getrdayOfMonth());
            robeInfoObj.setMonth(DatePickerFragment.getRmonth());
            robeInfoObj.setYear(DatePickerFragment.getRyear());
            robeInfoObj.setUniqueId();

        ///putting data in firebase
            robeToBeSoldDatabaseReference.push().setValue(robeInfoObj);

        /// calling intent
            Intent i = new Intent(this , GenerateRequest.class);
            //i.putExtra("UNIQUE_PDT_ID" , robeInfoObj.getUnique_pdt_id());
            startActivity(i);


        }

     ///Encode the image in required format
    public String encodeBitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        return imageEncoded;
    }

    }





