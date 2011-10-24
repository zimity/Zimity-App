package me.zimity.android.app;

import roboguice.activity.RoboMapActivity;
import roboguice.inject.InjectView;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.inject.Inject;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ImprintAudio extends RoboMapActivity {
    
	private GoogleAnalyticsTracker tracker;
    private GPSHandler gps;
    @Inject private Resources res;
    
    @InjectView(R.id.captionText)
    TextView captionText;
    
    @InjectView(R.id.save_button)
    ImageButton save_button;
    
    @InjectView(R.id.speech_button)
    ImageButton speech_button;
    
    @InjectView(R.id.map_view)
    MapView map_view;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imprint_audio);
		    	
        gps = new GPSHandler(this, map_view, captionText, save_button, speech_button);
        
        tracker = GoogleAnalyticsTracker.getInstance();
		tracker.startNewSession(res.getString(R.string.GOOGLE_ANALYTICS_API_KEY), Common.ANALYTICS_DISPATCH_INTERVAL, this);
	}
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        gps.speechResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();

        tracker.trackPageView("/ImprintAudio");        
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        
        tracker.stopSession();
    }

    /*
     * Mandatory overriden field.
     * 
     * @return boolean Returns whether a route is displayed or not
     */
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    public void onClickSharingButton(View view) {
        gps.sharingSelection();
    }

    public void onClickSpeechButton(View view) {
        gps.speechInput();
    }
    
	public void onClickSaveButton(View view) {
		//TODO:
	}
}