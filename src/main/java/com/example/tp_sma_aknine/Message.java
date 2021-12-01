package com.example.tp_sma_aknine;

public class Message {
    private final int freeX;
    private final int freeY;

    public Message(int freeX, int freeY) {
        this.freeX = freeX;
        this.freeY = freeY;
    }

    public int getFreeX() {
        return freeX;
    }

    public int getFreeY() {
        return freeY;
    }
}
