package com.hello.dialog;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.view.Gravity;
import android.widget.TextView;

public class SimpleAlertDialog {
    private Context mContext;
    private Builder b;
    private AlertDialog mDialog;
    private int mWindowType = -1;

    public SimpleAlertDialog(Context context) {
        mContext = context;
        b = new Builder(mContext);
    }

    public SimpleAlertDialog setMessage(String text) {
        b.setMessage(text);
        return this;
    }

    public SimpleAlertDialog setPositiveButton(String text, OnClickListener lsnr) {
        b.setPositiveButton(text, lsnr);
        return this;
    }

    public SimpleAlertDialog setNegativeButton(String text, OnClickListener lsnr) {
        b.setNegativeButton(text, lsnr);
        return this;
    }

    public SimpleAlertDialog setNeutralButton(String text, OnClickListener lsnr) {
        b.setNeutralButton(text, lsnr);
        return this;
    }

    public SimpleAlertDialog setOnDismissListener(
            android.content.DialogInterface.OnDismissListener lsnr) {
        if (lsnr != null) {
            b.setOnDismissListener(lsnr);
        }
        return this;
    }

    public SimpleAlertDialog setOnKeyListener(OnKeyListener lsnr) {
        if (lsnr != null) {
            b.setOnKeyListener(lsnr);
        }
        return this;
    }

    public SimpleAlertDialog setCanceled(boolean cancelable) {
        b.setCancelable(cancelable);
        return this;
    }

    public void show() {
        if (mDialog != null && mDialog.isShowing()) {
            return;
        }

        mDialog = b.create();
        if(mWindowType != -1){
            mDialog.getWindow().setType(mWindowType);
        }
        mDialog.show();

        TextView tv = (TextView) mDialog.getWindow().findViewById(android.R.id.message);
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(14, 46, 14, 36);
    }
    public void setWindowType(int type){
        mWindowType = type;
    }

    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }
}
