package com.example.tanvidadu.learnit;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

/**
 * Created by Dell on 12/5/2017.
 */

public class CustomerInfo implements Parcelable {
    private String name;
    private String email;
    private String ProviderId;
    private String phoneNumber;
    private int age;
    private Address address;

    public CustomerInfo() {

    }

    // getters and setters method

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getProviderId() {
        return ProviderId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProviderId(String providerId) {
        this.ProviderId = providerId;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
         dest.writeString(name);
         dest.writeString(email);
         dest.writeString(ProviderId);
         dest.writeString(phoneNumber);
         dest.writeInt(age);
    }
    public static final Parcelable.Creator<CustomerInfo> CREATOR
            = new Parcelable.Creator<CustomerInfo>() {
        public CustomerInfo createFromParcel(Parcel in) {
            return new CustomerInfo(in);
        }

        public CustomerInfo[] newArray(int size) {
            return new CustomerInfo[size];
        }
    };
    public CustomerInfo ( Parcel in ){
        name = in.readString();
        email = in.readString();
        ProviderId = in.readString();
        phoneNumber = in.readString();
        age = in.readInt();
    }
}
