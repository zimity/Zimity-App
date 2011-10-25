package me.zimity.android.app;

import greendroid.widget.ActionBarItem.Type;
import android.os.Bundle;

public class BookmarksActivity extends GenericActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.bookmarks_activity);
		
		addActionBarItem(Type.Add, TYPE_ADD);
		addActionBarItem(Type.Search, TYPE_SEARCH);
	}
    
    @Override
    public void onStart() {
        super.onStart();
        
        setTrackPageView("/Bookmarks");
    }
}