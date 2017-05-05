package com.hello.touch;

import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

public class MultiTouchEvent {

    private List<SingleTouchEvent> touchEvents = new ArrayList<>();
    private int actionIndex;
    private int actionId;

    public MultiTouchEvent(MotionEvent motionEvent) {
        setTouchEvents(motionEvent);
        actionIndex = motionEvent.getActionIndex();
        actionId = motionEvent.getPointerId(actionIndex);
    }


    public void setTouchEvents(MotionEvent motionEvent) {
        int count = motionEvent.getPointerCount();
        for (int i = 0; i < count; i++) {
            SingleTouchEvent touchEvent = new SingleTouchEvent();
            touchEvent.x = motionEvent.getX(i);
            touchEvent.y = motionEvent.getY(i);
            touchEvent.id = motionEvent.getPointerId(i);
            touchEvent.index = i;
            touchEvents.add(touchEvent);
        }
    }

    public SingleTouchEvent getTouchEvent(int index) {
        return touchEvents.get(index);
    }

    public SingleTouchEvent getTouchEventByID(int id) {
        if (id == -1) {
            return null;
        }
        int index = -1;
        for (SingleTouchEvent touchEvent : touchEvents) {
            if (touchEvent.id == id) {
                index = touchEvent.index;
            }
        }
        return touchEvents.get(index);
    }

    public int getActionIndex() {
        return actionIndex;
    }

    public List<SingleTouchEvent> getTouchEvents() {
        return touchEvents;
    }

    public int getPointerCount() {
        return touchEvents.size();
    }

    public int getActionId() {
        return actionId;
    }
}
