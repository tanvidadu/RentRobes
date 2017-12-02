package com.example.tanvidadu.learnit;

/**
 * Created by Dell on 12/2/2017.
 */

public class SellRequest {
    private String Email;
    private long uniqueId;
    private Address address = new Address();
    private int dayOfMonth;
    private int month;
    private int year;
    private int hourOfDay;
    private int minute;
    private int expectedPrice;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Address getAddress() {
        return address;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public int getExpectedPrice() {
        return expectedPrice;
    }

    public int getHourOfDay() {
        return hourOfDay;
    }

    public int getMinute() {
        return minute;
    }

    public String getEmail() {
        return Email;
    }

    public long getUniqueId() {
        return uniqueId;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setExpectedPrice(int expectedPrice) {
        this.expectedPrice = expectedPrice;
    }

    public void setHourOfDay(int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setUniqueId(long uniqueId) {
        this.uniqueId = uniqueId;
    }
}
