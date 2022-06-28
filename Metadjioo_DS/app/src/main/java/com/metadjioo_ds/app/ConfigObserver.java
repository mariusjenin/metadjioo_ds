package com.metadjioo_ds.app;

public interface ConfigObserver {
    void onDefaultLanguageModified();
    void onTeaserModified();
    void onProductsModified();
    void onOrderProductsModified();
    void onAdditionnalVideoModified();
    void onLanguagesModified();
    void onDatabaseReload();
}
