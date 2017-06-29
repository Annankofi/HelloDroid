package com.hello.keyevent;

import android.content.Intent;
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

    InputEventMonitor mInputEventMonitor;
    private enum DataItems {
        KEY_EVENT_PRINTER, INPUT_EVENT_MONITOR,INPUT_EVENT_UNREGISTER
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
            case KEY_EVENT_PRINTER:
                Intent intent = new Intent("android.intent.action.KEY_EVENT_PRINTER");
                startActivity(intent);
                break;
            case INPUT_EVENT_MONITOR:
                mInputEventMonitor = new InputEventMonitor(this);
                if (mInputEventMonitor != null) {
                    mInputEventMonitor.start();
                }
                break;
            case INPUT_EVENT_UNREGISTER:

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
            case INPUT_EVENT_MONITOR:
                if (mInputEventMonitor != null) {
                    mInputEventMonitor.stop();
                }
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
        super.onAttachedToWindow();
    }
}
