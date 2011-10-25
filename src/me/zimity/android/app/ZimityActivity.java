package me.zimity.android.app;

import me.zimity.android.util.Common;
import greendroid.app.GDActivity;
import greendroid.graphics.drawable.ActionBarDrawable;
import greendroid.widget.ActionBar;
import greendroid.widget.ActionBarItem;
import greendroid.widget.NormalActionBarItem;
import greendroid.widget.ActionBarItem.Type;
import roboguice.activity.RoboActivity;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.google.inject.Inject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.Contacts.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class ZimityActivity extends GDActivity {

	private GoogleAnalyticsTracker tracker;
	private Resources res;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.zimity_activity);

		this.addActionBarItem(Type.Settings);
		this.addActionBarItem(Type.Help);

		res = getResources();

		tracker = GoogleAnalyticsTracker.getInstance();
		tracker.startNewSession(
				res.getString(R.string.GOOGLE_ANALYTICS_API_KEY),
				Common.ANALYTICS_DISPATCH_INTERVAL, this);
	}

	@Override
	public boolean onHandleActionBarItemClick(ActionBarItem item, int position) {

		switch (position) {
		case 0:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		case 1:
			Toast.makeText(this, "Help", Toast.LENGTH_SHORT).show();
			return true;
		default:
			return super.onHandleActionBarItemClick(item, position);
		}
	}

	public void onClickImprintButton(View view) {
		startActivity(new Intent(this, ImprintActivity.class));
	}

	public void onClickSearchButton(View view) {
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

		tracker.trackPageView("/Home");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		tracker.stopSession();
	}
}