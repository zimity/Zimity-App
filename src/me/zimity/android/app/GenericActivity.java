package me.zimity.android.app;

import me.zimity.android.util.Common;
import android.content.res.Resources;
import android.os.Bundle;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;

import greendroid.app.GDActivity;
import greendroid.widget.ActionBarItem;

public class GenericActivity extends GDActivity {

	private GoogleAnalyticsTracker tracker;
	
	public final static int TYPE_SETTINGS = 0;
	public final static int TYPE_HELP = 1;
	public final static int TYPE_SEARCH = 2;
	public final static int TYPE_ADD = 3;
	public final static int TYPE_REFRESH = 4;
	public final static int TYPE_COMPASS = 5;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Resources res = getResources();

		tracker = GoogleAnalyticsTracker.getInstance();
		tracker.startNewSession(
				res.getString(R.string.GOOGLE_ANALYTICS_API_KEY),
				Common.ANALYTICS_DISPATCH_INTERVAL, this);
	}

	@Override
	public boolean onHandleActionBarItemClick(ActionBarItem item, int position) {
		switch (item.getItemId()) {
		case TYPE_ADD:
			onClickTypeAdd();
			return true;
		case TYPE_COMPASS:
			onClickTypeCompass();
			return true;
		case TYPE_SEARCH:
			onClickTypeSearch();
			return true;
		case TYPE_SETTINGS:
			onClickTypeSettings();
			return true;
		case TYPE_HELP:
			onClickTypeHelp();
			return true;
		default:
			return super.onHandleActionBarItemClick(item, position);
		}
	}

	@Override
	public void onStop() {
		super.onStop();

		tracker.stopSession();
	}

	public void setTrackPageView(String page) {
		tracker.trackPageView(page);
	}

	public void onClickTypeAdd() {
	}

	public void onClickTypeCompass() {
	}

	public void onClickTypeSearch() {
	}
	
	public void onClickTypeRefresh() {
	}
	
	public void onClickTypeSettings() {
	}
	
	public void onClickTypeHelp() {
	}
}