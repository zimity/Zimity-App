package me.zimity.android.app;

import greendroid.widget.ActionBarItem.Type;

import android.os.Bundle;

public class NearByActivity extends GenericActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.nearby_activity);
		
		addActionBarItem(Type.Talk);
		addActionBarItem(Type.Settings);
		addActionBarItem(Type.Search);
	}
    
    @Override
    public void onStart() {
        super.onStart();
        
        setTrackPageView("NearBy");
    }
}