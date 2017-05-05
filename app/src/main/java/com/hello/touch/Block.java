package com.hello.touch;

import android.graphics.Rect;
import android.util.Log;

public class Block {
    private static final String TAG = Block.class.getSimpleName();
    private int left;
    private int top;
    private int right;
    private int bottom;
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    public static final int DIVIDER_WIDTH = 5;

    public Block() {
    }

    public Block(int width, int heigh) {
        this.left = width;
        this.top = heigh;
    }
    public void set(int left,int top,int right,int bottom){
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }
    public Rect getRect(){
        return  new Rect(left,top,left+WIDTH,top+HEIGHT);
    }
    public int getTop() {
        return top;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public void debug(){
        Log.d(TAG,"("+left+","+top+","+left+WIDTH+","+top+HEIGHT+")");
    }
}
