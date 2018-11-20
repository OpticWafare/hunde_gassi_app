package com.github.opticwafare.hunde_gassi_app.tabs;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.opticwafare.hunde_gassi_app.MainActivity;
import com.github.opticwafare.hunde_gassi_app.R;
import com.github.opticwafare.hunde_gassi_app.listener.RegisterButtonListener;

import org.w3c.dom.Text;

public class RegisterTab extends SuperTab {

    private EditText editTextEmail;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextPassword2;
    private Button btnRegister;
    private TextView textViewError;

    public RegisterTab() {
        super("Registrieren", R.layout.activity_register);
    }

    @Override
    public void init(MainActivity mainActivity) {

        this.mainActivity = mainActivity;

        editTextEmail = (EditText) mainActivity.findViewById(R.id.editTextRegisterEmail);
        editTextUsername = (EditText) mainActivity.findViewById(R.id.editTextRegisterUsername);
        editTextPassword = (EditText) mainActivity.findViewById(R.id.editTextRegisterPassword);
        editTextPassword2 = (EditText) mainActivity.findViewById(R.id.editTextRegisterPassword2);
        btnRegister = (Button) mainActivity.findViewById(R.id.btnRegister);
        textViewError = (TextView) mainActivity.findViewById(R.id.textViewRegisterError);

        btnRegister.setOnClickListener(new RegisterButtonListener(this));

        // TODO listener erstellen
    }

    public EditText getEditTextEmail() {
        return editTextEmail;
    }

    public EditText getEditTextUsername() {
        return editTextUsername;
    }

    public EditText getEditTextPassword() {
        return editTextPassword;
    }

    public EditText getEditTextPassword2() {
        return editTextPassword2;
    }

    public void showError(String error) {
        textViewError.setText(error);
    }
}
