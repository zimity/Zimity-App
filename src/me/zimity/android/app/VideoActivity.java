package me.zimity.android.app;

import me.zimity.android.util.Common;
import me.zimity.android.util.GPSHandler;
import greendroid.app.GDMapActivity;
import greendroid.widget.ActionBarItem.Type;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.google.android.maps.MapView;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class VideoActivity extends GDMapActivity {
    
    private GPSHandler gps;
    
	private GoogleAnalyticsTracker tracker;
	private Resources res;
	
    TextView captionText;
    ImageButton save_button;    
    ImageButton speech_button;
    MapView map_view;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.imprint_video);
		
		addActionBarItem(Type.Share);
		addActionBarItem(Type.Settings);
		
		captionText = (TextView)findViewById(R.id.captionText);
		save_button = (ImageButton)findViewById(R.id.save_button);
		speech_button = (ImageButton)findViewById(R.id.speech_button);
		map_view = (MapView)findViewById(R.id.map_view);
		
		res = getResources();
				
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