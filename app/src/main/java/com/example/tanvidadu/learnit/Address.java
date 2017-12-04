package com.example.tanvidadu.learnit;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import static java.lang.System.in;

/**
 * Created by Dell on 12/2/2017.
 */

public class Address implements Parcelable{
    private String Street_Address_1;
    private String Street_Address_2;
    private String Landmark;
    private String City;
    private String State;
    private String Country;
    private String Pincode;

    public Address(){
        Log.i("ADDRESS" , "OBJECT INITIALIZED");
    }

    public Address(String s, String s1, String s2, String s3, String s4, String s5, String s6) {
        Street_Address_1 = s;
        Street_Address_1 = s1;
        Landmark = s2;
        City = s3;
        State = s4;
        Country = s5;
        Pincode = s6;
    }


    public String getCity() {
        return City;
    }

    public String getCountry() {
        return Country;
    }

    public String getLandmark() {
        return Landmark;
    }

    public String getStreet_Address_1() {
        return Street_Address_1;
    }

    public String getPincode() {
        return Pincode;
    }

    public String getState() {
        return State;
    }

    public String getStreet_Address_2() {
        return Street_Address_2;
    }

    public void setCity(String city) {
        City = city;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public void setLandmark(String landmark) {
        Landmark = landmark;
    }

    public void setState(String state) {
        State = state;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    public void setStreet_Address_1(String street_Address_1) {
        Street_Address_1 = street_Address_1;
    }

    public void setStreet_Address_2(String street_Address_2) {
        Street_Address_2 = street_Address_2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Street_Address_1);
        dest.writeString(Street_Address_2);
        dest.writeString(Landmark);
        dest.writeString(City);
        dest.writeString(State);
        dest.writeString(Country);
        dest.writeString(Pincode);
    }

    public static final Parcelable.Creator<Address> CREATOR
            = new Parcelable.Creator<Address>() {
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
    public Address(Parcel in){
        Street_Address_1 = in.readString();
        Street_Address_2 = in.readString();
        Landmark = in.readString();
        City = in.readString();
        State = in.readString();
        Country = in.readString();
        Pincode = in.readString();
    }


}
