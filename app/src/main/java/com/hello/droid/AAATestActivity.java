package com.hello.droid;

import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class AAATestActivity extends BaseListActivity {
    private static final String TAG = AAATestActivity.class.getSimpleName();

    private enum DataItems {
        COMMON
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

        switch (DataItems.valueOf(mHeaderAdapter.getData().get(position).getTitle())) {
            case COMMON:
                break;
            default:
                break;
        }

        super.onListItemClick(listView, v, position, id);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemLongClick");
        switch (DataItems.valueOf(mHeaderAdapter.getData().get(position).getTitle())) {
            default:
                break;
        }
        //return true and will not deal with short click event
        return true;
    }

    @Override
    protected void onMessage(Message msg) {
        super.onMessage(msg);
        switch (msg.what) {
            case 1:
                Log.d(TAG, "onMessage: 1");
                break;
            case 2:
                Log.d(TAG, "onMessage: 2");
                break;
            case 3:

                break;
            case 4:
                break;
            case 5:

                break;
            case 6:
                break;
            case 7:

                break;
            case 8:
                break;
            default:
                break;
        }
    }
}
