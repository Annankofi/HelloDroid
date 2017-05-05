package com.hello.touch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class BlockTouchView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = BlockTouchView.class.getSimpleName();

    private SurfaceHolder mHolder;
    private Paint mBlockPaint;
    private Paint mWirePaint;
    private int mSurfaceWidth;
    private int mSurfaceHeight;
    private static final int FORWARE_TOP = 1;
    private static final int FORWARE_BOTTOM = 2;
    private static final int FORWARE_LEFT = 3;
    private static final int FORWARE_RIGHT = 4;

    private List<Rect> mBlocks;
    private List<Points> mPoints = new ArrayList<>();

    public BlockTouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public BlockTouchView(Context context) {
        super(context);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        mHolder = getHolder();
        mHolder.addCallback(this);
        // set it transparent
        setZOrderOnTop(true);
        mHolder.setFormat(PixelFormat.TRANSLUCENT);
        initPaint();
    }

    private void initPaint() {
        mWirePaint = new Paint();
        mWirePaint.setAntiAlias(true);
        mWirePaint.setColor(Color.RED);
        mWirePaint.setAlpha(100);
        mWirePaint.setStrokeWidth(6);
        //important that if will not fill 默认会填充环形区域
        mWirePaint.setStyle(Paint.Style.STROKE);
        // mWirePaint.setStyle(Paint.Style.FILL);
        mWirePaint.setDither(true);
        mWirePaint.setStrokeJoin(Paint.Join.ROUND);
        mWirePaint.setStrokeCap(Paint.Cap.ROUND);

        mBlockPaint = new Paint();
        mBlockPaint.setAntiAlias(true);
        mBlockPaint.setColor(Color.GREEN);
        mBlockPaint.setStrokeWidth(6);
        mBlockPaint.setStyle(Paint.Style.FILL);
        mBlockPaint.setAlpha(100);
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
//        Timer timer = new Timer();
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                mSurfaceRect = mHolder.getSurfaceFrame();
//                draw();
//            }
//        };
//        timer.schedule(timerTask, 500);
        mSurfaceWidth = getWidth();
        mSurfaceHeight = getHeight();
        Log.d(TAG, "mSurfaceWidth " + mSurfaceWidth + " mSurfaceHeight:" + mSurfaceHeight);

        initBlocks();
        draw();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerCount;
        int historySize;
        Log.d(TAG, "onTouchEvent actionIndex:" + event.getActionIndex() + " id:" + event.getPointerId(event.getActionIndex()));
        Log.d(TAG,"onTouchEvent("+event.getX()+","+event.getY()+")");
        Log.d(TAG,"onTouchEvent0("+event.getX(0)+","+event.getY(0)+")");
        pointerCount = event.getPointerCount();
        historySize = event.getHistorySize();
        Log.d(TAG,"onTouchEvent pointerCount="+pointerCount+" historySize="+historySize);
        //目前屏幕上点数 那么index肯定是从0开始排到pointerCount-1 可以通过index找出对应的pointerId
        for(int i=0;i<pointerCount;i++){
            Log.d(TAG,"onTouchEvent (index,id) = ("+i+","+event.getPointerId(i)+")"+"  ("+event.getX(i)+","+event.getY(i)+")");
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG,"onTouchEvent  ACTION_DOWN");
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.d(TAG,"onTouchEvent  ACTION_POINTER_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG,"onTouchEvent  ACTION_MOVE");
                Log.d(TAG, "onTouchEvent" + event.getActionIndex() + " id:" + event.getPointerId(event.getActionIndex()));
                Log.d(TAG,"onTouchEvent("+event.getX()+","+event.getY()+")");
                //处理未上报的点 history只有move事件才有
                pointerCount = event.getPointerCount();
                historySize = event.getHistorySize();
                Log.d(TAG,"pointerCount="+pointerCount+" historySize="+historySize);
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG,"onTouchEvent  ACTION_UP");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.d(TAG,"onTouchEvent  ACTION_POINTER_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG,"onTouchEvent  ACTION_CANCEL");
                Log.d(TAG, "onTouchEvent" + event.getActionIndex() + " id:" + event.getPointerId(event.getActionIndex()));
                break;
        }

        switch (event.getActionMasked()/*equal: event.getAction() & MotionEvent.ACTION_MASK*/) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG,"onTouchEvent ACTION_MASK  ACTION_DOWN");
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.d(TAG,"onTouchEvent ACTION_MASK  ACTION_POINTER_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG,"onTouchEvent ACTION_MASK ACTION_MOVE");
                Log.d(TAG, "onTouchEvent" + event.getActionIndex() + " id:" + event.getPointerId(event.getActionIndex()));
                Log.d(TAG,"onTouchEvent("+event.getX()+","+event.getY()+")");
                //处理未上报的点 history只有move事件才有
                pointerCount = event.getPointerCount();
                historySize = event.getHistorySize();
                Log.d(TAG,"pointerCount="+pointerCount+" historySize="+historySize);
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG,"onTouchEvent ACTION_MASK ACTION_UP");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.d(TAG,"onTouchEvent ACTION_MASK ACTION_POINTER_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG,"onTouchEvent ACTION_MASK ACTION_CANCEL");
                Log.d(TAG, "onTouchEvent" + event.getActionIndex() + " id:" + event.getPointerId(event.getActionIndex()));
                break;
        }
        return true;
    }


//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Log.d(TAG,"onTouchEvent "+event.getAction());
//        MultiTouchEvent multiTouchEvent = new MultiTouchEvent(event);
//        int actionIndex = multiTouchEvent.getActionIndex();
//        switch (event.getActionMasked() & MotionEvent.ACTION_MASK) {
//            case MotionEvent.ACTION_DOWN:
//            case MotionEvent.ACTION_POINTER_DOWN:
//                Log.d(TAG, "Down: index:" + event.getActionIndex() + " id:" + event.getPointerId(event.getActionIndex()));
//                mPoints.add(new Points(multiTouchEvent.getTouchEvent(actionIndex)));
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.d(TAG, "Annan1 Move: index:" + event.getActionIndex() + " id:" + event.getPointerId(event.getActionIndex()));
//                Log.d(TAG,"Annan1 onTouchEvent("+event.getX()+","+event.getY()+")");
//
//                //处理未上报的点 history只有move事件才有
//                int count = event.getPointerCount();
//                int historySize = event.getHistorySize();
//                for(int i=0;i<historySize;i++){
//                    for(int j=0;j<count;j++){
//                        isContainInBlocks(event.getHistoricalX(j, i),event.getHistoricalY(j, i));
//                    }
//                }
//                for (Points point : mPoints) {
//                    int id = point.getActionId();
//                    SingleTouchEvent touchEvent = multiTouchEvent.getTouchEventByID(id);
//                    if (touchEvent != null) {
//                        Log.d(TAG,"addPointF "+mPoints.size());
//                        point.addPointF(touchEvent);
//                        isContainInBlocks(touchEvent.x, touchEvent.y);
//                    }
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_POINTER_UP:
//            case MotionEvent.ACTION_CANCEL:
//                Log.d(TAG, "Up: index:" + event.getActionIndex() + " id:" + event.getPointerId(event.getActionIndex()));
//                int actionId = multiTouchEvent.getActionId();
//                for (Points point : mPoints) {
//                    if (point.getActionId() == actionId) {
//                       point.dispose();
//                        //mPoints.remove(point);
//                        break;
//                    }
//                }
//                break;
//        }
//        draw();
//        return true;
//    }

    //如果不加这个会闪动
    public void drawBackground(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        //getSurfaceFrame是否定时获取
        canvas.drawRect(mHolder.getSurfaceFrame(), paint);
    }

    public boolean draw() {
        if (mHolder.getSurface().isValid()) {
            Canvas canvas = mHolder.lockCanvas();
            drawBackground(canvas);
            drawBlocks(canvas);
            for (Points points : mPoints) {
                drawPoints(canvas, points);
            }
            mHolder.unlockCanvasAndPost(canvas);
            return true;
        } else {
            Log.e(TAG, "getSurface is invalid");
        }
        return false;
    }

    public void drawPoints(Canvas canvas, Points points) {
        List<SerializablePointF> pointFs = points.getPointFs();
        Path path = new Path();
        Log.d(TAG,"drawPoints "+pointFs.size());
        for (int i = 0; i < pointFs.size() - 1; i++) {
            SerializablePointF p1 = pointFs.get(i);
            SerializablePointF p2 = pointFs.get(i + 1);
            path.moveTo(p1.x, p1.y);
            final float dx = Math.abs(p2.x - p1.x);
            final float dy = Math.abs(p2.y - p1.y);

            Log.d("TAG", " x1=" + p1.x + " y1=" + p1.y + " x2=" + p2.x + " y2=" + p2.y);
            //两点之间的距离大于等于3时，生成贝塞尔绘制曲线
            if (dx >= 3 || dy >= 3) {
                //设置贝塞尔曲线的操作点为起点和终点的一半
                float cX = (p1.x + p2.x) / 2;
                float cY = (p1.y + p2.y) / 2;
                //path.cubicTo(p1.x, p1.y, cX, cY, p2.x, p2.y);
                 path.lineTo(p2.x, p2.y);

            }
        }
        canvas.drawPath(path, mWirePaint);
    }


    private void initBlocks() {
        Block curBlock;
        List<Block> blocks = new ArrayList<Block>();
        //绘制第二竖条
        curBlock = new Block(2 * Block.HEIGHT, 0);
        blocks.add(curBlock);
        int direction = FORWARE_BOTTOM;
        while (hasNext(curBlock, direction)) {
            curBlock = getNext(curBlock, direction);
            if (!isRectIntersect(blocks, curBlock)) {
                blocks.add(curBlock);
            } else {
                break;
            }
        }
        blocks.remove(blocks.size() - 1);
        blocks.remove(blocks.size() - 1);
        blocks = getBlocks(blocks);
        Log.d(TAG, "blocks size is " + blocks.size());
        for (Block block : blocks) {
           block.debug();
        }

        mBlocks = new ArrayList<Rect>();
        for(int i=0;i<blocks.size();i++){
            mBlocks.add(blocks.get(i).getRect());
            Log.d(TAG,"mBlocks.size "+ mBlocks.size());
        }
    }

    private void drawBlocks(Canvas canvas) {
        if (mBlocks != null) {
            for (Rect block : mBlocks) {
                canvas.drawRect(block, mBlockPaint);
            }
        }
    }

    private List<Block> getBlocks(List<Block> blocks) {
        Block curBlock = new Block(0, 0);
        int direction = FORWARE_BOTTOM;
        blocks.add(curBlock);
        int count = 0;
        while (true) {
            while (hasNext(curBlock, direction)) {
                count++;
                curBlock = getNext(curBlock, direction);
                if (!isRectIntersect(blocks, curBlock)) {
                    blocks.add(curBlock);
                } else {
                    Log.d(TAG, "isRectIntersect is true");
                    blocks.remove(blocks.size() - 1);
                    curBlock = blocks.get(blocks.size() - 1);
                    break;
                }
            }
            direction = changeDirection(direction);

            Log.d(TAG, "count=" + count);
            Block block = getNext(curBlock, direction);
            if (isRectIntersect(blocks, block) || count <= 3) {
                return blocks;
            }
            count = 0;
        }
    }

    private boolean isRectIntersect(List<Block> blocks, Block newBlock) {
        Rect newRect = newBlock.getRect();
        for (Block block : blocks) {
            if (Rect.intersects(block.getRect(), newRect)) {
                return true;
            }
        }
        return false;
    }

    private int changeDirection(int direction) {
        int newDirection = 0;
        switch (direction) {
            case FORWARE_TOP:
                newDirection = FORWARE_LEFT;
                break;

            case FORWARE_BOTTOM:
                newDirection = FORWARE_RIGHT;
                break;

            case FORWARE_LEFT:
                newDirection = FORWARE_BOTTOM;
                break;

            case FORWARE_RIGHT:
                newDirection = FORWARE_TOP;
                break;
        }
        Log.d(TAG, "change direction from " + direction + " to " + newDirection);
        return newDirection;
    }

    private boolean hasNext(Block oldBlock, int direction) {
        boolean hasNext = false;
        int x = oldBlock.getLeft();
        int y = oldBlock.getTop();
        switch (direction) {
            case FORWARE_TOP:
                if ((y - Block.WIDTH - Block.DIVIDER_WIDTH) >= 0) {
                    hasNext = true;
                }
                break;

            case FORWARE_BOTTOM:
                if ((y + 2 * Block.WIDTH + Block.DIVIDER_WIDTH) < mSurfaceHeight) {
                    hasNext = true;
                }
                break;

            case FORWARE_LEFT:
                if ((x - 2 * Block.HEIGHT - Block.DIVIDER_WIDTH) > 0) {
                    hasNext = true;
                }
                break;

            case FORWARE_RIGHT:
                if ((x + 2 * Block.HEIGHT + Block.DIVIDER_WIDTH) < mSurfaceWidth) {
                    hasNext = true;
                }
                break;
        }
        return hasNext;
    }


    private Block getNext(Block oldBlock, int direction) {
        int x = oldBlock.getLeft();
        int y = oldBlock.getTop();
        boolean hasNext = false;
        Block block = new Block();

        switch (direction) {
            case FORWARE_TOP:
                if ((y - Block.WIDTH - Block.DIVIDER_WIDTH) >= 0) {
                    hasNext = true;
                    block.setLeft(x);
                    block.setTop(y - Block.WIDTH - Block.DIVIDER_WIDTH);
                }
                break;

            case FORWARE_BOTTOM:
                if ((y + 2 * Block.WIDTH + Block.DIVIDER_WIDTH) < mSurfaceHeight) {
                    hasNext = true;
                    block.setLeft(x);
                    block.setTop(y + Block.WIDTH + Block.DIVIDER_WIDTH);
                }
                break;

            case FORWARE_LEFT:
                if ((x - 2 * Block.HEIGHT - Block.DIVIDER_WIDTH) > 0) {
                    hasNext = true;
                    block.setLeft(x - Block.HEIGHT - Block.DIVIDER_WIDTH);
                    block.setTop(y);
                }
                break;

            case FORWARE_RIGHT:
                if ((x + 2 * Block.HEIGHT + Block.DIVIDER_WIDTH) < mSurfaceWidth) {
                    hasNext = true;
                    block.setLeft(x + Block.HEIGHT + Block.DIVIDER_WIDTH);
                    block.setTop(y);
                }
                break;
        }

        if (hasNext) {
            return block;
        } else {
            return null;
        }
    }


    //是否重叠
    private void isContainInBlocks(float x, float y) {
        Log.d(TAG, "Annan isContainInBlocks mBlocks " + mBlocks.size() + " (" + x + "," + y + ")");
        for (Rect block2 : mBlocks) {
            if (block2.contains((int) x, (int) y)) {
                mBlocks.remove(block2);
                Log.d(TAG,"Annan isContainIn true");
                return;
            }
        }
        Log.d(TAG,"Annan isContainIn false");
        return;
    }

}
