package me.zimity.android.app;

import me.zimity.android.util.Common;
import greendroid.app.GDActivity;
import roboguice.activity.RoboActivity;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.google.inject.Inject;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;

// TODO: Remove search, instead each category will have its own search actionbar icon
public class SearchActivity extends GDActivity {
	
	private GoogleAnalyticsTracker tracker;
	private Resources res;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.search_activity);
		
		res = getResources();

		tracker = GoogleAnalyticsTracker.getInstance();
		tracker.startNewSession(res.getString(R.string.GOOGLE_ANALYTICS_API_KEY), Common.ANALYTICS_DISPATCH_INTERVAL, this);
	}
    
    @Override
    public void onStart() {
        super.onStart();
        
        tracker.trackPageView("/Search");
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        
        tracker.stopSession();
    }
}