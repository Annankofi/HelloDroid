package com.hello.droid;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HeaderAdapter extends BaseAdapter {
    private static final String TAG = HeaderAdapter.class.getSimpleName();
    private LayoutInflater mInflater;
    private Context mContext;
    private List<Info> mDatas = new ArrayList<>();

    public HeaderAdapter(Context context, List<Info> datas) {
        super();
        mContext = context;
        this.mDatas = datas;
        mInflater = (LayoutInflater) (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<Info> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    public List<Info> getData() {
        return mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int arg0) {
        return mDatas.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.header_list_item, null);
            holder = new ViewHolder();
            holder.mTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.mSummary = (TextView) convertView.findViewById(R.id.tv_summary);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTitle.setText(mDatas.get(position).getTitle());
        String summary = mDatas.get(position).getSummary();
        Log.d(TAG, "getView and summary " + summary);
        if (summary == null || summary.length() == 0) {
            holder.mSummary.setVisibility(View.GONE);
        } else {
            holder.mSummary.setVisibility(View.VISIBLE);
            holder.mSummary.setText(mDatas.get(position).getSummary());
        }
        return convertView;
    }

    public static class ViewHolder {
        public TextView mTitle;
        public TextView mSummary;
    }
}
