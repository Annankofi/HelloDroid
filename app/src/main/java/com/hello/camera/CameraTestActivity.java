package com.hello.camera;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hello.droid.BaseListActivity;
import com.hello.droid.Info;

import java.util.ArrayList;
import java.util.List;

public class CameraTestActivity extends BaseListActivity {
    private static final String TAG = CameraTestActivity.class.getSimpleName();
    Camera mCamera;

    private enum DataItems {
        SUPPORT, AVAILABLE, STARTCAMERA, CAMERA_INFO
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
            case SUPPORT:
                checkCameraHardware(CameraTestActivity.this);
                break;
            case AVAILABLE:
                Log.d(TAG, "getNumberOfCameras=" + Camera.getNumberOfCameras());
                break;
            case STARTCAMERA:
                Intent intent = new Intent(CameraTestActivity.this, CameraActivity.class);
                startActivity(intent);
                break;
            case CAMERA_INFO:
                Camera camera = Camera.open(1);
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                Camera.getCameraInfo(1, cameraInfo);
                Log.d(TAG, "cameraInfo " + cameraInfo);
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


    // Check if this device has a mCamera
    private boolean checkCameraHardware(Context context) {
        //support but sometime unavailable
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Log.i(TAG, "There is a mCamera in the device");
            return true;
        } else {
            // no mCamera on this device
            Log.i(TAG, "There is not any  mCamera in the device");
            return false;
        }
    }
}
