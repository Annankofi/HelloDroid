package com.hello.touch;

import java.io.Serializable;

public class SerializablePointF implements Serializable {
    private static final long serialVersionUID = 186330698669012567L;
    public float x, y;

    public SerializablePointF(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
