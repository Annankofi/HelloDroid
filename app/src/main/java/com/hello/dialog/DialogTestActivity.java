package com.hello.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hello.droid.BaseListActivity;
import com.hello.droid.Info;
import com.hello.droid.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DialogTestActivity extends BaseListActivity {
    private static final String TAG = DialogTestActivity.class.getSimpleName();
    private static final int DIALOG_ALERT_DIALOG_ID = 0;
    private static final int DIALOG_PROGRESS_DIALOG_ID = 1;
    private AlertDialog mAlertDialog;

    private enum DataItems {
        DIALOG, ALERT_DIALOG, SIMPLE_ALERTDIALOG
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
            case DIALOG:
                break;
            case ALERT_DIALOG:
                showDialog(DIALOG_ALERT_DIALOG_ID);
                break;
            case SIMPLE_ALERTDIALOG:
                showSimpleAlertDialog();
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


    @Override
    protected Dialog onCreateDialog(int id) {
        Log.d(TAG, "onCreateDialog");
        AlertDialog.Builder builder = null;
        builder = new AlertDialog.Builder(this);
        switch (id) {
            case DIALOG_ALERT_DIALOG_ID:
                builder.setTitle("Hello");
                builder.setMessage("Hello");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        allowDismissOnClick(dialog, false);
                        // finish();
                        try {
                            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                            field.setAccessible(true);
                            field.set(dialog, false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                mAlertDialog = builder.create();
                return mAlertDialog;
            // dialog.show();
            // return null;
            default:
                break;
        }
        return null;
        // return super.onCreateDialog(id);
    }


    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        // TODO Auto-generated method stub
        Log.d(TAG, "onPrepareDialog");
        switch (id) {
            case DIALOG_ALERT_DIALOG_ID:
                dialog.setTitle("Hello");
            default:
                break;
        }

        super.onPrepareDialog(id, dialog);
    }

    private void showSimpleAlertDialog() {
        SimpleAlertDialog alertDialog = new SimpleAlertDialog(this);
        alertDialog.setMessage("Hello SimpleAlertDialog");
        alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                allowDismissOnClick(dialog, true);

            }
        });

        alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                allowDismissOnClick(dialog, false);
            }
        });
        alertDialog.show();
    }

    /**
     * @param dialog
     * @param allow  false: when you click the positive/negative buttom the dialog
     *               will not dismiss auto so you will add in
     */
    private void allowDismissOnClick(DialogInterface dialog, boolean allow) {
        try {
            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(dialog, allow);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
