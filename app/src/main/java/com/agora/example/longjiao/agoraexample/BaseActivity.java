package com.agora.example.longjiao.agoraexample;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Created by apple on 15/9/15.
 */
public abstract class BaseActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onUserInteraction(view);
        }
    };

    public View.OnClickListener getViewClickListener(){
        return onClickListener;
    }

    public void onUserInteraction(View view){}
}
