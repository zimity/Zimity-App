package me.zimity.android.app;

import com.flurry.android.FlurryAgent;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

public class Terms extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms);
        
        WebView termsWebView = (WebView)findViewById(R.id.termsWebView);
        termsWebView.getSettings().setJavaScriptEnabled(true);

        getWindow().requestFeature(Window.FEATURE_PROGRESS);

        final Activity activity = this;

        termsWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                activity.setProgress(progress * 1000);
            }
        });

        termsWebView.setWebChromeClient(new WebChromeClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingURL) {
                Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });

        termsWebView.loadUrl(getString(R.string.termsURL));
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