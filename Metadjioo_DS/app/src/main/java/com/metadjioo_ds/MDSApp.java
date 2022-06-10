package com.metadjioo_ds;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.metadjioo_ds.view.activity.MDSActivity;


/**
 * Application Metadjioo Display Stand
 */
public class MDSApp extends Application implements LifecycleObserver {

    private static Application MDS_APP;

    @SuppressLint("StaticFieldLeak")
    private static MDSActivity currentAct;

    public static Application getApplication() {
        return MDS_APP;
    }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    public static MDSActivity getCurrentAct() {
        return currentAct;
    }

    public static void setCurrentAct(MDSActivity cA) {
        currentAct = cA;
    }

    public static boolean FOREGROUND;

    @Override
    public void onCreate() {
        super.onCreate();
        FOREGROUND = true;
        MDS_APP = this;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public static void onAppBackgrounded() {
        //App in background
        FOREGROUND = false;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public static void onAppForegrounded() {
        // App in foreground
        FOREGROUND = true;
    }

    public static boolean isForeground() {
        return FOREGROUND;
    }
}
