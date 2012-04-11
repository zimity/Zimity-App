package me.zimity.android.activities;

import me.zimity.android.app.R;
import me.zimity.android.app.R.layout;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ZimityActivity extends BasicActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zimity_activity);
	}
	
	public void onClickTypeSettings() {
		startActivity(new Intent(this, SettingsActivity.class));
	}

	public void onClickImprintButton(View view) {
		startActivity(new Intent(this, ImprintActivity.class));
	}

	public void onClickBookmarksButton(View view) {
		startActivity(new Intent(this, BookmarksActivity.class));
	}

	public void onClickFriendsButton(View view) {
		startActivity(new Intent(this, FriendsActivity.class));
	}

	public void onClickDealsButton(View view) {
		startActivity(new Intent(this, DealsActivity.class));
	}

	@Override
	public void onStart() {
		super.onStart();

		setTrackPageView("/Home");
	}
}