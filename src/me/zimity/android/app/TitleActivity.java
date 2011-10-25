package me.zimity.android.app;

import android.os.Bundle;

public class TitleActivity extends GenericActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.title_activity);
	}
    
    @Override
    public void onStart() {
        super.onStart();
        
        setTrackPageView("/TitleScreen");
    }
}