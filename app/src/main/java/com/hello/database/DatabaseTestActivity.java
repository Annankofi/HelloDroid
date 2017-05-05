package com.hello.database;

import android.content.ContentValues;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hello.droid.BaseListActivity;
import com.hello.droid.Info;

import java.util.ArrayList;
import java.util.List;

public class DatabaseTestActivity extends BaseListActivity {
    private static final String TAG = DatabaseTestActivity.class.getSimpleName();

    private enum DataItems {
        INSERT, DELETE, DELETEALL, UPDATE, QUERY
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
            case INSERT:
                ContentValues values = new ContentValues();
                values.put(MusicTable.Columns.PATH, "path1");
                values.put(MusicTable.Columns.DURATION, 100);
                getContentResolver().insert(MusicTable.Columns.CONTENT_ID_URI_BASE, values);

                break;
            case DELETE:
                String args[] = {
                        "path1"
                };
                getContentResolver().delete(MusicTable.Columns.CONTENT_URI, MusicTable.Columns.PATH + "=?",
                        args);
                break;
            case DELETEALL:

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
