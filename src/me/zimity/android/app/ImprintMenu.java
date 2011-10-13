package me.zimity.android.app;

import java.io.IOException;
import java.net.MalformedURLException;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.flurry.android.FlurryAgent;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ImprintMenu extends Activity {
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imprint);
    }
    
    public void captionActivity(View view) {
        startActivity(new Intent(this, ImprintNote.class));
    }
    
    public void audioActivity(View view) {
        startActivity(new Intent(this, ImprintAudio.class));
    }
    
    public void photoActivity(View view) {
        startActivity(new Intent(this, ImprintPhoto.class));
    }
    
    public void videoActivity(View view) {
        startActivity(new Intent(this, ImprintVideo.class));
    }
    
    public void reminderActivity(View view) {
        startActivity(new Intent(this, ImprintReminder.class));
    }
    
    public void eventActivity(View view) {
        startActivity(new Intent(this, ImprintReminder.class));
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
}