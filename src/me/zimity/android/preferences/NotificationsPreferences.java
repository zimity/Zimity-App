package me.zimity.android.preferences;

import me.zimity.android.app.R;
import me.zimity.android.app.R.string;
import me.zimity.android.app.R.xml;
import me.zimity.android.util.Common;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;

import android.content.res.Resources;
import android.os.Bundle;
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