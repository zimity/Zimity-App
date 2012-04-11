package me.zimity.android.activities;

import me.zimity.android.app.R;
import me.zimity.android.app.R.id;
import me.zimity.android.app.R.layout;
import me.zimity.android.app.R.string;
import me.zimity.android.util.Common;
import me.zimity.android.util.GPSHandler;

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

public class VideoActivity extends BasicImprintActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imprint_video);
	
        gps = new GPSHandler();
        gps.setActivity(this);
        gps.setMapView(map_view);
        gps.setCaptionText(captionText);
        gps.setSaveButton(save_button);
        gps.setSpeechButton(speech_button);
        gps.setContext(this);
        
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

        setTrackPageView("/ImprintVideo");
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