package com.github.opticwafare.hunde_gassi_app.tabs;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.opticwafare.hunde_gassi_app.MainActivity;
import com.github.opticwafare.hunde_gassi_app.R;
import com.github.opticwafare.hunde_gassi_app.listener.LoginButtonListener;

import org.w3c.dom.Text;

public class LoginTab extends SuperTab {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button btnLogin;
    private TextView textViewError;

    public LoginTab() {
        super("Login", R.layout.activity_login);
    }

    @Override
    public void init(MainActivity mainActivity) {

        this.mainActivity = mainActivity;

        editTextUsername = (EditText) mainActivity.findViewById(R.id.editTextLoginUsername);
        editTextPassword = (EditText) mainActivity.findViewById(R.id.editTextLoginPassword);
        btnLogin = (Button) mainActivity.findViewById(R.id.btnLogin);
        textViewError = (TextView) mainActivity.findViewById(R.id.textViewLoginError);

        btnLogin.setOnClickListener(new LoginButtonListener(this));
    }

    public EditText getEditTextUsername() {
        return editTextUsername;
    }

    public EditText getEditTextPassword() {
        return editTextPassword;
    }

    public void showError(String error) {
        textViewError.setText(error);
    }

}
