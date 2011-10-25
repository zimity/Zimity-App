package me.zimity.android.app;

import greendroid.widget.ActionBarItem.Type;

import android.os.Bundle;
import android.widget.Toast;

public class FriendsActivity extends GenericActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.friends_activity);
		
		addActionBarItem(Type.Add, TYPE_ADD);
		addActionBarItem(Type.Compass, TYPE_COMPASS);
		addActionBarItem(Type.Search, TYPE_SEARCH);
	}
	
	@Override
	public void onClickTypeAdd() {
		super.onClickTypeAdd();
		
		Toast.makeText(this, "Friends Add", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onClickTypeCompass() {
		Toast.makeText(this, "Friends Search", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onClickTypeSearch() {
		Toast.makeText(this, "Friends Search", Toast.LENGTH_SHORT).show();
	}
    
    @Override
    public void onStart() {        
        this.setTrackPageView("/Friends");
    }
}