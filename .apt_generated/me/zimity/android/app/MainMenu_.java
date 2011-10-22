//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package me.zimity.android.app;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import com.googlecode.androidannotations.api.SdkVersionHelper;
import me.zimity.android.app.R.id;
import me.zimity.android.app.R.layout;

public final class MainMenu_
    extends MainMenu
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        beforeCreate_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.mainmenu);
        afterSetContentView_(savedInstanceState);
    }

    private void beforeCreate_(Bundle savedInstanceState) {
    }

    private void afterSetContentView_(Bundle savedInstanceState) {
        findViewById(id.friends).setOnClickListener(new OnClickListener() {


            public void onClick(View view) {
                friendsActivity(view);
            }

        }
        );
        findViewById(id.search).setOnClickListener(new OnClickListener() {


            public void onClick(View view) {
                searchActivity(view);
            }

        }
        );
        findViewById(id.imprint).setOnClickListener(new OnClickListener() {


            public void onClick(View view) {
                imprintActivity(view);
            }

        }
        );
        findViewById(id.deals).setOnClickListener(new OnClickListener() {


            public void onClick(View view) {
                dealsActivity(view);
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