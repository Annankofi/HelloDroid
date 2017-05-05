package com.hello.anr;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hello.droid.BaseListActivity;
import com.hello.droid.Info;

import java.util.ArrayList;
import java.util.List;

public class AnrTestActivity extends BaseListActivity {
    private static final String TAG = AnrTestActivity.class.getSimpleName();

    private enum DataItems {
        DISPATCH, BROADCAST, SERVICE
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
            case DISPATCH:
                try {
                    Thread.sleep(12000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case BROADCAST:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.anr");
                sendBroadcast(intent);
                break;
            case SERVICE:
                Intent intentService = new Intent(this, ServiceAnr.class);
                startService(intentService);
                break;

            default:
                break;
        }
        super.onListItemClick(listView, v, position, id);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemLongClick " + mHeaderAdapter.getData().get(position).getTitle());
        switch (DataItems.valueOf(mHeaderAdapter.getData().get(position).getTitle())) {
            default:
                break;
        }
        //return true and will not deal with short click event
        return true;
    }
}
