package com.example.tanvidadu.learnit;

import android.util.Log;

import static java.lang.Math.abs;

/**
 * Created by Dell on 12/13/2017.
 */

public class Date {

    private boolean [] dates = new boolean[366];
    private boolean leapyear = false;
    private int year ;
    private int [] daysinMonth = { 0, 31 , 28 , 31,30, 31 , 30, 31,31,30,31,30,31};

    Date(){
        for( int i = 0; i <= 365 ; i++){
            dates[i] = false;
        }
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setDates(int  sdate , int smonth , int syear , int edate , int emonth , int eyear) {
        int days = CalculateDays(sdate,smonth,syear,edate,emonth,eyear);
        int sindex = CalculateDays(1 , 1,syear , sdate,smonth,syear);


            for( int i = 0 ; i < days ; i++ ) {
                dates[(sindex + i)%365] = true;
            }
            if( syear == eyear && syear %4 == 0){
                if( (smonth <= 2 && emonth > 2) ||( smonth <= 2 && emonth == 2 && edate == 29) ){
                    leapyear = true;
                    ///Log.i("date" , "no of days " + days +"  " +  leapyear + " starting index " + sindex);
                }
            } else {
                if( syear%4 == 0 && smonth <= 2){
                    leapyear = true;
                } else if (eyear %4 == 0 && ((emonth == 2 && edate == 29) || emonth > 2) ){
                    leapyear = true;
                }
            }
        Log.i("date" , "no of days " + days +"  " +  leapyear + " starting index " + sindex);
    }

    private int CalculateDays(int sdate, int smonth, int syear, int edate, int emonth, int eyear) {
        int days = 0;
        if( syear == eyear){
            if(smonth == emonth){
                days += emonth-smonth + 1;
            } else {
                for (int i = smonth + 1 ; i < emonth; i++){
                    days += daysinMonth[i];
                }
                days += daysinMonth[smonth] - sdate + 1;
                days += edate;
            }
        } else{
            for( int i = smonth+1 ; i <= 12 ; i++){
                days += daysinMonth[i];
            }
            days += daysinMonth[smonth] - sdate+1;
            for (int i = 1 ; i < emonth ; i++){
                days += daysinMonth[i];
            }
            days += edate;
        }
        return days;
    }
}
