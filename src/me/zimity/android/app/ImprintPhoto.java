package me.zimity.android.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.flurry.android.FlurryAgent;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ImprintPhoto extends MapActivity implements SurfaceHolder.Callback {

    private static int TAKE_PICTURE = 1;
    private Uri outputFileUri;
    private Camera camera;
    
    //TextView captionText;
    
    GPSHandler gps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imprint_photo);
        
        gps = new GPSHandler(this, (MapView) findViewById(R.id.map_view),
                (TextView) findViewById(R.id.captionText),
                (ImageButton) findViewById(R.id.save_button),
                (ImageButton) findViewById(R.id.speech_button));
        
        SurfaceView surface = (SurfaceView) findViewById(R.id.surface);
        SurfaceHolder holder = surface.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.setFixedSize(400, 300);
    }

    public void savePhoto(View view) {
        camera.takePicture(shutterCallback, rawCallback, jpegCallback);
    }

    @Override
    public void onStart() {
        super.onStart();

        Resources res = getResources();
        FlurryAgent.onStartSession(this, res.getString(R.string.flurryid));
    }

    @Override
    public void onStop() {
        super.onStop();

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

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera = camera.open();
            camera.setPreviewDisplay(holder);        
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
    }

    ShutterCallback shutterCallback = new ShutterCallback() {
        public void onShutter() {
            // TODO Do something when the shutter closes.
        }
    };
    PictureCallback rawCallback = new PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            // TODO Do something with the image RAW data.
        }
    };
    PictureCallback jpegCallback = new PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            SimpleDateFormat formatDate = new SimpleDateFormat("MMddyy_hhmmss");
            String fileDate = formatDate.format(new Date());
            
            File zimityDirectory = new File(
                    Environment.getExternalStorageDirectory()
                            + Common.FILE_BASE_DIRECTORY);
            zimityDirectory.mkdirs(); // Create the directory if not already exists)
            
            FileOutputStream outStream = null;
            try {
                Log.e("filename:", zimityDirectory.getAbsoluteFile() + "/zimity_" + fileDate + ".jpg");
                outStream = new FileOutputStream(zimityDirectory.getAbsoluteFile() + "/zimity_" + fileDate + ".jpg");
                outStream.write(data);
                outStream.close();
                
                
                gps.transmitData(Common.IMPRINT_TYPE_PHOTO, zimityDirectory.getAbsoluteFile() + "/zimity_" + fileDate + ".jpg");
            } catch (FileNotFoundException e) {
                Log.d("CAMERA", e.getMessage());
            } catch (IOException e) {
                Log.d("CAMERA", e.getMessage());
            }
        }
    };
    
    Camera.Parameters parameters;
    
    public void photoEffect(View view) {
        final CharSequence[] items;
        
        parameters = camera.getParameters();
        List<String> cameraEffects = parameters.getSupportedColorEffects();
        items = new CharSequence[cameraEffects.size()];
        for (int i = 0; i < cameraEffects.size(); i++) {
            items[i] = cameraEffects.get(i).substring(0, 1).toUpperCase() + cameraEffects.get(i).substring(1);
        }
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.PHOTO_EFFECT_DIALOG_TITLE));
        builder.setSingleChoiceItems(items, -1,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        parameters.setColorEffect(items[item].toString().toLowerCase());
                        camera.setParameters(parameters);
                    }
                });
        builder.setPositiveButton(getString(R.string.OK_BUTTON_TEXT), null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected boolean isRouteDisplayed() {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        gps.speechResult(requestCode, resultCode, data);
    }
    
    public void sharingSelection(View view) {
        gps.sharingSelection();
    }

    public void speechInput(View view) {
        gps.speechInput();
    }
}