package com.hello.camera;


import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.util.Log;

public class CameraDetector extends CameraManager.AvailabilityCallback {
    static private final String TAG = CameraDetector.class.getSimpleName();
    static CameraDetector mInstance;
    Context mContext;
    CameraManager mManager;
    Handler mHandler = new Handler();


    public CameraDetector(Context context) {
        mContext = context;
        //get the camera manager
        mManager = (CameraManager) mContext.getSystemService(Context.CAMERA_SERVICE);
        mManager.registerAvailabilityCallback(this, mHandler);
    }

    public void getCameraIdList() {
        boolean bCameraAvailable = false;
        try {
            if (mManager != null) {
                String[] cameraList = mManager.getCameraIdList();
                //set it if there is a available camera
                bCameraAvailable = (cameraList.length != 0);
                Log.d(TAG, "mCamera number - " + cameraList.length);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCameraAvailable(String cameraId) {
        super.onCameraAvailable(cameraId);
        Log.d(TAG, "onCameraAvailable " + cameraId);
//		logic.SetCameraAvailable(true);
    }

    @Override
    public void onCameraUnavailable(String cameraId) {
        super.onCameraUnavailable(cameraId);
        Log.d(TAG, "onCameraUnavailable " + cameraId);
//		logic.SetCameraAvailable(false);
    }
}
