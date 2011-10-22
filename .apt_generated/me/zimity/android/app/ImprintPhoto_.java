//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package me.zimity.android.app;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.android.maps.MapView;
import com.googlecode.androidannotations.api.SdkVersionHelper;
import me.zimity.android.app.R.id;
import me.zimity.android.app.R.layout;

public final class ImprintPhoto_
    extends ImprintPhoto
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        beforeCreate_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.imprint_photo);
        afterSetContentView_(savedInstanceState);
    }

    private void beforeCreate_(Bundle savedInstanceState) {
    }

    private void afterSetContentView_(Bundle savedInstanceState) {
        speech_button = ((ImageButton) findViewById(id.speech_button));
        map_view = ((MapView) findViewById(id.map_view));
        captionText = ((TextView) findViewById(id.captionText));
        save_button = ((ImageButton) findViewById(id.save_button));
        surface = ((SurfaceView) findViewById(id.surface));
        findViewById(id.speech_button).setOnClickListener(new OnClickListener() {


            public void onClick(View view) {
                onClickSpeechInput(view);
            }

        }
        );
        findViewById(id.sharing_button).setOnClickListener(new OnClickListener() {


            public void onClick(View view) {
                onClickSharingButton(view);
            }

        }
        );
        findViewById(id.save_button).setOnClickListener(new OnClickListener() {


            public void onClick(View view) {
                onClickSaveButton(view);
            }

        }
        );
        init();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (((SdkVersionHelper.getSdkInt()< 5)&&(keyCode == KeyEvent.KEYCODE_BACK))&&(event.getRepeatCount() == 0)) {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

}
