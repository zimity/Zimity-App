package me.zimity.android.app;

import greendroid.widget.ActionBarItem.Type;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ZimityActivity extends GenericActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.zimity_activity);

		this.addActionBarItem(Type.Settings, TYPE_SETTINGS);
		this.addActionBarItem(Type.Help, TYPE_HELP);
	}
	
	@Override
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