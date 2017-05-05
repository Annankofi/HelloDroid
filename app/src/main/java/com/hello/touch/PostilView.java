package com.hello.touch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PostilView extends View {

    private static final int MAX_TOUCHPOINTS = 5;

    private int width;

    private int height;

    private float pX;

    private float pY;
    private Canvas mCacheCanvas;

    private Bitmap mCacheBitmap;

    private Bitmap mBottomBitmap;

    private Paint mBitmapPaint;


    private Path path;

    private List<DrawPath> savePath;

    private List<DrawPath> deletePath;

    private DrawPath drawPath;


    private Paint paint;

    private int paintWidth = 4;

    private int paintColor=Color.RED;
    private boolean isEraser = false;

    private float earserWidth = 30f;

    private boolean isDrawCurIndicator = false;

    private final Rect mInvalidRect = new Rect();

    private Rect lastRect = new Rect();

    private int mImageWidth = 480;
    private int mImageHeight = 800;


    public PostilView(Context context) {
        super(context);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        mCacheBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCacheCanvas = new Canvas(mCacheBitmap);
    }

    private void init() {
        paint = new Paint(Paint.DITHER_FLAG);
        savePath = new ArrayList<>();
        deletePath = new ArrayList<>();
        updateToPaint();
    }

    public void updateToPaint() {
        isEraser = false;
        Paint paint = new Paint();
//        mBitmapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
        paint.setStrokeWidth(paintWidth);
        paint.setColor(paintColor);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        mBitmapPaint=paint;
    }

    public void updateToMarkerPaint() {
        isEraser = false;
        Paint paint = new Paint();
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
        paint.setStrokeWidth(paintWidth);
        paint.setColor(paintColor);
        paint.setAlpha(80);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setStyle(Paint.Style.STROKE);
        mBitmapPaint=paint;
    }

    public void updateToEraser() {
        isEraser = true;
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        paint.setStrokeWidth(earserWidth);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        mBitmapPaint=paint;
    }

    public void setBitmapColor(int color) {
        mCacheBitmap.eraseColor(color);
    }

    public boolean setBitmap(String imagePath)
    {
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float nxScale = -1;
        float nyScale = -1;
        if( width!=0 && height!=0)
        {
            nxScale = (float)width/mImageWidth;
            nyScale = (float)height/mImageHeight;
            if (nxScale>=1 && nyScale >=1 || nxScale<1 && nyScale<1)
            {
                if(nxScale > nyScale)
                {
                    width = (int)(width/nxScale);
                    height = (int)(height/nxScale);
                }
                else
                {
                    width = (int)(width/nyScale);
                    height = (int)(height/nyScale);
                }

            }
            if (nxScale >=1 && nyScale <1)
            {
                width = mImageWidth;
            }
            if(nxScale <=1 && nyScale >=1)
            {
                height = mImageHeight;
            }
            mCacheBitmap = Bitmap.createScaledBitmap(bitmap,width,height,true);
            mBottomBitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
            savePath.clear();
            deletePath.clear();
            mCacheCanvas.setBitmap(mBottomBitmap);
            mCacheCanvas.drawBitmap(mCacheBitmap,0,0,mBitmapPaint);
            postInvalidate();

            return true;
        }
        else
            return false;

    }

    public void setPenColor(int color) {
        paintColor = color;
        mBitmapPaint.setColor(paintColor);
    }

    public void setPenWidth(Paint paint) {
        paintWidth= (int) paint.getStrokeWidth();
        mBitmapPaint.setStrokeWidth(paintWidth);
    }

    public int getPenWidth() {
        return (int) mBitmapPaint.getStrokeWidth();
    }

    public int getPenColor() {
        return paintColor;
    }

    private class DrawPath {
        Path mPath;
        Paint mPaint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mCacheBitmap, 0, 0, paint);
        if (path != null) {
            canvas.drawPath(path,mBitmapPaint);
        }
        if (isEraser && isDrawCurIndicator) {
            DrawCurIndicator(canvas, pX, pY);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isDrawCurIndicator = true;
                path = new Path();
                drawPath = new DrawPath();
                path.moveTo(event.getX(), event.getY());
                drawPath.mPath = path;
                drawPath.mPaint = new Paint(mBitmapPaint);
                pX = event.getX();
                pY = event.getY();
                mInvalidRect.set((int) pX, (int) pY, (int) pX, (int) pY);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                isDrawCurIndicator = true;
                Rect areaToRefresh;
                areaToRefresh = mInvalidRect;
                areaToRefresh.set((int) (pX - paintWidth), (int) (pY - paintWidth),
                        (int) (pX + paintWidth), (int) (pY + paintWidth));

                final float dx = Math.abs(event.getX() - pX);
                final float dy = Math.abs(event.getY() - pY);

                //两点之间的距离大于等于3时，生成贝塞尔绘制曲线
                if (dx >= 3 || dy >= 3) {

                    //设置贝塞尔曲线的操作点为起点和终点的一半
                    float cX = (event.getX() + pX) / 2;
                    float cY = (event.getY() + pY) / 2;
                    path.quadTo(pX, pY, cX, cY);
                    areaToRefresh.union((int) (cX - paintWidth), (int) (cY - paintWidth),
                            (int) (cX + paintWidth), (int) (cY + paintWidth));
                    pX = event.getX();
                    pY = event.getY();
                    Rect tempRect = new Rect(mInvalidRect);
                    mInvalidRect.union(lastRect);
                    //使用局部刷新
                    if (isEraser && isDrawCurIndicator) {
                        invalidate();
                    } else {
                        invalidate(areaToRefresh);
                    }
                    lastRect.set(tempRect);
                }
                break;
            case MotionEvent.ACTION_UP:
                isDrawCurIndicator = false;
                path.lineTo(pX, pY);
                savePath.add(drawPath);
                mCacheCanvas.drawPath(path, mBitmapPaint);
                path = null;
                invalidate();
                break;
        }
        return true;
    }

    public void redraw() {
//        int width = mCacheCanvas.getWidth();
//        int height = mCacheCanvas.getHeight();
//        mCacheBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        mCacheCanvas.setBitmap(mCacheBitmap);

        Iterator<DrawPath> it = savePath.iterator();
        DrawPath temp;
        while (it.hasNext()) {
            temp = it.next();
            mCacheCanvas.drawPath(temp.mPath, temp.mPaint);
        }
        invalidate();
    }

    public void undo() {
        int savePathSize = savePath.size();
        if (savePath != null && savePath.size() > 0) {
            deletePath.add(0, savePath.get(savePathSize - 1));
            savePath.remove(savePathSize - 1);
        } else return;

        clearScreen();
        redraw();
    }

    public void redo() {
        int deletePathSize = deletePath.size();
        if (deletePath != null && deletePathSize > 0) {
            savePath.add(deletePath.get(0));
            deletePath.remove(0);
        }
        clearScreen();
        redraw();
    }

    public void clearScreen() {
        if (mCacheCanvas != null) {
            Paint backPaint = new Paint();
            backPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            mCacheCanvas.drawPaint(backPaint);
        }
        invalidate();
    }

    private void DrawCurIndicator(Canvas canvas, float mx, float my) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GRAY);
        PathEffect effects = new DashPathEffect(new float[]{
                5, 5, 5, 5
        }, 1);
        paint.setPathEffect(effects);
        canvas.drawCircle(mx, my, 30, paint);
    }

}
