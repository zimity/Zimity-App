package me.zimity.android.activities;

import me.zimity.android.app.R;
import me.zimity.android.util.Common;
import android.content.res.Resources;
import android.os.Bundle;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;

import roboguice.activity.RoboActivity;

public class BasicActivity extends RoboActivity {
    
    private GoogleAnalyticsTracker tracker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Resources res = getResources();

        tracker = GoogleAnalyticsTracker.getInstance();
        tracker.startNewSession(
                res.getString(R.string.GOOGLE_ANALYTICS_API_KEY),
                Common.ANALYTICS_DISPATCH_INTERVAL, this);
    }
    
    @Override
    public void onStop() {
        super.onStop();

        tracker.stopSession();
    }
    
    public void setTrackPageView(String page) {
        tracker.trackPageView(page);
    }
}
