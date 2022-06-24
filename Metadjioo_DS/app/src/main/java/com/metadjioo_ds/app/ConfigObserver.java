package com.metadjioo_ds.app;

public interface ConfigObserver {
    public void onDefaultLanguageModified();
    public void onTeaserModified();
    public void onProductsModified();
    public void onOrderProductsModified();
    public void onAdditionnalVideoModified();
    public void onLanguagesModified();
    public void onDatabaseReload();
}
