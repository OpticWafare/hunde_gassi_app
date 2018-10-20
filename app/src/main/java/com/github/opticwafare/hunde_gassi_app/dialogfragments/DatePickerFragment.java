package com.github.opticwafare.hunde_gassi_app.dialogfragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {

    private DateTimePickerFragment dateTimePickerFragment;

    public DateTimePickerFragment getDateTimePickerFragment() {
        return dateTimePickerFragment;
    }

    public void setDateTimePickerFragment(DateTimePickerFragment dateTimePickerFragment) {
        this.dateTimePickerFragment = dateTimePickerFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), getListener(), year, month, dayOfMonth);
    }

    public DatePickerDialog.OnDateSetListener getListener() {
        if(dateTimePickerFragment == null) {
            return (DatePickerDialog.OnDateSetListener) getActivity();
        }
        else {
            return dateTimePickerFragment;
        }
    }
}
