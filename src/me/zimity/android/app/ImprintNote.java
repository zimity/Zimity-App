package me.zimity.android.app;

import com.flurry.android.FlurryAgent;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/*
 * Allows the user to create new text-based imprints.
 * 
 */

public class ImprintNote extends MapActivity {

    GPSHandler gps;
    TextView noteText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imprint_caption);
        
        noteText = (TextView) findViewById(R.id.captionText);
        
        gps = new GPSHandler(this, (MapView) findViewById(R.id.map_view),
                noteText,
                (ImageButton) findViewById(R.id.save_button),
                (ImageButton) findViewById(R.id.speech_button));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        gps.speechResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.e("Entering onStart()", ".");

        Resources res = getResources();
        FlurryAgent.onStartSession(this, res.getString(R.string.flurryid));
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.e("Entering onStop()", ".");

        FlurryAgent.onEndSession(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.e("Entering onResume()", ".");

        gps.startLocationUpdates();
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.e("Entering onPause()", ".");

        gps.stopLocationUpdates();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.e("Entering onDestroy()", ".");
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

    public void sharingSelection(View view) {
        gps.sharingSelection();
    }

    public void saveCaption(View view) {
        if (noteText.getText().length() != 0) {
            gps.transmitData(Common.IMPRINT_TYPE_NOTE, "");
        }
    }

    public void speechInput(View view) {
        gps.speechInput();
    }
}