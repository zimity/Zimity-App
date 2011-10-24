package me.zimity.android.app;

import roboguice.activity.RoboActivity;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.google.inject.Inject;

import android.content.res.Resources;
import android.os.Bundle;

public class Deals extends RoboActivity {
	
	private GoogleAnalyticsTracker tracker;
	@Inject private Resources res;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deals);

		tracker = GoogleAnalyticsTracker.getInstance();
		tracker.startNewSession(res.getString(R.string.GOOGLE_ANALYTICS_API_KEY), Common.ANALYTICS_DISPATCH_INTERVAL, this);		
	}

    @Override
    public void onStart() {
       super.onStart();
        
       tracker.trackPageView("/Deals");
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	tracker.stopSession();
    }
}