package com.github.opticwafare.hunde_gassi_app.dialogfragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.github.opticwafare.hunde_gassi_app.model.DateTime;

import java.util.ArrayList;

public class DateTimePickerFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private AppCompatActivity context;
    /** Was der User aus beiden dialogen ausgewählt hat */
    private DateTime chosenDateTime;

    private ArrayList<DateTimePickerChanged> registeredListeners;

    public DateTimePickerFragment(AppCompatActivity context) {
        registeredListeners = new ArrayList<>();
    }

    public void showDateTimePicker() {

        chosenDateTime = new DateTime();
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setDateTimePickerFragment(this);
        datePickerFragment.show(getContext().getSupportFragmentManager(), "date picker");
    }

    public AppCompatActivity getContext() {
        return context;
    }

    public void setContext(AppCompatActivity context) {
        this.context = context;
    }

    public DateTime getChosenDateTime() {
        return chosenDateTime;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        chosenDateTime.setYear(year);
        chosenDateTime.setMonth(month+1);
        chosenDateTime.setDayOfMonth(dayOfMonth);

        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setDateTimePickerFragment(this);
        timePickerFragment.show(getContext().getSupportFragmentManager(), "time picker");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        chosenDateTime.setHourOfDay(hourOfDay);
        chosenDateTime.setMinute(minute);

        for(DateTimePickerChanged listener : registeredListeners) {
            listener.dateTimeSet(this, chosenDateTime);
        }
    }

    public void addListener(DateTimePickerChanged listener) {
        registeredListeners.add(listener);
    }
}
