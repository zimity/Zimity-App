package me.zimity.android.app;

import java.io.IOException;
import java.net.MalformedURLException;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

@EActivity(R.layout.imprint)
public class ImprintMenu extends Activity {
	
	private GoogleAnalyticsTracker tracker;
	private Resources res;
	
	@AfterViews
	public void init() {
		res = this.getResources();
		
		tracker = GoogleAnalyticsTracker.getInstance();
		tracker.startNewSession(res.getString(R.string.GOOGLE_ANALYTICS_API_KEY), Common.ANALYTICS_DISPATCH_INTERVAL, this);
	}
    
	@Click(R.id.caption_button)
    public void captionActivity(View view) {
        startActivity(new Intent(this, ImprintNote_.class));
    }
    
	@Click(R.id.audio_button)
    public void audioActivity(View view) {
        startActivity(new Intent(this, ImprintAudio_.class));
    }
    
	@Click(R.id.photo)
    public void photoActivity(View view) {
        startActivity(new Intent(this, ImprintPhoto_.class));
    }
    
	@Click(R.id.video)
    public void videoActivity(View view) {
        startActivity(new Intent(this, ImprintVideo_.class));
    }
    
	@Click(R.id.reminder)
    public void reminderActivity(View view) {
        startActivity(new Intent(this, ImprintReminder_.class));
    }
    
	@Click(R.id.event)
    public void eventActivity(View view) {
        startActivity(new Intent(this, ImprintEvent_.class));
    }
    
    @Override
    public void onStart() {
        super.onStart();
        
        tracker.trackPageView("/Home");
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        
        tracker.stopSession();
    }
}