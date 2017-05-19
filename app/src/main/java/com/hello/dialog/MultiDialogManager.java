package com.hello.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Message;
import android.util.ArrayMap;
import android.util.Log;

import com.hello.droid.BroadcastActivity;

import java.util.Map;


public class MultiDialogManager {
    private static final String TAG = MultiDialogManager.class.getSimpleName();
    private Context mContext;

    private enum NoticeType {HELLO_WORLD, HELLO_DROID}
    private enum NoticeState{SHOW,DISMISS}

    private Map<String, SimpleAlertDialog> mNoticeDialogs;

    public MultiDialogManager(Context context) {
        mContext = context;
        mNoticeDialogs = new ArrayMap<>();
    }


    public void onMessage(Message msg) {
            if (getState(msg).equals(NoticeState.DISMISS)) {
                dismissNoticeDialog(getNoticeType(msg));
            } else  if (getState(msg).equals(NoticeState.SHOW)) {
                createNoticeDialog(getNoticeType(msg), getState(msg));
            }
    }



    private NoticeType getNoticeType(Message msg) {
        NoticeType type = NoticeType.HELLO_DROID;
        switch (msg.what) {
            case BroadcastActivity.MSG_MULTI_DIALOG_HELLO_DROID:
                type = NoticeType.HELLO_DROID;
                break;
            case BroadcastActivity.MSG_MULTI_DIALOG_HELLO_WORLD:
                type = NoticeType.HELLO_WORLD;
                break;
            default:
                break;
        }
        return type;
    }

    private NoticeState getState(Message msg) {
        NoticeState state;
        if (msg.arg1 == 0) {
            state = NoticeState.DISMISS;
        } else {
            state = NoticeState.SHOW;
        }
        return state;
    }

    private String getNoticeTip(NoticeType noticeType, NoticeState state) {
        String tip = null;
        if (noticeType.equals(NoticeType.HELLO_WORLD)) {
            if (state.equals(NoticeState.SHOW)) {
                tip = "Hello world!";
            }
        } else if (noticeType.equals(NoticeType.HELLO_DROID)) {
            if (state.equals(NoticeState.SHOW)) {
                tip = "Hello Droid!";
            }
        }

        if (tip == null) {
            throw new IllegalStateException("No update tip Found.");
        }
        return tip;
    }

    /**
     * show update dialog which can not be dismiss by user
     */
    private synchronized boolean createNoticeDialog(final NoticeType noticeType, NoticeState state) {
        if (mContext == null) {
            Log.e(TAG, "createNoticeDialog failed because context is null");
            return false;
        }
        SimpleAlertDialog noticeDialog = mNoticeDialogs.get(noticeType.name());
        if (noticeDialog != null) {//FIXME 这里没有showing方法所以保证每次消失都会从list移除
            Log.i(TAG, "createNoticeDialog fail  for " + noticeType.name() + " which is showing.");
            return false;
        }
        noticeDialog = new SimpleAlertDialog(mContext);
        noticeDialog.setMessage(getNoticeTip(noticeType, state));
        noticeDialog.setPositiveButton("Okay",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismissNoticeDialog(noticeType);
                    }
                });
        noticeDialog.setCanceled(false);
        //    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
        //permission denied for this window type
        //noticeDialog.setWindowType(WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG);
        noticeDialog.show();
        noticeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Log.d(TAG, "onDismiss");
            }
        });
        //put the dialog into dialog array maps
        Log.d(TAG, "createNoticeDialog success and put it into mNoticeDialogs");
        mNoticeDialogs.put(noticeType.name(), noticeDialog);
        return true;
    }


    /**
     * close the update dialog after update finish
     */
    private synchronized void dismissNoticeDialog(NoticeType noticeType) {
        SimpleAlertDialog noticeDialog;
        noticeDialog = mNoticeDialogs.get(noticeType.name());
        if (noticeDialog != null) {
            Log.d(TAG, "dismissNoticeDialog and  removed from mUpdateDialog");
            mNoticeDialogs.remove(noticeType.name());
            noticeDialog.dismiss();
        }
    }

    private synchronized void dismissAllNoticeDialog() {
        for (Map.Entry<String, SimpleAlertDialog> entry : mNoticeDialogs.entrySet()) {
            SimpleAlertDialog noticeDialog = entry.getValue();
            Log.d(TAG, "dismissNoticeDialog: " + entry.getKey());
            if (noticeDialog != null) {
                noticeDialog.dismiss();
            }
        }
        mNoticeDialogs.clear();
    }
}
