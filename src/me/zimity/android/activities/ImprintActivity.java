package me.zimity.android.activities;

import me.zimity.android.app.R;
import me.zimity.android.app.R.layout;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ImprintActivity extends BasicActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imprint_activity);
	}
    
    public void onClickNoteButton(View view) {
        startActivity(new Intent(this, NoteActivity.class));
    }
    
    public void onClickAudioButton(View view) {
        startActivity(new Intent(this, AudioActivity.class));
    }
    
    public void onClickPhotoButton(View view) {
        startActivity(new Intent(this, PhotoActivity.class));
    }
    
    public void onClickVideoButton(View view) {
        startActivity(new Intent(this, VideoActivity.class));
    }
    
    public void onClickReminderButton(View view) {
        startActivity(new Intent(this, ReminderActivity.class));
    }
    
    public void onClickEventButton(View view) {
        startActivity(new Intent(this, EventActivity.class));
    }
    
    @Override
    public void onStart() {
        super.onStart();
        
        setTrackPageView("/Home");
    }
}