package com.github.opticwafare.hunde_gassi_app.dialogfragments;

import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.github.opticwafare.hunde_gassi_app.R;

import java.util.ArrayList;

public class NotificationTypeChooserFragment extends DialogFragment {

    private Button btn_NotificationChooserOk;
    private CheckBox checkBox_NotificationChooserAndroidPush;
    private CheckBox checkBox_NotificationChooserEmail;

    private ArrayList<NotificationTypeChooserDismissed> dismissListeners;

    public NotificationTypeChooserFragment() {
        dismissListeners = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        //getDialog().getWindow().setLayout(1000, 1000);
        View view = inflater.inflate(R.layout.element_notificationchooser, container, false);

        // UI Elemente aus XML holen
        btn_NotificationChooserOk = (Button) view.findViewById(R.id.btn_NotificationChooserOk);
        checkBox_NotificationChooserAndroidPush = (CheckBox) view.findViewById(R.id.checkBox_NotificationChooserAndroidPush);
        checkBox_NotificationChooserEmail = (CheckBox) view.findViewById(R.id.checkBox_NotificationChooserEmail);

        btn_NotificationChooserOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(1000, 1000);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        System.out.println("notification type chooser fragment dismissed");

        // Resultate abspeichern
        NotificationTypeChooserResults results = new NotificationTypeChooserResults(
                checkBox_NotificationChooserEmail.isChecked(), checkBox_NotificationChooserAndroidPush.isChecked());

        for(int i = 0; i < dismissListeners.size(); i++) {
            dismissListeners.get(i).onDismiss(dialog, results);
        }
    }

    public ArrayList<NotificationTypeChooserDismissed> getDismissListeners() {
        return dismissListeners;
    }

    public void addDismissListener(NotificationTypeChooserDismissed listener) {
        dismissListeners.add(listener);
    }

    public void removeDismissListener(NotificationTypeChooserDismissed listener) {
        dismissListeners.remove(listener);
    }
}
