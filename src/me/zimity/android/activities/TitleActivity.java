package me.zimity.android.activities;

import me.zimity.android.app.R;
import android.os.Bundle;

public class TitleActivity extends BasicActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.title_activity);
	}
    
    @Override
    public void onStart() {
        super.onStart();
        
        setTrackPageView("/TitleScreen");
    }
}