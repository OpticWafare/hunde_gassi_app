package com.github.opticwafare.hunde_gassi_app.servlettasks;

import android.widget.Toast;

import com.github.opticwafare.hunde_gassi_app.model.LoginTransfer;
import com.github.opticwafare.hunde_gassi_app.model.User;
import com.github.opticwafare.hunde_gassi_app.pageradapter.MainPagerAdapter;
import com.github.opticwafare.hunde_gassi_app.tabs.LoginTab;
import com.github.opticwafare.hunde_gassi_app.tabs.RegisterTab;
import com.google.gson.Gson;

public class RegisterTask extends SendToServletTask {

    private RegisterTab registerTab;

    private String email;
    private String username;
    private String password;
    private String password2;
    private String fcmToken;

    public RegisterTask(RegisterTab registerTab) {
        setServletName("Register");
        this.registerTab = registerTab;

        this.email = registerTab.getEditTextEmail().getText().toString();
        this.username = registerTab.getEditTextUsername().getText().toString();
        this.password = registerTab.getEditTextPassword().getText().toString();
        this.password2 = registerTab.getEditTextPassword2().getText().toString();
        this.fcmToken = User.getLonelyFcmToken();

        System.out.println("register username: " + username);

        setUrlParameters("username="+username+"&email="+email+"&password="+password+"&password2="+password2+"&fcmToken="+fcmToken);
    }

    @Override
    protected void onPostExecute(String s) {

        Gson gson = new Gson();
        LoginTransfer loginTransfer = gson.fromJson(s, LoginTransfer.class);

        if(loginTransfer == null) {
            registerTab.showError("Registrieren fehlgeschlagen");
        }
        else if(loginTransfer.getError() != null) {
            registerTab.showError(loginTransfer.getError());
        }
        else {
            Toast.makeText(registerTab.getMainActivity(), "User erfolgreich registriert!", Toast.LENGTH_SHORT).show();
            User.setLoggedInUser(loginTransfer.getUser());

            registerTab.getEditTextEmail().clearFocus();
            registerTab.getEditTextPassword().clearFocus();
            registerTab.getEditTextPassword2().clearFocus();
            registerTab.getEditTextUsername().clearFocus();

            registerTab.getMainActivity().setPagerAdapter(new MainPagerAdapter(registerTab.getMainActivity()));
        }
    }
}
