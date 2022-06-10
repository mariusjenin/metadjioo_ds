package com.metadjioo_ds.middleware;

import com.metadjioo_ds.view.activity.MiddlewareActivity;

public interface MDSMiddleware {
    void verify_and_redirect(MiddlewareActivity activity);
    void redirect(MiddlewareActivity activity);
}
