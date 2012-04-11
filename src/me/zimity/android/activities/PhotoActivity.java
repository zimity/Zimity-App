package me.zimity.android.activities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import me.zimity.android.app.R;
import me.zimity.android.util.Common;
import me.zimity.android.util.GPSHandler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class PhotoActivity extends BasicImprintActivity implements SurfaceHolder.Callback {

    private Camera camera;
    private Parameters parameters;
  
  	private SurfaceView surface;
  	
  	private Context context;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_activity);
		
		context = getApplicationContext();
		
		surface = (SurfaceView)findViewById(R.id.surface);
		
        gps = new GPSHandler();
        gps.setActivity(this);
        gps.setMapView(map_view);
        gps.setCaptionText(captionText);
        gps.setSaveButton(save_button);
        gps.setSpeechButton(speech_button);
        gps.setContext(this);
        
        SurfaceHolder holder = surface.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.setFixedSize(400, 300);
	}

    @Override
    public void onStart() {
        super.onStart();

        setTrackPageView("/ImprintPhoto");
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

    /*
     * Implementing mandatory method
     */
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera = Camera.open();
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
                String filename = zimityDirectory.getAbsoluteFile() + "/zimity_" + fileDate + ".jpg";
                
                Log.e("filename:", filename);
                outStream = new FileOutputStream(filename);
                outStream.write(data);
                outStream.close();
                
                gps.transmitData(Common.ImprintType.PHOTO, zimityDirectory.getAbsoluteFile() + "/zimity_" + fileDate + ".jpg");
            } catch (FileNotFoundException e) {
                Log.d("CAMERA", e.getMessage());
            } catch (IOException e) {
                Log.d("CAMERA", e.getMessage());
            }
        }
    };
        
    public void onClickPhotoEffectButton(View view) {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        gps.speechResult(requestCode, resultCode, data);
    }
  
    
    public void onClickSharingButton(View view) {
        gps.sharingSelection();
    }

    public void onClickSpeechButton(View view) {
        gps.speechInput();
    }
    
	public void onClickSaveButton(View view) {
        camera.takePicture(shutterCallback, rawCallback, jpegCallback);
	}
}