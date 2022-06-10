package com.metadjioo_ds.middleware;

import com.metadjioo_ds.view.activity.MiddlewareActivity;
import com.metadjioo_ds.manager.ConnectionManager;

/**
 * Middleware redirects app on Home if the user isn't connected and should be
 */
public class ConnectedMiddleware implements MDSMiddleware {
    public void verify_and_redirect(MiddlewareActivity activity){
        ConnectionManager cm = ConnectionManager.getInstance();
        boolean isConnected = cm.isConnected();
        if(!isConnected){
            redirect(activity);
        }
    }
    public void redirect(MiddlewareActivity activity){
//        TODO activity.finish();
//        Intent intent = new Intent(activity, IdentificationActivity.class);
//        activity.setIsRedirected(true);
//        activity.startActivity(intent);
    }
}
