package com.example.tanvidadu.learnit;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

/**
 * Created by Dell on 12/21/2017.
 */

public class BookingDate  implements Parcelable{

   private int sDate;
   private int sMonth;
   private int sYear;
   private int eDate;
   private int eMonth;
   private int Eyear;
    private int [] daysinMonth = { 0, 31 , 28 , 31,30, 31 , 30, 31,31,30,31,30,31};
   String uniqueId;

    public BookingDate() {

    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setsYear(int sYear) {
        this.sYear = sYear;
    }

    public void setsMonth(int sMonth) {
        this.sMonth = sMonth;
    }

    public void setsDate(int sDate) {
        this.sDate = sDate;
    }

    public void seteMonth(int eMonth) {
        this.eMonth = eMonth;
    }

    public void seteDate(int eDate) {
        this.eDate = eDate;
    }

    public int getsYear() {
        return sYear;
    }

    public int getsMonth() {
        return sMonth;
    }

    public int getsDate() {
        return sDate;
    }

    public int geteMonth() {
        return eMonth;
    }

    public int geteDate() {
        return eDate;
    }

    public int getEyear() {
        return Eyear;
    }

    public void setEyear(int eyear) {
        Eyear = eyear;
    }

    public boolean Compare ( int csDate , int csMonth , int csYear , int ceDate , int ceMonth , int ceYear){
        if( ceYear < sYear){
            return  true;
        } else if ( csYear > Eyear){
            return true;
        } else if ( csYear == sYear && ceYear == Eyear) {
            if( csYear == ceYear){
               int csIndex = CalculateDays(1,1,csYear,csDate,csMonth,csYear);
               int ceIndex = CalculateDays(1,1,ceYear,ceDate,ceMonth,ceYear);
               int sIndex = CalculateDays(1,1,sYear,sDate,sMonth,sYear);
               int eIndex = CalculateDays(1,1,Eyear,eDate,eMonth,Eyear);
               if( ceIndex < sIndex || csIndex> eIndex){
                   return  true;
               } else {
                   return  false;
               }
            }

        }
        return false;

    }

    private int CalculateDays(int sdate, int smonth, int syear, int edate, int emonth, int eyear) {
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
        return days;
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
        dest.writeInt(Eyear);
        dest.writeIntArray(daysinMonth);
        dest.writeString(uniqueId);
    }
    public static final Parcelable.Creator<BookingDate> CREATOR =
            new Parcelable.Creator<BookingDate>(){

                public BookingDate createFromParcel(Parcel in ){
                    return new BookingDate(in);
                }

                @Override
                public BookingDate[] newArray(int size) {
                    return new BookingDate[size];
                }
            };

    public BookingDate ( Parcel in){
        sDate = in.readInt();
        sMonth = in.readInt();
        sYear = in.readInt();
        eDate = in.readInt();
        eMonth = in.readInt();
        Eyear = in.readInt();
        daysinMonth = in.createIntArray();
        uniqueId = in.readString();
    }
}
