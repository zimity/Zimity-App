package me.zimity.android.app;

import me.zimity.android.util.Common;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

public class NotificationsPreferences extends PreferenceActivity {
	
	private GoogleAnalyticsTracker tracker;
	private Resources res;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		res = getResources();

		tracker = GoogleAnalyticsTracker.getInstance();
		tracker.startNewSession(res.getString(R.string.GOOGLE_ANALYTICS_API_KEY), Common.ANALYTICS_DISPATCH_INTERVAL, this);
		
        addPreferencesFromResource(R.xml.notification_preferences);
	}
	
    @Override
    public void onStart() {
        super.onStart();
        
        tracker.trackPageView("/Settings");
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        
        tracker.stopSession();
    }
}