package com.github.opticwafare.hunde_gassi_app.dialogfragments;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {

    private DateTimePickerFragment dateTimePickerFragment;

    public DateTimePickerFragment getDateTimePickerFragment() {
        return dateTimePickerFragment;
    }

    public void setDateTimePickerFragment(DateTimePickerFragment dateTimePickerFragment) {
        this.dateTimePickerFragment = dateTimePickerFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        System.out.println(getActivity());
        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getListener(), hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
    }

    public TimePickerDialog.OnTimeSetListener getListener() {
        if(dateTimePickerFragment == null) {
            return (TimePickerDialog.OnTimeSetListener) getActivity();
        }
        else {
            return dateTimePickerFragment;
        }
    }
}
