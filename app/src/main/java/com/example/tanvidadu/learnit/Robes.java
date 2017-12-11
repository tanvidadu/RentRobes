package com.example.tanvidadu.learnit;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by Dell on 12/2/2017.
 */

public class Robes implements Parcelable {
    private static long unique_pdt_id;
    private long uniqueId;
    private String name_of_product;
    private String brand;
    private float cost_price;
    private int year;
    private int month;
    private int day_of_month;
    private int size;
    private String colour;
    private String image_url;
    private String bill_url;

    Robes(){
        unique_pdt_id= UniqueRandom.getNextUniqueRandom();
        //Log.i(TAG, "Robes: "+ unique_pdt_id);
        //uniqueId = unique_pdt_id;
    }



    public void setName_of_product(String name_of_product) {
        this.name_of_product = name_of_product;
    }

    public String getName_of_product() {
        return name_of_product;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setCost_price(float cost_price) {
        this.cost_price = cost_price;
    }

    public float getCost_price() {
        return cost_price;
    }

    public void setDay_of_month(int day_of_month) {
        this.day_of_month = day_of_month;
    }

    public int getDay_of_month() {
        return day_of_month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getMonth() {
        return month;
    }

    public void setYear(int year) {
        this.year = year;
        //Log.i(TAG, "setYear: "+this.year);
    }


    public int getYear() {
        return year;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getColour() {
        return colour;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setBill_url(String bill_url) {
        this.bill_url = bill_url;
    }

    public String getBill_url() {
        return bill_url;
    }

    public long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId() {
        this.uniqueId = unique_pdt_id;
    }

    public static long getUnique_pdt_id() {
        return unique_pdt_id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(uniqueId);
      dest.writeString(name_of_product);
      dest.writeString(brand);
      dest.writeFloat(cost_price);
      dest.writeInt(year);
      dest.writeInt(month);
      dest.writeInt(day_of_month);
      dest.writeInt(size);
      dest.writeString(colour);
      dest.writeString(image_url);
      dest.writeString(bill_url);
    }

    public static final Parcelable.Creator<Robes> CREATOR =
            new Parcelable.Creator<Robes>(){

                public Robes createFromParcel(Parcel in ){
                 return new Robes(in);
                }

                @Override
                public Robes[] newArray(int size) {
                    return new Robes[size];
                }
            };

    public Robes(Parcel in){
        uniqueId = in.readLong();
        name_of_product = in.readString();
        brand = in.readString();
        cost_price = in.readFloat();
        year = in.readInt();
        month = in.readInt();
        day_of_month = in.readInt();
        size = in.readInt();
        colour = in.readString();
        image_url = in.readString();
        bill_url = in.readString();
    }
}
