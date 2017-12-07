package com.example.tanvidadu.learnit;

/**
 * Created by Dell on 12/5/2017.
 */

public class CustomerPresent {
    public static boolean isPresent = false;
    public static String customerEmail;

    public static void setCustomerEmail(String customerEmail) {
        CustomerPresent.customerEmail = customerEmail;
    }

    public static String getCustomerEmail() {
        return customerEmail;
    }
}
