package com.hello.droid;

import android.os.Bundle;
import android.widget.AdapterView;

import java.util.List;

public abstract class BaseListActivity extends BroadcastActivity implements AdapterView.OnItemLongClickListener {
    private static final String TAG = BaseListActivity.class.getSimpleName();
    protected HeaderAdapter mHeaderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHeaderAdapter = new HeaderAdapter(this, buildDatas());
        getListView().setAdapter(mHeaderAdapter);
        getListView().setOnItemLongClickListener(this);
    }

    protected abstract List<Info> buildDatas();

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcast(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterBroadcast(this);
    }
}
