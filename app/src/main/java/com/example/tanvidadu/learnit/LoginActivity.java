package com.example.tanvidadu.learnit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    public static final int RC_SIGN_IN = 1;
    public static final String CustomerEmail = "CURRENT_CUSTOMER";
    private CustomerInfo customerInfo = new CustomerInfo();

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences settings = getSharedPreferences(CustomerEmail, 0);
        CustomerPresent.setCustomerEmail( settings.getString("silentMode", null));
        //setSilent(silent);

        LinearLayout hiddenLayout = (LinearLayout) findViewById(R.id.hiddenLayout);
        if(hiddenLayout != null){
            RemoveSignUp();
        }



        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("user");

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    customerInfo.setName(user.getDisplayName());
                    customerInfo.setEmail(user.getEmail());
                    customerInfo.setProviderId(user.getProviderId());
                    CustomerPresent.setCustomerEmail(user.getEmail());
                   /// onSignedinIntialize(user);
                   ///InflateWelcomePage();

                    boolean isAlreadyPresent = CheckinFirebase(user.getEmail());
                    if(isAlreadyPresent){
                        RemoveWelcomePage();
                        InflateWelcomePage();
                    } else {
                        InflateSignup();
                    }

                    //Toast.makeText(LoginActivity.this, "YOU HAVE SUCCESSFULLY LOGGED IN", Toast.LENGTH_LONG).show();
                } else {
                    onSignedoutCleanUp();
                    // Choose authentication providers
                    List<AuthUI.IdpConfig> providers = Arrays.asList(
                            new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build()
                            //new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build(),
                            //new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                            //new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                            //new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build()
                    );

                    // Create and launch sign-in intent
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(providers)
                                    .build(),
                            RC_SIGN_IN);

                }

            }
        };
    }

    @Override
    protected void onStop(){
        super.onStop();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(CustomerEmail, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("silentMode", CustomerPresent.getCustomerEmail());

        // Commit the edits!
        editor.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(LoginActivity.this, "SIGN IN CANCELLED", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    private void onSignedoutCleanUp() {
    }

    private void onSignedinIntialize(String name) {
        TextView textView = (TextView) findViewById(R.id.WelcomeText);
        textView.setText(" WELCOME TO RENTROBES " + name);
    }

    @Override
    protected void onPause() {
        super.onPause();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    public  void Signout(View view) {
        AuthUI.getInstance().signOut(LoginActivity.this);
    }
    public  void Signout() {
        AuthUI.getInstance().signOut(LoginActivity.this);
    }

    //Inflate SignUP
    public void InflateSignup() {
        LinearLayout hiddenLayout = (LinearLayout) findViewById(R.id.hiddenLayout);
        if (hiddenLayout == null) {
            //Inflate the Hidden Layout Information View
            LinearLayout myLayout = (LinearLayout) findViewById(R.id.signup_layout);
            View hiddenInfo = getLayoutInflater().inflate(R.layout.signup, myLayout, false);
            myLayout.addView(hiddenInfo);

        }
    }

    //Extracting Info and sending it to firebase
    public void SubmitUserInfo( View view){
        //Getting info for CustomerInfo obj
        EditText et = (EditText) findViewById(R.id.customerPhoneno);
        customerInfo.setPhoneNumber(et.getText().toString());
        et = (EditText) findViewById(R.id.customerAge);
        customerInfo.setAge(Integer.valueOf(et.getText().toString()));

        // Loading Information in Firebase
        databaseReference.push().setValue(customerInfo);

        //Removing SignUP layout and Calling Welcome Page
        RemoveSignUp();
        CustomerPresent.isPresent = true;
        InflateWelcomePage();
    }

    // Inflating Welcome page
    public void InflateWelcomePage() {
        LinearLayout hiddenLayout = (LinearLayout) findViewById(R.id.hiddenLayout2);

            //Inflate the Hidden Layout Information View
        if(hiddenLayout == null) {
            LinearLayout myLayout = (LinearLayout) findViewById(R.id.WelcomeLayout);
            View hiddenInfo = getLayoutInflater().inflate(R.layout.welcome_activity, myLayout, false);
            myLayout.addView(hiddenInfo);
        }
            if( customerInfo.getName() != null) {
                onSignedinIntialize(customerInfo.getName());
            }
        Toast.makeText(LoginActivity.this, "SIGNED IN", Toast.LENGTH_LONG).show();
    }

    // to check whether firebase contains that user or not
    public boolean CheckinFirebase( String userEmail){
        Query checkUser = databaseReference.orderByChild("email").equalTo(CustomerPresent.getCustomerEmail());

        checkUser.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                CustomerPresent.isPresent = true;
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
        if(CustomerPresent.isPresent){
            return true;
        } else {
            return false;
        }

    }

    // Remove SignUP
    public void RemoveSignUp(){
        View myView = findViewById(R.id.hiddenLayout);
        ViewGroup parent = (ViewGroup) myView.getParent();
        parent.removeView(myView);

    }
    //Remove Welcome Page
    public void RemoveWelcomePage(){
        View myView = findViewById(R.id.hiddenLayout2);

        try {
            ViewGroup parent = (ViewGroup) myView.getParent();
            parent.removeView(myView);
        } catch (NullPointerException e){

        }
    }

    //Further to main Activity
    public void proceedToMainActivty (View v){
        Intent i = new Intent(LoginActivity.this,MainActivity.class);
        LinearLayout hiddenLayout = (LinearLayout) findViewById(R.id.hiddenLayout);
        if(hiddenLayout != null){
            RemoveSignUp();
        }
        startActivity(i);
    }


}
