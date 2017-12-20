package com.example.tanvidadu.learnit;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dell on 12/20/2017.
 */

public class RobesForRent implements Parcelable {
    private String Name;
    private String Brand;
    private float Price;
    private int size;
    private String color;
    private Date calenderAvailable;
    private String url;

    public void setName(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public Date getCalenderAvailable() {
        return calenderAvailable;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public String getColor() {
        return color;
    }

    public String getUrl() {
        return url;
    }

    public void setCalenderAvailable(Date calenderAvailable) {
        this.calenderAvailable = calenderAvailable;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
       dest.writeString(Name);
       dest.writeString(Brand);
       dest.writeFloat(Price);
       dest.writeInt(size);
       dest.writeString(color);
       dest.writeParcelable(calenderAvailable, flags);
       dest.writeString(url);
    }

    public RobesForRent( Parcel in){
        Name = in.readString();
        Brand = in.readString();
        Price = in.readFloat();
        size = in.readInt();
        color = in.readString();
        calenderAvailable = in.readParcelable(getClass().getClassLoader());
        url = in.readString();
    }
    public static final Parcelable.Creator<RobesForRent> CREATOR =
            new Parcelable.Creator<RobesForRent>(){

                public RobesForRent createFromParcel(Parcel in ){
                    return new RobesForRent(in);
                }

                @Override
                public RobesForRent[] newArray(int size) {
                    return new RobesForRent[size];
                }
            };

}
