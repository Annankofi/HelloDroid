package com.hello.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class AnnanService extends Service {
    private static final String TAG = AnnanService.class.getSimpleName();
    private List<Client> mClientList = new ArrayList<>();

    private RemoteCallbackList<IToast> mRemoteCallbacks = new RemoteCallbackList<>();

    public AnnanService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(TAG, "onStart");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "onRebind");
        super.onRebind(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d(TAG, "onTaskRemoved");
        super.onTaskRemoved(rootIntent);
    }

    //must override
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return mBinder;
    }

    private static final int MAX_CLIENT = 3;

    private IAnnan.Stub mBinder = new IAnnan.Stub() {

        @Override
        public boolean join(IToast token, final String name) throws RemoteException {
            Log.d(TAG, "join: " + name + " before add mClient:" + mClientList.size());
            Client client = new Client(token, name);

            if (name == null || name.isEmpty() || mClientList.contains(token) || mClientList.size() >= MAX_CLIENT) {
                return false;
            }
            mClientList.add(client);
            token.asBinder().linkToDeath(client,0);
            //notify all
            notifyJoinOrLeave(name,true);
            return true;
        }

        @Override
        public boolean leave(IToast token) throws RemoteException {
            if (token == null || !mClientList.contains(token)) {
                return false;
            }
            Log.d(TAG, "leave and remove: " + getClientName(token));
            mClientList.remove(token);
            return true;
        }

        @Override
        public void registerCallback(IToast token) throws RemoteException {
            mRemoteCallbacks.register(token);
        }

        @Override
        public void unregisterCallback(IToast token) throws RemoteException {
            mRemoteCallbacks.unregister(token);
        }
    };
    private void notifyJoinOrLeave(String name, boolean joinOrLeave) {
        final int size = mRemoteCallbacks.beginBroadcast();
        for (int i = 0; i < size; i++) {
            try {
                mRemoteCallbacks.getBroadcastItem(i).toast(name, joinOrLeave);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        mRemoteCallbacks.finishBroadcast();
    }

    private String getClientName(IToast token) {
        if (token == null) {
            return "";
        }
        for (Client client : mClientList) {
            if (client.token.equals(token)) {
                return client.name;
            }
        }
        return "";
    }

    class Client implements IBinder.DeathRecipient {
        //the only Id
        private IToast token;
        private String name;

        public Client(IToast token, String name) {
            this.token = token;
            this.name = name;
        }

        @Override
        public void binderDied() {
            Log.d(TAG, "binderDied: ");
            if (mClientList.contains(this)) {
                Log.d(TAG, "binderDied and remove:" + getClientName(token));
                mClientList.remove(this);
            }
        }
    }
}
