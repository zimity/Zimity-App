package me.zimity.android.app;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/*
 * Allows the user to create new text-based imprints.
 * 
 */

@EActivity(R.layout.imprint_note)
public class ImprintNote extends MapActivity {

	private GPSHandler gps;
	private GoogleAnalyticsTracker tracker;
	private Resources res;

	@ViewById
	TextView captionText;

	@ViewById
	ImageButton save_button;

	@ViewById
	ImageButton speech_button;

	@ViewById
	MapView map_view;

	@AfterViews
	public void init() {
		res = this.getResources();

		gps = new GPSHandler(this, map_view, captionText, save_button,
				speech_button);

		tracker = GoogleAnalyticsTracker.getInstance();
		tracker.startNewSession(
				res.getString(R.string.GOOGLE_ANALYTICS_API_KEY),
				Common.ANALYTICS_DISPATCH_INTERVAL, this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		gps.speechResult(requestCode, resultCode, data);
	}

	@Override
	public void onStart() {
		super.onStart();

		tracker.trackPageView("/ImprintNote");
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

	@Click(R.id.save_button)
	public void saveCaption(View view) {
		if (captionText.getText().length() != 0) {
			gps.transmitData(Common.IMPRINT_TYPE_NOTE, "");
		}
	}

    @Click(R.id.sharing_button)
    public void onClickSharingButton(View view) {
        gps.sharingSelection();
    }

    @Click(R.id.speech_button)
    public void onClickSpeechInput(View view) {
        gps.speechInput();
    }
}