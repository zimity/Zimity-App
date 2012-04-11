package me.zimity.android.activities;

import android.os.Bundle;

public class SettingsActivity extends BasicActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		List<Item> items = new ArrayList<Item>();
//
//		items.add(new ThumbnailItem("Notifications",
//				"Sound, vibration, notifications.", R.drawable.mini_logo));
//		items.add(new ThumbnailItem("Your Account",
//				"Your name, username and email.", R.drawable.mini_logo));
//		items.add(new ThumbnailItem("Help & About Us",
//				"About Zimity, feedback and help.", R.drawable.mini_logo));
//
//		final ItemAdapter adapter = new ItemAdapter(this, items);
//		setListAdapter(adapter);
	}

//	@Override
//	protected void onListItemClick(ListView l, View v, int position, long id) {
//		switch (position) {
//		case 0:
//			startActivity(new Intent(this, NotificationsPreferences.class));
//			break;
//		case 1:
//			startActivity(new Intent(this, YourAccountPreferences.class));
//			break;
//		case 2:
//			startActivity(new Intent(this, AboutPreferences.class));
//			break;
//		default:
//			break;
//		}
//	}

	@Override
	public void onStart() {
		super.onStart();

		setTrackPageView("/Settings");
	}

}