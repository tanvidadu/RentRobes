package com.example.tanvidadu.learnit;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class MerchantActivity extends AppCompatActivity implements PaymentResultListener {

    private static final String TAG = MerchantActivity.class.getSimpleName(); ;
    private RobesForRent RobeSelected = null;
    private BookingDate bookingDate = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);

        //Receive object

        try {
            Bundle data = getIntent().getExtras();
            RobeSelected = (RobesForRent) data.getParcelable("RobeFinalized");
            bookingDate = (BookingDate) data.getParcelable("BookingDates");

            Log.i(TAG, "onCreate: " + RobeSelected.getName());
        }catch (Exception e){
            Log.i(TAG , "No Robes received   :" + e);
        }


        TextView textView = (TextView) findViewById(R.id.payment_name);
        textView.setText(RobeSelected.getName());
        textView = (TextView) findViewById(R.id.payment_price);
        textView.setText(Float.toString(RobeSelected.getPrice()));

         /*
         To ensure faster loading of the Checkout form,
          call this method as early as possible in your checkout flow.
         */
        Checkout.preload(getApplicationContext());

        // Payment button created by you in XML layout
        Button button = (Button) findViewById(R.id.btn_pay);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });

    }

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Razorpay Corp");
            options.put("description", "Demoing Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", RobeSelected.getPrice() * 100*CustomerPresent.no_of_days);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "test@razorpay.com");
            preFill.put("contact", "9876543210");

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
            FirebaseDatabase firebaseDatabase  = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference().child("BookedDates").child(RobeSelected.getUniqueCode());
            databaseReference.push().setValue(bookingDate);
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }

    }

    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }

    }
}
