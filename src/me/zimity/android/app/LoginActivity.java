package me.zimity.android.app;

import android.os.Bundle;

public class LoginActivity extends GenericActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.main);
	}
    
    @Override
    public void onStart() {
        super.onStart();
        
        setTrackPageView("/LoginScreen");
    }
}