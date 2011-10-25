package me.zimity.android.app;

import me.zimity.android.util.Common;
import greendroid.app.GDActivity;
import greendroid.widget.ActionBarItem.Type;
import roboguice.activity.RoboActivity;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.google.inject.Inject;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;

public class NearByActivity extends GDActivity {
	
	private GoogleAnalyticsTracker tracker;
	private Resources res;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.nearby_activity);
		
		addActionBarItem(Type.Talk);
		addActionBarItem(Type.Settings);
		addActionBarItem(Type.Search);
		
		res = getResources();

		tracker = GoogleAnalyticsTracker.getInstance();
		tracker.startNewSession(res.getString(R.string.GOOGLE_ANALYTICS_API_KEY), Common.ANALYTICS_DISPATCH_INTERVAL, this);
	}
    
    @Override
    public void onStart() {
        super.onStart();
        
        tracker.trackPageView("NearBy");
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        
        tracker.stopSession();
    }
}