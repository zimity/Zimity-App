//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package me.zimity.android.app;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.android.maps.MapView;
import com.googlecode.androidannotations.api.SdkVersionHelper;
import me.zimity.android.app.R.id;
import me.zimity.android.app.R.layout;

public final class ImprintNote_
    extends ImprintNote
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        beforeCreate_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.imprint_note);
        afterSetContentView_(savedInstanceState);
    }

    private void beforeCreate_(Bundle savedInstanceState) {
    }

    private void afterSetContentView_(Bundle savedInstanceState) {
        save_button = ((ImageButton) findViewById(id.save_button));
        map_view = ((MapView) findViewById(id.map_view));
        speech_button = ((ImageButton) findViewById(id.speech_button));
        captionText = ((TextView) findViewById(id.captionText));
        findViewById(id.save_button).setOnClickListener(new OnClickListener() {


            public void onClick(View view) {
                saveCaption(view);
            }

        }
        );
        findViewById(id.sharing_button).setOnClickListener(new OnClickListener() {


            public void onClick(View view) {
                onClickSharingButton(view);
            }

        }
        );
        findViewById(id.speech_button).setOnClickListener(new OnClickListener() {


            public void onClick(View view) {
                onClickSpeechInput(view);
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
