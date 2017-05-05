package com.hello.camera;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.List;


public class CameraView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "WaveView";

    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private Context mContext;
    private int mSvWidth;
    private int mSvHeight;
    private Paint mPaint;
    private int mCameraId;

    //
    Camera camera;
    private boolean isExceptionToken = false;

    // Must override the constructor
    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mHolder = getHolder();
        mHolder.addCallback(this);
        // set it transparent
        setZOrderOnTop(true);
        mHolder.setFormat(PixelFormat.TRANSLUCENT);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(0xff08afa5);
        mPaint.setStyle(Style.FILL);
        mPaint.setAlpha(100);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated");
        mSvWidth = this.getWidth();
        mSvHeight = this.getHeight();
        drawErrorZone();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed");
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    void drawErrorZone() {
        try {
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setTextSize(40);
            String msg1 = "Check Camera";
            String msg2 = "Touch Me To Test It Again!";
            float msg1Len = paint.measureText(msg1);
            float msg2Len = paint.measureText(msg2);
            mCanvas = mHolder.lockCanvas();
            // clear the mCanvas every time
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            mCanvas.drawText(msg1, mSvWidth / 2 - ((px2dip(mContext, msg1Len)) / 2), mSvHeight / 2, paint);
            mCanvas.drawText(msg2, mSvWidth / 2 - ((px2dip(mContext, msg2Len)) / 2), mSvHeight / 2 + 50, paint);
            mHolder.unlockCanvasAndPost(mCanvas);
        } catch (Exception e) {
            Log.e(TAG, "DrawSine failed, exception:" + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    public int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getX() > 200 && event.getX() < 800
                && event.getY() > 300 && event.getY() < 600) {
            Toast.makeText(mContext, "Hello", Toast.LENGTH_SHORT).show();
            startCamera();
        } else {
            Toast.makeText(mContext, "Hello", Toast.LENGTH_SHORT).show();
        }
        return super.onTouchEvent(event);
    }

    public void startCamera() {
        try {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(0, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                Log.i(TAG, "get a facing_back mCamera");
            } else if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                Log.i(TAG, "get a facing_front mCamera");
            }
            if (camera == null) {
                camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
                if (camera == null) {
                    Log.e(TAG, "open Camera failed");
                    return;
                }
            }
            Camera.Parameters param = camera.getParameters();
            List<Camera.Size> supportedSize = param.getSupportedPreviewSizes();
            int count = supportedSize.size();
            for (int i = 0; i < count; i++) {
                if (supportedSize.get(i).width == 1280) {
                    Log.i(TAG, "setPreviewSize " + supportedSize.get(i).width + "*" + supportedSize.get(i).height);
                    param.setPreviewSize(supportedSize.get(i).width, supportedSize.get(i).height);
                    break;
                } else if (supportedSize.get(i).width == 800) {
                    Log.i(TAG, "setPreviewSize " + supportedSize.get(i).width + "*" + supportedSize.get(i).height);
                    param.setPreviewSize(supportedSize.get(i).width, supportedSize.get(i).height);
                    break;
                } else if (supportedSize.get(i).width == 720) {
                    Log.i(TAG, "setPreviewSize " + supportedSize.get(i).width + "*" + supportedSize.get(i).height);
                    param.setPreviewSize(supportedSize.get(i).width, supportedSize.get(i).height);
                    break;
                }
            }
            String info = param.flatten();
            Log.i("Info", info);
            param.setJpegQuality(80);
            param.setPreviewFrameRate(4);
            camera.setParameters(param);
            camera.setPreviewDisplay(mHolder);
            camera.startPreview();
        } catch (Exception e) {
            isExceptionToken = true;
            Log.d(TAG, "open mCamera fail.");
            e.printStackTrace();
            drawErrorZone();
        }
        if (!isExceptionToken) {
            Log.d(TAG, "open mCamera succes");
        }
    }
}