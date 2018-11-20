package com.github.opticwafare.hunde_gassi_app.model;

public class User {

    private int userid;
    private String username;
    private String password;
    private String email;
    private String fcmtoken;

    /**
     * Der derzeit eingeloggte User
     */
    private static User loggedInUser;
    /**
     * Hier wird der FCM Token gespeichert, wenn derzeitig kein User angemeldet ist
     * */
    private static String lonelyFcmToken;

    public User(int userid, String username, String password, String email, String fcmtoken) {
        super();
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fcmtoken = fcmtoken;
    }

    public int getUserid() {
        return userid;
    }
    public void setUserid(int userid) {
        this.userid = userid;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getFcmtoken() {
        return fcmtoken;
    }

    public void setFcmtoken(String fcmtoken) {
        this.fcmtoken = fcmtoken;
    }

    // Static Getter & Setter

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        User.loggedInUser = loggedInUser;
    }

    public static String getLonelyFcmToken() {
        return lonelyFcmToken;
    }

    public static void setLonelyFcmToken(String lonelyFcmToken) {
        User.lonelyFcmToken = lonelyFcmToken;
    }
}
