package me.zimity.android.app;

import com.flurry.android.FlurryAgent;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.Preference;

public class Settings extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        
        Resources res = getResources();
        FlurryAgent.onStartSession(this, res.getString(R.string.flurryid));
    }
    
    @Override
    public void onStop() {
        super.onStop();
        
        FlurryAgent.onEndSession(this);
    }
}