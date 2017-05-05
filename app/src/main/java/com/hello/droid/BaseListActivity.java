package com.hello.droid;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.AdapterView;

import java.util.List;

public abstract class BaseListActivity extends ListActivity implements AdapterView.OnItemLongClickListener {
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
}
