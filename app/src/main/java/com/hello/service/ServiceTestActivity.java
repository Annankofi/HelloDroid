package com.hello.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hello.droid.BaseListActivity;
import com.hello.droid.Info;

import java.util.ArrayList;
import java.util.List;


public class ServiceTestActivity extends BaseListActivity {
    private static final String TAG = ServiceTestActivity.class.getSimpleName();

    private enum DataItems {
        BIND_ANNAN_SERVICE, UNBIND_ANNAN_SERVICE_1, BIND_ANNAN_SERVICE_2, UNBIND_ANNAN_SERVICE_2, TEST_ANNAN_SERVICE, EXCEPTION
    }

    private IAnnan mService;
    private int mCount = 0;
    private IToast.Stub mToken1 = new IToast.Stub() {
        @Override
        public void toast(String name, boolean joinOrLeave) throws RemoteException {
            Log.d(TAG, "toast1 name: " + name + " join:" + joinOrLeave);
        }
    };
    private IToast.Stub mToken2 = new IToast.Stub() {
        @Override
        public void toast(String name, boolean joinOrLeave) throws RemoteException {
            Log.d(TAG, "toast2 name: " + name + " join:" + joinOrLeave);
        }
    };

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
        Log.d(TAG, "onItemLongClick " + mHeaderAdapter.getData().get(position).getTitle());
        switch (DataItems.valueOf(mHeaderAdapter.getData().get(position).getTitle())) {
            case BIND_ANNAN_SERVICE:
                Intent intent = new Intent("com.hello.service.ACTION.ANNAN_SERVICE");
                //Service Intent must be explicit in android5.0 Need to set the package
                intent.setPackage("com.hello.service");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                bindService(intent, mAnnanServiceConnection1, Context.BIND_AUTO_CREATE);
                break;

            case TEST_ANNAN_SERVICE:
                if (mService != null) {
                    try {
                        String clientName = "Annan" + mCount;
                        boolean result = mService.join(mToken1, clientName);
                        mService.registerCallback(mToken1);
                        Log.d(TAG, "onListItemClick: join " + clientName + " " + result);
                        mCount++;
                        clientName = "Annan" + mCount;
                        result = mService.join(mToken2, clientName);
                        mService.registerCallback(mToken2);
                        Log.d(TAG, "onListItemClick: join " + clientName + " " + result);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case EXCEPTION:
                throw new IllegalArgumentException("Hello");
            default:
                break;
        }

        super.onListItemClick(listView, v, position, id);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemLongClick " + mHeaderAdapter.getData().get(position).getTitle());
        switch (DataItems.valueOf(mHeaderAdapter.getData().get(position).getTitle())) {
            case TEST_ANNAN_SERVICE:
                if (mService != null) {
                    try {
                        boolean result = mService.leave(mToken1);
                        mService.unregisterCallback(mToken1);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            default:
                break;
        }
        //return true and will not deal with short click event
        return true;
    }


    private ServiceConnection mAnnanServiceConnection1 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //onServiceConnected ComponentInfo{com.hello.service/com.hello.service.AnnanService}
            Log.d(TAG, "onServiceConnected " + name);
            mService = IAnnan.Stub.asInterface(service);
            try {
                mService.asBinder().linkToDeath(mDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            //to reconnect here
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected " + name);
            mService.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mService = null;
        }
    };

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.d(TAG, "binderDied");
        }
    };
}
