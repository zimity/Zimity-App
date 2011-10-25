package me.zimity.android.app;

import greendroid.widget.ActionBarItem.Type;

import android.os.Bundle;

public class DealsActivity extends GenericActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.deals_activity);
		
		addActionBarItem(Type.Refresh, TYPE_REFRESH);
		addActionBarItem(Type.Settings, TYPE_SETTINGS);
		addActionBarItem(Type.Search, TYPE_SEARCH);
	}

    @Override
    public void onStart() {
       super.onStart();
        
       setTrackPageView("/Deals");
    }
}