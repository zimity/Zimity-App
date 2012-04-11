package me.zimity.android.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import me.zimity.android.app.ImprintData;
import me.zimity.android.app.R;
import me.zimity.android.util.Common.SharingScope;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class GPSHandler {
    private final static int MAP_ZOOMLEVEL = 17;
    private final static int VOICE_RECOGNITION_REQUEST_CODE = 1985;
    private SharingScope shareScope = SharingScope.PRIVATE;

    private MapView mapView;
    
    private LocationManager locationManager;
    
    private MapController mapController;
    private String bestProvider;
    private String coarseProvider;
    private Activity activity;

    private List<Overlay> mapOverlays;
    private Drawable drawable;
    private MapViewOverlay itemizedOverlay;

    private TextView captionText;
    private Location userLocation;

    private File sdDir = Environment.getExternalStorageDirectory();
    private ImageButton saveButton;
    private ImageButton speechButton;
    
    private Context context;
    
    
    public void setMapView(MapView mapView) {
        this.mapView = mapView;
        mapView.setSatellite(true);
        mapView.setBuiltInZoomControls(true);
        
        mapController = mapView.getController();
        mapController.setZoom(MAP_ZOOMLEVEL);
        
        mapOverlays = mapView.getOverlays();
        drawable = activity.getResources().getDrawable(R.drawable.mini_logo);
        itemizedOverlay = new MapViewOverlay(drawable);
    }
    
    public void setCaptionText(TextView captionText) {
        this.captionText = captionText;
    }
    
    public void setSaveButton(ImageButton saveButton) {
        this.saveButton = saveButton;
        
        saveButton.setEnabled(false);
    }
    
    public void setSpeechButton(ImageButton speechButton) {
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
    
    public void setActivity(Activity activity) {
        this.activity = activity;
        
        locationManager = (LocationManager)activity.getSystemService(Context.LOCATION_SERVICE);

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
    }

    public GPSHandler() {
    }
    
    public void setContext(Context context) {
        this.context = context;
    }

    public void transmitData(Common.ImprintType type, String extra) {
        ImprintData imprintData = new ImprintData();
        imprintData.setImp_type(type.ordinal());
        imprintData.setNote(captionText.getText().toString());
        imprintData.setClient(Common.CLIENT_ID);
        imprintData.setLatitude(userLocation.getLatitude());
        imprintData.setLongitude(userLocation.getLongitude());
        imprintData.setAltitude(userLocation.getAltitude());
        imprintData.setBearing(userLocation.getBearing());
        imprintData.setSpeed(userLocation.getSpeed());
        imprintData.setAccuracy(userLocation.getAccuracy());
        imprintData.setSharing(shareScope.ordinal());
        imprintData.setSyncd(0);
        imprintData.setDeleted(0);
        
        Gson gson = new Gson();
        String json = gson.toJson(imprintData);
        
        File file;
        RequestParams params = new RequestParams();
        if (extra != "") {
            file = new File(extra);
            
            try {
                params.put("file", file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }            
        }
        
        params.put("metadata", json);
        
        AsyncHttpClient client = new AsyncHttpClient();
        //http://192.168.1.112:8888/imprints/add
        client.post("http://zimity.me/imprints/add", new AsyncHttpResponseHandler() {
            ProgressDialog pd;
            
            @Override
            public void onStart() {
                pd = ProgressDialog.show(context, "Uploading...", "Please wait.");
            }
            
            @Override
            public void onFailure(Throwable e) {
                Log.e("GPSHandle onFailure", e.getMessage());
            }
            
            @Override
            public void onFinish() {
                pd.dismiss();
            }
            
            @Override
            public void onSuccess(String response) {
                Log.d("GPSHandle onSuccess", response);
                pd.dismiss();
            }
        });
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
                            shareScope = SharingScope.PRIVATE;
                            break;
                        case 1:
                            shareScope = SharingScope.FRIENDS;
                            break;
                        case 2:
                            shareScope = SharingScope.GLOBAL;
                            break;
                        default:
                            shareScope = SharingScope.PRIVATE;
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