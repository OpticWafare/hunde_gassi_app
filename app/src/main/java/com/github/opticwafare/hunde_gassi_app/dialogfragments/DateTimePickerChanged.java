package com.github.opticwafare.hunde_gassi_app.dialogfragments;

import com.github.opticwafare.hunde_gassi_app.model.DateTime;

public interface DateTimePickerChanged {

    void dateTimeSet(DateTimePickerFragment view, DateTime chosenDateTime);
}
