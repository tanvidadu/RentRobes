package com.example.tanvidadu.learnit;

import java.util.Map;

/**
 * Created by Dell on 12/21/2017.
 */

public class BookingDate {

   String start_date;
   String end_date;
   String uniqueId;

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }
}
