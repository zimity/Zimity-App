package me.zimity.android.app;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;

import android.app.Activity;
import android.content.res.Resources;

@EActivity(R.layout.deals)
public class Deals extends Activity {
	
	private GoogleAnalyticsTracker tracker;
	private Resources res;
	
	@AfterViews
	public void init() {
		res = this.getResources();
		
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