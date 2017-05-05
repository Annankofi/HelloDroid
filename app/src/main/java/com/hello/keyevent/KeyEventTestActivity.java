package com.hello.keyevent;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hello.droid.BaseListActivity;
import com.hello.droid.Info;

import java.util.ArrayList;
import java.util.List;


public class KeyEventTestActivity extends BaseListActivity {
    private static final String TAG = KeyEventTestActivity.class.getSimpleName();
    private boolean mIsHomeCatchedEnabled = false;

    private enum DataItems {
        ENABLE_HOME, DISABLE_HOME
    }

    @Override
    protected List<Info> buildDatas() {
        List<Info> datas = new ArrayList<Info>();
        for (DataItems e : DataItems.values()) {
            Info info = new Info(e.toString(), "");
            datas.add(info);
        }
        return datas;
    }

    @Override
    protected void onListItemClick(ListView listView, View v, int position, long id) {
        Log.d(TAG, "onListItemClick " + mHeaderAdapter.getData().get(position).getTitle());
        switch (DataItems.valueOf(mHeaderAdapter.getData().get(position).getTitle())) {
            case ENABLE_HOME:
                mIsHomeCatchedEnabled = true;
                break;
            case DISABLE_HOME:
                mIsHomeCatchedEnabled = false;
                break;
            default:
                break;
        }

        super.onListItemClick(listView, v, position, id);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onListItemClick " + mHeaderAdapter.getData().get(position).getTitle());
        switch (DataItems.valueOf(mHeaderAdapter.getData().get(position).getTitle())) {
            default:
                break;
        }
        //return true and will not deal with short click event
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyDown keyConde:" + keyCode + " event.label:" + event.getDisplayLabel());
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onAttachedToWindow() {
        //Test fail
        Log.d(TAG, "onAttachedToWindow and mIsHomeCatchedEnabled " + mIsHomeCatchedEnabled);
        mIsHomeCatchedEnabled = true;
        if (mIsHomeCatchedEnabled) {
            //This only works with target API 12.otherwise will cause:
            //java.lang.IllegalArgumentException: Window type can not be changed after the window is added
            // this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        }
        super.onAttachedToWindow();
    }
}
