//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package me.zimity.android.app;

import android.os.Bundle;
import android.view.KeyEvent;
import com.googlecode.androidannotations.api.SdkVersionHelper;
import me.zimity.android.app.R.layout;

public final class Register_
    extends Register
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        beforeCreate_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.main);
        afterSetContentView_(savedInstanceState);
    }

    private void beforeCreate_(Bundle savedInstanceState) {
    }

    private void afterSetContentView_(Bundle savedInstanceState) {
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
