package me.zimity.android.activities;

import me.zimity.android.app.R;
import me.zimity.android.app.R.layout;
import android.os.Bundle;

public class ReminderActivity extends BasicImprintActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
//		addActionBarItem(Type.Share);
//		addActionBarItem(Type.Settings);
	}
    
    @Override
    public void onStart() {
        super.onStart();
        
        setTrackPageView("/ImprintReminder");
    }
}