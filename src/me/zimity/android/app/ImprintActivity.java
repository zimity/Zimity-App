package me.zimity.android.app;

import me.zimity.android.util.Common;
import roboguice.activity.RoboActivity;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.google.inject.Inject;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import greendroid.app.GDActivity;
import greendroid.widget.ActionBar;
import greendroid.widget.ActionBarItem.Type;

public class ImprintActivity extends GDActivity {
	
	private GoogleAnalyticsTracker tracker;
	private Resources res;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.imprint_activity);
		
		addActionBarItem(Type.Settings);
		
		res = getResources();

		tracker = GoogleAnalyticsTracker.getInstance();
		tracker.startNewSession(res.getString(R.string.GOOGLE_ANALYTICS_API_KEY), Common.ANALYTICS_DISPATCH_INTERVAL, this);
	}
    
    public void onClickNoteButton(View view) {
        startActivity(new Intent(this, NoteActivity.class));
    }
    
    public void onClickAudioButton(View view) {
        startActivity(new Intent(this, AudioActivity.class));
    }
    
    public void onClickPhotoButton(View view) {
        startActivity(new Intent(this, PhotoActivity.class));
    }
    
    public void onClickVideoButton(View view) {
        startActivity(new Intent(this, VideoActivity.class));
    }
    
    public void onClickReminderButton(View view) {
        startActivity(new Intent(this, ReminderActivity.class));
    }
    
    public void onClickEventButton(View view) {
        startActivity(new Intent(this, EventActivity.class));
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