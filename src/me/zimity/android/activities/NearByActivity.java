package me.zimity.android.activities;


import me.zimity.android.app.R;
import me.zimity.android.app.R.layout;
import android.os.Bundle;

public class NearByActivity extends BasicImprintActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nearby_activity);
	}
    
    @Override
    public void onStart() {
        super.onStart();
        
        setTrackPageView("NearBy");
    }
}