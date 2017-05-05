package com.hello.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiConfiguration;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hello.droid.BaseListActivity;
import com.hello.droid.Info;

import java.util.ArrayList;
import java.util.List;


public class WifiTestActivity extends BaseListActivity {
    private static final String TAG = WifiTestActivity.class.getSimpleName();
    public static final String CONFIGURED_NETWORKS_CHANGED_ACTION =
            "android.net.wifi.CONFIGURED_NETWORKS_CHANGE";
    public static final String EXTRA_MULTIPLE_NETWORKS_CHANGED = "multipleChanges";
    public static final String EXTRA_WIFI_CONFIGURATION = "wifiConfiguration";
    public static final String EXTRA_CHANGE_REASON = "changeReason";
    /**
     * The configuration is new and was added.
     * @hide
     */
    public static final int CHANGE_REASON_ADDED = 0;
    /**
     * The configuration was removed and is no longer present in the system's list of
     * configured networks.
     * @hide
     */
    public static final int CHANGE_REASON_REMOVED = 1;
    /**
     * The configuration has changed as a result of explicit action or because the system
     * took an automated action such as disabling a malfunctioning configuration.
     * @hide
     */
    public static final int CHANGE_REASON_CONFIG_CHANGE = 2;
    private IntentFilter mFilter;
    private enum DataItems {
        REGISTER, UNREGISTER
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
            case REGISTER:
                Log.d(TAG,"Register wifi broadcastReciever");
                mFilter = new IntentFilter();//添加过滤器
//                mFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
//                mFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
//                mFilter.addAction(WifiManager.NETWORK_IDS_CHANGED_ACTION);
//                mFilter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
                mFilter.addAction(/*WifiManager.*/CONFIGURED_NETWORKS_CHANGED_ACTION);
//                mFilter.addAction(WifiManager.LINK_CONFIGURATION_CHANGED_ACTION);
//                mFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
//                mFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
                registerReceiver(mReceiver, mFilter);
                break;
            case UNREGISTER:
                unregisterReceiver(mReceiver);
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

    BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override

        public void onReceive(Context context, Intent intent) {
            Log.d(TAG,"onReceive:"+intent);
            if (intent.getAction().equals(CONFIGURED_NETWORKS_CHANGED_ACTION)){
               boolean multipleNetChnaged = intent.getBooleanExtra(EXTRA_MULTIPLE_NETWORKS_CHANGED,false);
                Log.d(TAG, "multipleChanged:" + multipleNetChnaged);
                if (!multipleNetChnaged) {
                    int   reason = intent.getIntExtra(EXTRA_CHANGE_REASON,-1);
                    WifiConfiguration   configuration = (WifiConfiguration) intent.getParcelableExtra(EXTRA_WIFI_CONFIGURATION);
                    String psk= intent.getStringExtra("psk");
                    Log.d(TAG, "reason:" + reason + " psk:" +psk);
                    Log.d(TAG, "configuration:" + configuration);
                }
            }
//            intent.putExtra(WifiManager.EXTRA_MULTIPLE_NETWORKS_CHANGED, false);
//            intent.putExtra(WifiManager.EXTRA_WIFI_CONFIGURATION, network);
//            intent.putExtra(WifiManager.EXTRA_CHANGE_REASON, reason);


        }

    };
}
