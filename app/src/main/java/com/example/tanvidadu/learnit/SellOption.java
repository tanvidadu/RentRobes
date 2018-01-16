package com.example.tanvidadu.learnit;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.xdty.preference.colorpicker.ColorPickerDialog;
import org.xdty.preference.colorpicker.ColorPickerSwatch;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import uk.co.senab.photoview.PhotoViewAttacher;


public class SellOption extends AppCompatActivity {
    private static final int CAMERA_CLOTH_REQUEST = 1888;
    private static final int CAMERA_BILL_REQUEST = 1889;
    private static int RESULT_LOAD_IMG = 1890;
    private static int RESULT_LOAD_BILL = 1891;

    private ImageView imageView;
    private ImageView billView;
    private TextView colorView;

    private Robes robeInfoObj = new Robes();

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference robeToBeSoldDatabaseReference;
    private String imgDecodableString;
    private int mSelectedColor;

    ///public long UNIQUE_PDT_ID = robeInfoObj.getUnique_pdt_id();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_option);
        final Button colorButton = (Button) findViewById(R.id.Color);
        try {
            Bundle data = getIntent().getExtras();
            robeInfoObj.setColour(Integer.toString(data.getInt("COLOR")));

        } catch (Exception e){

        }

        colorView = (TextView) findViewById(R.id.displayColor);
        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] mColors = getResources().getIntArray(R.array.default_rainbow);

                ColorPickerDialog dialog = ColorPickerDialog.newInstance(R.string.color_picker_default_title,
                        mColors,
                        mSelectedColor,
                        5, // Number of columns
                        ColorPickerDialog.SIZE_SMALL,
                        true // True or False to enable or disable the serpentine effect
                        //0, // stroke width
                        //Color.BLACK // stroke color
                );

                dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {

                    @Override
                    public void onColorSelected(int color) {
                         mSelectedColor = color;
                        try {
                            //textView.setTextColor(mSelectedColor);
                            robeInfoObj.setColour(Integer.toString(mSelectedColor));
                            //colorButton.setBackgroundColor(Integer.valueOf(robeInfoObj.getColour()));
                            colorView.setBackgroundColor(Integer.valueOf(robeInfoObj.getColour()));

                        } catch (NullPointerException e){
                            Log.i("Exception", "onColorSelected: "+e);
                        }
                    }

                });

                dialog.show(getFragmentManager(), "color_dialog_test");
            }
        });

        Spinner spinner_name_of_cloth = (Spinner) findViewById(R.id.NameOfCloth);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Name_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_name_of_cloth.setAdapter(adapter);

        spinner_name_of_cloth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                robeInfoObj.setName_of_product(parent.getItemAtPosition(position).toString());
                Log.i("Name" , robeInfoObj.getName_of_product());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        Spinner spinner_size = (Spinner) findViewById(R.id.Size);
        ArrayAdapter<CharSequence> adapter_size = ArrayAdapter.createFromResource(this,
                R.array.Size_array, android.R.layout.simple_spinner_item);
        adapter_size.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_size.setAdapter(adapter_size);

        spinner_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                robeInfoObj.setSize(Integer.valueOf(parent.getItemAtPosition(position).toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final ImageButton imageButton = (ImageButton) findViewById(R.id.LockPurchaseDate);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton.setImageDrawable(getResources().getDrawable(R.drawable.tick_mark_icon_png_6619));
                Button button = (Button) findViewById(R.id.PurchaseDate);
                robeInfoObj.setDay_of_month(DatePickerFragment.getrdayOfMonth());
                robeInfoObj.setMonth(DatePickerFragment.getRmonth());
                robeInfoObj.setYear(DatePickerFragment.getRyear());
                Calendar currentDate = Calendar.getInstance();
                int cdate = currentDate.get(Calendar.DATE);
                int cmonth = currentDate.get(Calendar.MONTH);
                int cyear = currentDate.get(Calendar.YEAR);
                Date d = new Date();
                int no_of_days = d.CalculateDays(1,1, robeInfoObj.getYear() , robeInfoObj.getDay_of_month(),robeInfoObj.getMonth(),robeInfoObj.getYear());
                int no_of_days_passed = d.CalculateDays(1,1,cyear,cdate,cmonth,cyear);

                button.setText(robeInfoObj.getDay_of_month() + "/" + (robeInfoObj.getMonth()+1) +"/" + robeInfoObj.getYear());
                TextView textView = ( TextView) findViewById(R.id.LockDateView);
                textView.setVisibility(View.INVISIBLE);
                if(no_of_days >= no_of_days_passed) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SellOption.this)
                            .setTitle("INVALID DATE")
                            .setMessage("PLEASE SELECT DATE BEFORE THE CURRENT DATE ");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    imageButton.setImageDrawable(getResources().getDrawable(R.drawable.exclamation_mark_red));
                    button.setText("PURCHASE DATE");
                    textView.setVisibility(View.VISIBLE);
                }
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        robeToBeSoldDatabaseReference = firebaseDatabase.getReference().child("robeToBeSold");

        this.imageView = (ImageView)this.findViewById(R.id.Sell_cloth_image);
        this.billView = (ImageView) this.findViewById(R.id.Sell_cloth_bill);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_background));
        billView.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_background));


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoViewAttacher pAttacher;
                pAttacher = new PhotoViewAttacher(imageView);
                pAttacher.update();
            }
        });
        billView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoViewAttacher pAttacher;
                pAttacher = new PhotoViewAttacher(billView);
                pAttacher.update();
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
        try {
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                Bitmap photo = BitmapFactory
                        .decodeFile(imgDecodableString);
                imageView.setImageBitmap(photo);
                robeInfoObj.setImage_url(encodeBitmap(photo));
            } else {
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
        try {
            if (requestCode == RESULT_LOAD_BILL && resultCode == RESULT_OK
                    && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                Bitmap photo = BitmapFactory
                        .decodeFile(imgDecodableString);
                billView.setImageBitmap(photo);
                robeInfoObj.setBill_url(encodeBitmap(photo));
            } else {

            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }


    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();

        newFragment.show(getFragmentManager(), "datePicker");
        }




    public void ProceedFunctionality (View view){

        //Extracting Information and tranferring it to object
            EditText et;
            et = (EditText) findViewById(R.id.BrandName);
            robeInfoObj.setBrand(et.getText().toString());
            et = (EditText) findViewById(R.id.CostPrice);
            robeInfoObj.setCost_price(Float.valueOf(et.getText().toString()));
            robeInfoObj.setUniqueId();

        ///putting data in firebase
            robeToBeSoldDatabaseReference.push().setValue(robeInfoObj);

        /// calling intent
            Intent i = new Intent(this , GenerateRequest.class);
            i.putExtra("UNIQUE_PDT_ID" , robeInfoObj.getUnique_pdt_id());
            startActivity(i);


        }

     ///Encode the image in required format
    public String encodeBitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        return imageEncoded;
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
                Intent i = new Intent(SellOption.this , Settings.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }
    public void loadImagefromCamera( View view){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_CLOTH_REQUEST);
    }

    public void loadBillfromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_BILL);
    }
    public void loadBillfromCamera( View view){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_BILL_REQUEST);
    }



}





