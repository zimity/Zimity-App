package me.zimity.android.app;

import android.os.Bundle;
import android.webkit.WebView;

public class TermsActivity extends GenericActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.terms_activity);
	
        WebView termsWebView = (WebView)findViewById(R.id.termsWebView);
        termsWebView.getSettings().setJavaScriptEnabled(true);

        //final Activity activity = this;

        /*
        termsWebView.setWebViewClient(new WebViewClient() {
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

        termsWebView.loadUrl(getString(R.string.termsURL));
	}
    
    @Override
    public void onStart() {
        super.onStart();
        
        setTrackPageView("/Terms");
    }
}