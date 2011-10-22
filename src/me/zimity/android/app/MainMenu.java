package me.zimity.android.app;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

@EActivity(R.layout.mainmenu)
public class MainMenu extends Activity {
    
	private GoogleAnalyticsTracker tracker;
	private Resources res;
    
	@AfterViews
	public void init() {
		res = this.getResources();
		
		tracker = GoogleAnalyticsTracker.getInstance();
		tracker.startNewSession(res.getString(R.string.GOOGLE_ANALYTICS_API_KEY), Common.ANALYTICS_DISPATCH_INTERVAL, this);
	}
    
	@Click(R.id.imprint)
    public void imprintActivity(View view) {
        startActivity(new Intent(this, ImprintMenu_.class));
    }
    
	@Click(R.id.search)
    public void searchActivity(View view) {
        startActivity(new Intent(this, Search_.class));
    }
    
	@Click(R.id.friends)
    public void friendsActivity(View view) {
        startActivity(new Intent(this, Friends_.class));
    }
    
	@Click(R.id.deals)
    public void dealsActivity(View view) {
        startActivity(new Intent(this, Deals_.class));
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