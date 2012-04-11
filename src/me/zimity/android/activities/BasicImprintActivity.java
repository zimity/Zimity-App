package me.zimity.android.activities;

import roboguice.activity.RoboMapActivity;
import roboguice.inject.InjectView;

import me.zimity.android.app.R;
import me.zimity.android.util.Common;
import me.zimity.android.util.GPSHandler;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.google.android.maps.MapView;

public class BasicImprintActivity extends RoboMapActivity {

    private GoogleAnalyticsTracker tracker;

    @InjectView(R.id.captionText)
    protected TextView captionText;
    @InjectView(R.id.save_button)
    protected ImageButton save_button;
    @InjectView(R.id.speech_button)
    protected ImageButton speech_button;
    @InjectView(R.id.map_view)
    protected MapView map_view;

    protected GPSHandler gps;
    private Resources res;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        res = getResources();

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

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
    

    @Override
    public void onResume() {
        super.onResume();

        gps.startLocationUpdates();
    }

    @Override
    public void onPause() {
        super.onPause();

        gps.stopLocationUpdates();
    }
}