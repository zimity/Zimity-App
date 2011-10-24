package me.zimity.android.app;

import roboguice.activity.RoboPreferenceActivity;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.google.inject.Inject;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.Preference;

public class Settings extends RoboPreferenceActivity {
	
	private GoogleAnalyticsTracker tracker;
	@Inject Resources res;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);

		tracker = GoogleAnalyticsTracker.getInstance();
		tracker.startNewSession(res.getString(R.string.GOOGLE_ANALYTICS_API_KEY), Common.ANALYTICS_DISPATCH_INTERVAL, this);
		
        addPreferencesFromResource(R.xml.settings);
        
        Preference privacyPolicy = (Preference)findPreference("privacyPolicy");
        privacyPolicy.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
               Settings.this.startActivity(new Intent(Settings.this, Privacy.class));
               return true;
            }  
        });
        
        Preference terms = (Preference)findPreference("termsOfService");
        terms.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
               Settings.this.startActivity(new Intent(Settings.this, Terms.class));
               return true;
            }  
        });
	}
	
    @Override
    public void onStart() {
        super.onStart();
        
        tracker.trackPageView("/Settings");
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        
        tracker.stopSession();
    }
}