package me.zimity.android.app;

import com.flurry.android.FlurryAgent;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainMenu extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
    }
    
    public void imprintActivity(View view) {
        startActivity(new Intent(this, ImprintMenu.class));
    }
    
    public void searchActivity(View view) {
        startActivity(new Intent(this, Search.class));
    }
    
    public void friendsActivity(View view) {
        startActivity(new Intent(this, Friends.class));
    }
    
    public void dealsActivity(View view) {
        startActivity(new Intent(this, Deals.class));
    }
    
    @Override
    public void onStart() {
        super.onStart();
        
        Resources res = getResources();
        FlurryAgent.onStartSession(this, res.getString(R.string.flurryid));
    }
    
    @Override
    public void onStop() {
        super.onStop();
        
        FlurryAgent.onEndSession(this);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.settings:
            startActivity(new Intent(this, Settings.class));
            return true;
        default: 
            return super.onOptionsItemSelected(item);
        }
    }
}