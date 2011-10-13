package me.zimity.android.app;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.flurry.android.FlurryAgent;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class GPSHandler {

    private final static int MAP_ZOOMLEVEL = 17;
    private final static int VOICE_RECOGNITION_REQUEST_CODE = 1985;
    private int userSharingChoice = Common.IMPRINT_SHARING_PRIVATE;

    private MapView mapView;
    private LocationManager locationManager;
    private MapController mapController;
    private String bestProvider;
    private String coarseProvider;
    private final Activity activity;

    private List<Overlay> mapOverlays;
    private Drawable drawable;
    private MapViewOverlay itemizedOverlay;

    private TextView captionText;
    private Location userLocation;

    private File sdDir = Environment.getExternalStorageDirectory();
    private ImageButton saveButton;
    private ImageButton speechButton;

    public GPSHandler(Activity act, MapView mapView, TextView captionText,
            ImageButton saveButton, ImageButton speechButton) {
        activity = act;

        this.mapView = mapView;
        mapView.setSatellite(true);
        mapView.setBuiltInZoomControls(true);

        mapController = mapView.getController();
        mapController.setZoom(MAP_ZOOMLEVEL);

        this.captionText = captionText;

        this.saveButton = saveButton;
        saveButton.setEnabled(false);

        mapOverlays = mapView.getOverlays();
        drawable = activity.getResources().getDrawable(R.drawable.mini_logo);
        itemizedOverlay = new MapViewOverlay(drawable);

        locationManager = (LocationManager) activity
                .getSystemService(Context.LOCATION_SERVICE);

        Criteria criteriaFine = new Criteria();
        criteriaFine.setPowerRequirement(Criteria.POWER_LOW);
        criteriaFine.setAccuracy(Criteria.ACCURACY_FINE);
        criteriaFine.setAltitudeRequired(true);
        criteriaFine.setBearingRequired(true);
        criteriaFine.setSpeedRequired(true);
        criteriaFine.setCostAllowed(true);
        bestProvider = locationManager.getBestProvider(criteriaFine, true);

        Criteria criteriaCoarse = new Criteria();
        criteriaCoarse.setPowerRequirement(Criteria.POWER_LOW);
        criteriaCoarse.setAccuracy(Criteria.ACCURACY_COARSE);
        coarseProvider = locationManager.getBestProvider(criteriaCoarse, true);

        // Check to see if a recognition activity is present
        this.speechButton = speechButton;

        PackageManager pm = activity.getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0) {
            speechButton.setEnabled(true);
        } else {
            // Hide and disable if not available
            speechButton.setVisibility(View.INVISIBLE);
            speechButton.setEnabled(false);
        }

    }

    public void transmitData(int type, String s) {
        HashMap<String, String> hm = new HashMap<String, String>();

        hm.put(Common.IMPRINT_TYPE, Integer.toString(type));
        hm.put(Common.IMPRINT_NOTE, captionText.getText().toString());
        hm.put(Common.IMPRINT_USERID, "1");
        hm.put(Common.IMPRINT_CLIENT, Common.CLIENT_ID);
        hm.put(Common.IMPRINT_LATITUDE,
                Double.toString(userLocation.getLatitude()));
        hm.put(Common.IMPRINT_LONGITUDE,
                Double.toString(userLocation.getLongitude()));
        hm.put(Common.IMPRINT_ALTITUDE,
                Double.toString(userLocation.getAltitude()));
        hm.put(Common.IMPRINT_BEARING,
                Float.toString(userLocation.getBearing()));
        hm.put(Common.IMPRINT_SPEED, Float.toString(userLocation.getSpeed()));
        hm.put(Common.IMPRINT_ACCURACY,
                Float.toString(userLocation.getAccuracy()));
        hm.put(Common.IMPRINT_SHARING, Integer.toString(userSharingChoice));
        hm.put(Common.IMPRINT_SYNCD, "0");
        hm.put(Common.IMPRINT_DELETED, "0");
        
        // Only include an EXTRA value for multimedia imprints (ie. not notes only)
        if (type != Common.IMPRINT_TYPE_NOTE) {
            hm.put(Common.IMPRINT_EXTRA, s);
        }

        DataDelivery dm = new DataDelivery();
        dm.execute(hm);
        activity.finish();
    }

    public void startLocationUpdates() {
        // Initial Best Guess
        // Log.e("last known location network accuracy",
        // Float.toString(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getAccuracy()));
        // Log.e("last known location gps accuracy",
        // Float.toString(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getAccuracy()));
        // updateLocation(bestProvider);

        // We want to get updates as quickly as possible. Assuming that
        // coarseProvider will give us
        // the fastest fix (but less accurate), we use that to give our initial
        // position. After which
        // point, updates for coarseProvider are removed and bestProvider takes
        // over.
        locationManager
                .requestLocationUpdates(bestProvider, 0, 0, locationBest);
        locationManager.requestLocationUpdates(coarseProvider, 0, 0,
                locationCoarse);
    }

    public void stopLocationUpdates() {
        locationManager.removeUpdates(locationBest);
        locationManager.removeUpdates(locationCoarse);
    }

    private LocationListener locationCoarse = new LocationListener() {
        public void onLocationChanged(Location location) {
            // loadingScreen.dismiss();
            updateLocation(coarseProvider);

            Log.e("location coarse accuracy:",
                    Float.toString(location.getAccuracy()));

            locationManager.removeUpdates(locationCoarse);
        }

        public void onProviderDisabled(String p) {
        }

        public void onProviderEnabled(String p) {
        }

        public void onStatusChanged(String p, int s, Bundle e) {
        }
    };

    private LocationListener locationBest = new LocationListener() {
        public void onLocationChanged(Location location) {
            updateLocation(bestProvider);

            Log.e("location best accuracy:",
                    Float.toString(location.getAccuracy()));

            locationManager.removeUpdates(locationCoarse);
        }

        public void onProviderDisabled(String p) {
        }

        public void onProviderEnabled(String p) {
        }

        public void onStatusChanged(String p, int s, Bundle e) {
        }
    };

    private void updateLocation(String provider) {
        Location location = locationManager.getLastKnownLocation(provider);
        Double latitude = location.getLatitude() * 1E6;
        Double longitude = location.getLongitude() * 1E6;

        userLocation = location;

        GeoPoint point = new GeoPoint(latitude.intValue(), longitude.intValue());

        OverlayItem overlayitem = new OverlayItem(point, "", "");
        itemizedOverlay.clearOverlays();
        itemizedOverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedOverlay);

        mapController.animateTo(point);

        saveButton.setEnabled(true);
    }

    public void sharingSelection() {
        final CharSequence[] items = {
                activity.getString(R.string.SHARING_OPTION_PRIVATE),
                activity.getString(R.string.SHARING_OPTION_FRIENDS),
                activity.getString(R.string.SHARING_OPTION_GLOBAL) };

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.SHARING_OPTION_TITLE));
        builder.setSingleChoiceItems(items, -1,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                        case 0:
                            userSharingChoice = Common.IMPRINT_SHARING_PRIVATE;
                            break;
                        case 1:
                            userSharingChoice = Common.IMPRINT_SHARING_FRIENDS;
                            break;
                        case 2:
                            userSharingChoice = Common.IMPRINT_SHARING_GLOBAL;
                            break;
                        default:
                            userSharingChoice = Common.IMPRINT_SHARING_PRIVATE;
                        }
                    }
                });
        builder.setPositiveButton(activity.getString(R.string.OK_BUTTON_TEXT),
                null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void speechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Caption Text");
        activity.startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    public void speechResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE
                && resultCode == Activity.RESULT_OK) {
            ArrayList<String> matches = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            captionText.setText(matches.get(0)); // Only interested in the first
                                                 // 'guess'
        }
    }
}