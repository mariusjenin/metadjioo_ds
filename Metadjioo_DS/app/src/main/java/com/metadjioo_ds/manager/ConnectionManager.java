package com.metadjioo_ds.manager;

import android.app.Activity;
import android.content.SharedPreferences;

public class ConnectionManager {
    private final static String TOKEN_PREF = "TOKEN_PREF";
    private final static String NAME_SHARED_PREFERENCE_IDENTIFICATION = "IDENTIFICATION";

    private String tokenConnect;
    private boolean connected;
    private static ConnectionManager INSTANCE;

    private ConnectionManager() {
        tokenConnect = null;
        connected = false;
    }

    public static ConnectionManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConnectionManager();
        }
        return INSTANCE;
    }

    /**
     * Connects the user
     * @param token Token to connect the user
     */
    public void setConnected(String token) {
        tokenConnect = token;
        connected = !tokenConnect.isEmpty();
    }

    /**
     * Getter of connected
     * @return if the user is conencted
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Getter of user
     *
     * @return user
     */
    public String getTokenConnect() {
        return tokenConnect;
    }

    /**
     * Disconnects the user
     */
    public void disconnect() {
        tokenConnect = null;
        connected = false;
    }

    /**
     * Will get the user stored in SharePreferences and connect him
     * @param act current activity
     */
    public void retrieveTokenConnectFromPrefs(Activity act){

        SharedPreferences preferences = act.getSharedPreferences(NAME_SHARED_PREFERENCE_IDENTIFICATION,Activity.MODE_PRIVATE);

        tokenConnect = preferences.getString(ConnectionManager.TOKEN_PREF, null);
    }

    /**
     * Will store the user connected to be able to retreive it later (stored in SharePreferences in private mode)
     * @param act current activity
     */
    public void storeTokenUserInPrefs(Activity act){
        SharedPreferences preferences = act.getSharedPreferences(NAME_SHARED_PREFERENCE_IDENTIFICATION,Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        if(tokenConnect != null){
            editor.putString(ConnectionManager.TOKEN_PREF, tokenConnect);
            editor.apply();
        }
    }

    /**
     * Remove the user stored in SharedPreferences
     * @param act current activity
     */
    public void removeTokenUserFromPrefs(Activity act){
        SharedPreferences preferences = act.getSharedPreferences(NAME_SHARED_PREFERENCE_IDENTIFICATION,Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(ConnectionManager.TOKEN_PREF);
        editor.apply();
    }
}
