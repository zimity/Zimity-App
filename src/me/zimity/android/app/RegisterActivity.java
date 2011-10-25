package me.zimity.android.app;

import greendroid.widget.ActionBarItem.Type;
import android.os.Bundle;

public class RegisterActivity extends GenericActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.main);
		
		addActionBarItem(Type.Info);
		addActionBarItem(Type.Help);
	}
	
    @Override
    public void onStart() {
        super.onStart();
        
        setTrackPageView("/Register");
    }
}