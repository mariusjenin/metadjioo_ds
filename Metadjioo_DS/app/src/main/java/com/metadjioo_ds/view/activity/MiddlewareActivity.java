package com.metadjioo_ds.view.activity;

import android.os.Bundle;

import com.metadjioo_ds.middleware.MDSMiddleware;

public abstract class MiddlewareActivity extends MDSActivity{
    protected MDSMiddleware middleware;
    protected boolean is_redirected;

    public void verify_and_redirect() {
        middleware.verify_and_redirect(this);
    }

    public void setIsRedirected(boolean is_redirected){
        this.is_redirected = is_redirected;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ConnectionManager cm = ConnectionManager.getInstance();
//        cm.removeTokenUserFromPrefs(this);
        is_redirected = false;
        verify_and_redirect();
    }


    @Override
    protected void onResume() {
        super.onResume();
        is_redirected = false;
        verify_and_redirect();
    }
}
