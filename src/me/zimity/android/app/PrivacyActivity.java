package me.zimity.android.app;

import me.zimity.android.util.Common;
import greendroid.app.GDActivity;
import roboguice.activity.RoboActivity;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.google.inject.Inject;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

public class PrivacyActivity extends GDActivity {
	
	private GoogleAnalyticsTracker tracker;
	private Resources res;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().requestFeature(Window.FEATURE_PROGRESS);

		setActionBarContentView(R.layout.privacy_activity);
				
		res = getResources();
		
		tracker = GoogleAnalyticsTracker.getInstance();
		tracker.startNewSession(res.getString(R.string.GOOGLE_ANALYTICS_API_KEY), Common.ANALYTICS_DISPATCH_INTERVAL, this);
		
        WebView privacyWebView = (WebView)findViewById(R.id.privacyWebView);
        privacyWebView.getSettings().setJavaScriptEnabled(true);

        final Activity activity = this;

        privacyWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                activity.setProgress(progress * 1000);
            }
        });

        privacyWebView.setWebChromeClient(new WebChromeClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingURL) {
                Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });

        privacyWebView.loadUrl(getString(R.string.privacyURL));
	}
    
    @Override
    public void onStart() {
        super.onStart();
        
        tracker.trackPageView("/Privacy");
    }
    
    @Override
    public void onStop() {
        super.onStop();
        
        tracker.stopSession();
    }
}



