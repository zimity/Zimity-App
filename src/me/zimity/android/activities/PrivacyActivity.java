package me.zimity.android.activities;

import me.zimity.android.app.R;
import me.zimity.android.app.R.id;
import me.zimity.android.app.R.layout;
import me.zimity.android.app.R.string;
import android.os.Bundle;
import android.webkit.WebView;

public class PrivacyActivity extends BasicActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.privacy_activity);
		
        WebView privacyWebView = (WebView)findViewById(R.id.privacyWebView);
        privacyWebView.getSettings().setJavaScriptEnabled(true);

        //final Activity activity = this;

        /*
        privacyWebView.setWebViewClient(new WebViewClient() {
        	@Override
            public void onPageFinished(WebView view, String url) {
            	loading.setLoading(false);
            }
            
        	@Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            	loading.setLoading(true);
            }
            
        	@Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingURL) {
            	// TODO: Display loading error
            	
                Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });
        */

        privacyWebView.loadUrl(getString(R.string.privacyURL));
	}
    
    @Override
    public void onStart() {
        super.onStart();
        
        setTrackPageView("/Privacy");
    }
}



