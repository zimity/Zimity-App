package me.zimity.android.app;

import roboguice.activity.RoboMapActivity;
import roboguice.inject.InjectView;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.inject.Inject;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ImprintVideo extends RoboMapActivity {
    
    private GPSHandler gps;
    
	private GoogleAnalyticsTracker tracker;
	@Inject Resources res;
	
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
		setContentView(R.layout.imprint_video);
				
		tracker = GoogleAnalyticsTracker.getInstance();
		tracker.startNewSession(res.getString(R.string.GOOGLE_ANALYTICS_API_KEY), Common.ANALYTICS_DISPATCH_INTERVAL, this);
        
        gps = new GPSHandler(this, map_view, captionText, save_button, speech_button);
        
        Uri i = Uri.parse("/");
        
        recordVideo(i);
	}
	
	// TODO: THIS SHOULD BE A PREFERENCES OPTIONS
    private static int RECORD_VIDEO = 1;
    private static int HIGH_VIDEO_QUALITY = 1;
    private static int MMS_VIDEO_QUALITY = 0;

    private void recordVideo(Uri outputpath) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (outputpath != null)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputpath);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, HIGH_VIDEO_QUALITY);
        startActivityForResult(intent, RECORD_VIDEO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        gps.speechResult(requestCode, resultCode, data);
        
        if (requestCode == RECORD_VIDEO) {
            Uri recordedVideo = data.getData();
            // TODO: Do something with the recorded video
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        tracker.trackPageView("/ImprintVideo");
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
    
	public void onClickSaveButton(View view) {
		// SAVE IMPRINT
	}

    public void onClickSharingButton(View view) {
        gps.sharingSelection();
    }

    public void onClickSpeechButton(View view) {
        gps.speechInput();
    }
}