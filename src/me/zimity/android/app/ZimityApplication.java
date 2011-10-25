package me.zimity.android.app;

import greendroid.app.GDApplication;

import android.content.Intent;
import android.net.Uri;

public class ZimityApplication extends GDApplication {
	
    @Override
    public Class<?> getHomeActivityClass() {
        return ZimityActivity.class;
    }
    
    @Override
    public Intent getMainApplicationIntent() {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.app_url)));
    }

}
