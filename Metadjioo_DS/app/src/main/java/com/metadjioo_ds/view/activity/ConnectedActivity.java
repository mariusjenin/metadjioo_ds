package com.metadjioo_ds.view.activity;

import com.metadjioo_ds.middleware.ConnectedMiddleware;

public abstract class ConnectedActivity extends MiddlewareActivity {

    public ConnectedActivity(){
        super();
        middleware = new ConnectedMiddleware();
    }
}
