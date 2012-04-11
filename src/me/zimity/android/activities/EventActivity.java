package me.zimity.android.activities;

import me.zimity.android.app.R;
import me.zimity.android.app.R.layout;
import android.os.Bundle;

public class EventActivity extends BasicImprintActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public void onStart() {
		super.onStart();

		setTrackPageView("/ImprintEvent");
	}
}