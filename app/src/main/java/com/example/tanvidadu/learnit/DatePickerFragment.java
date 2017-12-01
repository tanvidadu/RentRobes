package com.example.tanvidadu.learnit;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;

import static android.content.ContentValues.TAG;

/**
 * Created by Dell on 12/1/2017.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private static int rdayOfMonth;
    private static int rmonth;
    private static int ryear;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        ///Log.e(TAG, "onDateSet: ");
           rdayOfMonth = dayOfMonth;
           rmonth = month;
           ryear = year;
    }

    public  static int getrdayOfMonth(){
        return rdayOfMonth;
    }

    public static int getRmonth() {
        return rmonth;
    }

    public static int getRyear() {
        return ryear;
    }
}
