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
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/*
 * Allows the user to create new text-based imprints.
 * 
 */
public class NoteActivity extends BasicImprintActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_activity);
		
        gps = new GPSHandler();
        gps.setActivity(this);
        gps.setMapView(map_view);
        gps.setCaptionText(captionText);
        gps.setSaveButton(save_button);
        gps.setSpeechButton(speech_button);
        gps.setContext(this);
	}

	@Override
	public void onStart() {
		super.onStart();

		setTrackPageView("/ImprintNote");
	}

	public void onClickSaveButton(View view) {
		if (captionText.getText().length() != 0) {
			gps.transmitData(Common.ImprintType.NOTE, "");
		}
	}
	
    public void onClickSharingButton(View view) {
        gps.sharingSelection();
    }

    public void onClickSpeechButton(View view) {
        gps.speechInput();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        gps.speechResult(requestCode, resultCode, data);
    }
}