package com.hello.touch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Points implements Serializable {
    private static final long serialVersionUID = -5336231910447611363L;

    private List<SerializablePointF> pointFs = new ArrayList<>();
    private int actionId;

    public Points(SingleTouchEvent event) {
        actionId = event.id;
    }

    public SerializablePointF getPointF(int index) {
        return pointFs.get(index);
    }

    public void addPointF(SingleTouchEvent event) {
        pointFs.add(new SerializablePointF(event.x, event.y));
    }

    public int getActionId() {
        return actionId;
    }

    public List<SerializablePointF> getPointFs() {
        return pointFs;
    }

    public void dispose() {
        actionId = -1;
    }
}
