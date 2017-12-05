package com.example.tanvidadu.learnit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class RequestSummary extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference requestSummaryRobeToSold , requestSummaryRequestGenerated;
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_summary);

        firebaseDatabase = FirebaseDatabase.getInstance();
        requestSummaryRobeToSold = firebaseDatabase.getReference().child("robeToBeSold");
        requestSummaryRequestGenerated = firebaseDatabase.getReference().child("RequestGenerated");

        LinearLayout requestSummaryLinearLayout = (LinearLayout) findViewById(R.id.requestSummaryLayout);
        requestSummaryLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RequestSummary.this , MainActivity.class);
                startActivity(i);
            }
        });

        long UniqueId = Robes.getUnique_pdt_id();
        ///UniqueId = 65506;

        Query requestSummaryRobes = requestSummaryRobeToSold.orderByChild("uniqueId").equalTo(UniqueId);
        requestSummaryRobes.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Robes RobesrequestSummary = dataSnapshot.getValue( Robes.class);
                DisplayRobesInfo(RobesrequestSummary);
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

        Query requestSummaryRequest = requestSummaryRequestGenerated.orderByChild("uniqueId").equalTo(UniqueId);

        requestSummaryRequest.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                SellRequest generateRequest = dataSnapshot.getValue(SellRequest.class);
                DisplayRequestInfo(generateRequest);
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




    }

    private void DisplayRequestInfo(SellRequest generateRequest) {
        TextView display = (TextView) findViewById(R.id.requestSummaryExpectedPrice);
        display.setText("Expected Price : " + generateRequest.getExpectedPrice());
        display = (TextView) findViewById(R.id.requestSummaryDate);
        display.setText("Date Selected For Transcation : "
                +generateRequest.getDayOfMonth()+"/"
                + generateRequest.getMonth() + "/" +
                 generateRequest.getYear());
        display = (TextView) findViewById(R.id.requestSummaryTime);
        display.setText("Time Selected For Transaction : " +
        generateRequest.getHourOfDay() + ":" +
        generateRequest.getMinute());
        display = (TextView) findViewById(R.id.requestSummaryAddress);
        display.setText("ADDRESS : "+ generateRequest.toStringAddress());

    }

    private void DisplayRobesInfo(Robes robesrequestSummary) {
        TextView display = (TextView) findViewById(R.id.requestSummaryNameOfRobe);
        display.setText(robesrequestSummary.getName_of_product());
        display = (TextView) findViewById(R.id.requestSummaryBrand);
        display.setText(robesrequestSummary.getBrand());
        ImageView imageView = (ImageView) findViewById(R.id.requestSummaryRobeImage);
        Bitmap imageBitmap;
        try {
            imageBitmap = decodeFromFirebaseBase64(robesrequestSummary.getImage_url());
            imageView.setImageBitmap(imageBitmap);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(RequestSummary.this , "Image could'nt be loaded" , Toast.LENGTH_LONG).show();
        }


    }
    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
}
