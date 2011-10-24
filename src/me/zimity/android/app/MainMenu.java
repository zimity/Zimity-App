package me.zimity.android.app;

import roboguice.activity.RoboActivity;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.google.inject.Inject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainMenu extends RoboActivity {
    
	private GoogleAnalyticsTracker tracker;
	@Inject Resources res;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
			
		tracker = GoogleAnalyticsTracker.getInstance();
		tracker.startNewSession(res.getString(R.string.GOOGLE_ANALYTICS_API_KEY), Common.ANALYTICS_DISPATCH_INTERVAL, this);

	}
    
    public void onClickImprintButton(View view) {
        startActivity(new Intent(this, ImprintMenu.class));
    }
    
    public void onClickSearchButton(View view) {
        startActivity(new Intent(this, Search.class));
    }
    
    public void onClickFriendsButton(View view) {
        startActivity(new Intent(this, Friends.class));
    }
    
    public void onClickDealsButton(View view) {
        startActivity(new Intent(this, Deals.class));
    }
    
    @Override
    public void onStart() {
        super.onStart();
        
        tracker.trackPageView("/MainMenu");
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        
        tracker.stopSession();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.settings:
            startActivity(new Intent(this, Settings.class));
            return true;
        default: 
            return super.onOptionsItemSelected(item);
        }
    }
}