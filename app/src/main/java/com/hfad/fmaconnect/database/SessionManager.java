package com.hfad.fmaconnect.database;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences usersSession;
    SharedPreferences.Editor editor;
    Context context;

    //Session names
    public static final String SESSION_USERSESSION ="userLoginSession";
    public static final String SESSION_REMEMBERME ="rememberMe";

    //User session variables
    private static final String IS_LOGIN = "IsLogedIn";
    public static final String KEY_FULLNAME = "fullName";
    public static final String KEY_USERNAME = "userName";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    //Remember me variables
    private static final String IS_REMEMBERME = "IsRememberMe";
    public static final String KEY_SESSION_EMAIL = "email";
    public static final String KEY_SESSION_PASSWORD = "password";

    //Constructor
    public SessionManager(Context _context, String sessionName) {
        context = _context;
        usersSession = context.getSharedPreferences(sessionName, Context.MODE_PRIVATE);
        editor = usersSession.edit();
    }

    public void createLoginSession(String fullName, String userName, String email, String password){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_FULLNAME, fullName);
        editor.putString(KEY_USERNAME, userName);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);

        editor.commit();

    }

    public HashMap<String, String> getUsersDetailFromSession() {
        HashMap<String, String> userData = new HashMap<>();

        userData.put(KEY_FULLNAME, usersSession.getString(KEY_FULLNAME, null));
        userData.put(KEY_USERNAME, usersSession.getString(KEY_USERNAME, null));
        userData.put(KEY_EMAIL, usersSession.getString(KEY_EMAIL, null));
        userData.put(KEY_PASSWORD, usersSession.getString(KEY_PASSWORD, null));
        return userData;

    }

    public boolean checkLogin() {
        if (usersSession.getBoolean(IS_LOGIN, false)){
            return true;
        } else {
            return false;
        }
    }

    public void logoutUserFromSession() {
        editor.clear();
        editor.commit();
    }

    public void createRememberMeSession(String email, String password){
        editor.putBoolean(IS_REMEMBERME, true);
        editor.putString(KEY_SESSION_EMAIL, email);
        editor.putString(KEY_SESSION_PASSWORD, password);

        editor.commit();
    }

    public void deleteRememberMeSession(String email, String password){
        editor.putBoolean(IS_REMEMBERME, false);
        editor.putString(KEY_SESSION_EMAIL, " ");
        editor.putString(KEY_SESSION_PASSWORD, " ");

        editor.commit();
    }

    public HashMap<String, String> getRememberMeDetailFromSession() {
        HashMap<String, String> userData = new HashMap<>();

        userData.put(KEY_SESSION_EMAIL, usersSession.getString(KEY_SESSION_EMAIL, null));
        userData.put(KEY_SESSION_PASSWORD, usersSession.getString(KEY_SESSION_PASSWORD, null));
        return userData;

    }

    public boolean checkRememberMe() {
        if (usersSession.getBoolean(IS_REMEMBERME, false)){
            return true;
        } else {
            return false;
        }
    }
}
