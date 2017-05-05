package com.hello.system;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hello.droid.BaseListActivity;
import com.hello.droid.Info;

import java.util.ArrayList;
import java.util.List;

public class SystemTestActivity extends BaseListActivity {
    private static final String TAG = SystemTestActivity.class.getSimpleName();

    private PowerManager.WakeLock mWakeLock;
    private PowerManager mPowerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPowerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
    }

    private enum DataItems {
        WAKELOCK_ACQUIRE, WAKELOCK_RELEASE, GOTOSLEEP,QUERY_ALLOCK_POWER_SAVING,ADJUST_SIZE,ADJUST_PAN
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
            case WAKELOCK_ACQUIRE:
                mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, this.getClass().getCanonicalName());
                mWakeLock.acquire();
                break;
            case WAKELOCK_RELEASE:
                if (mWakeLock != null) {
                    mWakeLock.release();
                    mWakeLock = null;
                }
                break;
            case GOTOSLEEP:
               //     mPowerManager.goToSleep(SystemClock.uptimeMillis());
                break;
            case QUERY_ALLOCK_POWER_SAVING:
               // mPowerManager.goToSleep(SystemClock.uptimeMillis());
                 //   mPowerManager.goToSleep(SystemClock.uptimeMillis());
                break;
            case ADJUST_SIZE:
                Intent intent1 = new Intent(SystemTestActivity.this, AdjustSizeActivity.class);
                startActivity(intent1);
                //   mPowerManager.goToSleep(SystemClock.uptimeMillis());
                break;
            case ADJUST_PAN:
               // Intent intent2 = new Intent(SystemTestActivity.this, AdjustPanActivity.class);
                //startActivity(intent2);
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
