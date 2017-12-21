package com.example.tanvidadu.learnit;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import static java.lang.Math.abs;

/**
 * Created by Dell on 12/13/2017.
 */

public class Date implements Parcelable {
   private int sDate;
   private int sMonth;
   private int sYear;
   private int eDate;
   private int eMonth;
   private int eYear;
    private int [] daysinMonth = { 0, 31 , 28 , 31,30, 31 , 30, 31,31,30,31,30,31};

    public Date() {

    }

    public int geteDate() {
        return eDate;
    }

    public int geteMonth() {
        return eMonth;
    }

    public int geteYear() {
        return eYear;
    }

    public int getsDate() {
        return sDate;
    }

    public int getsMonth() {
        return sMonth;
    }

    public int getsYear() {
        return sYear;
    }

    public void seteDate(int eDate) {
        this.eDate = eDate;
    }

    public void seteMonth(int eMonth) {
        this.eMonth = eMonth;
    }

    public void seteYear(int eYear) {
        this.eYear = eYear;
    }

    public void setsDate(int sDate) {
        this.sDate = sDate;
    }

    public void setsMonth(int sMonth) {
        this.sMonth = sMonth;
    }

    public void setsYear(int sYear) {
        this.sYear = sYear;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(sDate);
        dest.writeInt(sMonth);
        dest.writeInt(sYear);
        dest.writeInt(eDate);
        dest.writeInt(eMonth);
        dest.writeInt(eYear);
    }

    public Date ( Parcel in){
        sDate = in.readInt();
        sMonth = in.readInt();
        sYear = in.readInt();
        eDate = in.readInt();
        eMonth = in.readInt();
        eYear = in.readInt();
    }
    public static final Parcelable.Creator<Date> CREATOR =
            new Parcelable.Creator<Date>(){

                public Date createFromParcel(Parcel in ){
                    return new Date(in);
                }

                @Override
                public Date[] newArray(int size) {
                    return new Date[size];
                }
            };

    public void setDates(int sDate, int sMonth, int sYear, int eDate, int eMonth, int eYear) {
       this.sDate = sDate;
       this.sMonth = sMonth;
       this.sYear = sYear;
       this.eDate = eDate;
       this.eMonth = eMonth;
       this.eYear = eYear;
    }

    public int CalculateDays(int sdate, int smonth, int syear, int edate, int emonth, int eyear) {
        int days = 0;
        if( syear == eyear){
            if(smonth == emonth){
                days += edate-sdate + 1;
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
        boolean leapyear = false;
        if( syear == eyear && syear %4 == 0) {
            if ((smonth <= 2 && emonth > 2) || (smonth <= 2 && emonth == 2 && edate == 29)) {
                leapyear = true;
                ///Log.i("date" , "no of days " + days +"  " +  leapyear + " starting index " + sindex);
            }
        }else {
            if( syear%4 == 0 && smonth <= 2){
                leapyear = true;
            } else if (eyear %4 == 0 && ((emonth == 2 && edate == 29) || emonth > 2) ){
                leapyear = true;
            }
        }
        if(leapyear){
            days++;
        }
        return days;
    }
}
