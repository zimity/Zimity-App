package me.zimity.android.app;

import greendroid.app.GDListActivity;
import me.zimity.android.util.Common;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import greendroid.widget.ItemAdapter;
import greendroid.widget.item.DescriptionItem;
import greendroid.widget.item.DrawableItem;
import greendroid.widget.item.Item;
import greendroid.widget.item.SeparatorItem;
import greendroid.widget.item.TextItem;
import greendroid.widget.item.ThumbnailItem;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import android.content.Intent;
import android.content.res.Resources;

public class SettingsActivity extends GDListActivity {

	private GoogleAnalyticsTracker tracker;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Resources res = getResources();

		tracker = GoogleAnalyticsTracker.getInstance();
		tracker.startNewSession(
				res.getString(R.string.GOOGLE_ANALYTICS_API_KEY),
				Common.ANALYTICS_DISPATCH_INTERVAL, this);

		List<Item> items = new ArrayList<Item>();

		items.add(new ThumbnailItem("Notifications",
				"Sound, vibration, notifications.", R.drawable.mini_logo));
		items.add(new ThumbnailItem("Your Account",
				"Your name, username and email.", R.drawable.mini_logo));
		items.add(new ThumbnailItem("Help & About Us",
				"About Zimity, feedback and help.", R.drawable.mini_logo));

		final ItemAdapter adapter = new ItemAdapter(this, items);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		switch (position) {
		case 0:
			startActivity(new Intent(this, NotificationsPreferences.class));
			break;
		case 1:
			startActivity(new Intent(this, YourAccountPreferences.class));
			break;
		case 2:
			startActivity(new Intent(this, AboutPreferences.class));
			break;
		default:
			break;
		}
	}

	@Override
	public void onStart() {
		super.onStart();

		tracker.trackPageView("/Settings");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		tracker.stopSession();
	}
}