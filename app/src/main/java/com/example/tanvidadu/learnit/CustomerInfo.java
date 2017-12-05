package com.example.tanvidadu.learnit;

/**
 * Created by Dell on 12/5/2017.
 */

public class CustomerInfo {
    private String name;
    private String email;
    private String ProviderId;
    private String phoneNumber;
    private int age;

    // getters and setters method
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

}
