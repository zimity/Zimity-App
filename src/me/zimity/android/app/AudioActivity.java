package me.zimity.android.app;

import me.zimity.android.util.Common;
import me.zimity.android.util.GPSHandler;
import greendroid.app.GDMapActivity;
import greendroid.widget.ActionBarItem.Type;
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

public class AudioActivity extends GDMapActivity {
    
	private GoogleAnalyticsTracker tracker;
    private GPSHandler gps;
    private Resources res;
    
    TextView captionText;
    ImageButton save_button;    
    ImageButton speech_button;
    MapView map_view;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.audio_activity);
		
		captionText = (TextView)findViewById(R.id.captionText);
		save_button = (ImageButton)findViewById(R.id.save_button);
		speech_button = (ImageButton)findViewById(R.id.speech_button);
		map_view = (MapView)findViewById(R.id.map_view);
		
		setTitle("Record an Audio Imprint");
		
		res = getResources();
		    	
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