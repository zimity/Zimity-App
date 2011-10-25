package me.zimity.android.app;

import greendroid.widget.ActionBarItem.Type;

import android.os.Bundle;

public class ReminderActivity extends GenericActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.main);
		
		addActionBarItem(Type.Share);
		addActionBarItem(Type.Settings);
	}
    
    @Override
    public void onStart() {
        super.onStart();
        
        setTrackPageView("/ImprintReminder");
    }
}