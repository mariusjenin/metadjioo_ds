package com.metadjioo_ds.middleware;

import com.metadjioo_ds.app.activity.MiddlewareActivity;

public interface MDSMiddleware {
    void verify_and_redirect(MiddlewareActivity activity);
    void redirect(MiddlewareActivity activity);
}
