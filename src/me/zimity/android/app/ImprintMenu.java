package me.zimity.android.app;

import roboguice.activity.RoboActivity;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.google.inject.Inject;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

public class ImprintMenu extends RoboActivity {
	
	private GoogleAnalyticsTracker tracker;
	@Inject Resources res;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imprint);
				
		tracker = GoogleAnalyticsTracker.getInstance();
		tracker.startNewSession(res.getString(R.string.GOOGLE_ANALYTICS_API_KEY), Common.ANALYTICS_DISPATCH_INTERVAL, this);
	}
    
    public void onClickNoteButton(View view) {
        startActivity(new Intent(this, ImprintNote.class));
    }
    
    public void onClickAudioButton(View view) {
        startActivity(new Intent(this, ImprintAudio.class));
    }
    
    public void onClickPhotoButton(View view) {
        startActivity(new Intent(this, ImprintPhoto.class));
    }
    
    public void onClickVideoButton(View view) {
        startActivity(new Intent(this, ImprintVideo.class));
    }
    
    public void onClickReminderButton(View view) {
        startActivity(new Intent(this, ImprintReminder.class));
    }
    
    public void onClickEventButton(View view) {
        startActivity(new Intent(this, ImprintEvent.class));
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