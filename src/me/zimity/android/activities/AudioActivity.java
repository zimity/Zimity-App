package me.zimity.android.activities;

import me.zimity.android.app.R;
import me.zimity.android.util.GPSHandler;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

public class AudioActivity extends BasicImprintActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_activity);

        gps = new GPSHandler();
        gps.setActivity(this);
        gps.setMapView(map_view);
        gps.setCaptionText(captionText);
        gps.setSaveButton(save_button);
        gps.setSpeechButton(speech_button);
        gps.setContext(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        gps.speechResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();

        setTrackPageView("/ImprintAudio");
    }

    public void onClickSharingButton(View view) {
        gps.sharingSelection();
    }

    public void onClickSpeechButton(View view) {
        gps.speechInput();
    }

    public void onClickSaveButton(View view) {
        // TODO:
    }
}