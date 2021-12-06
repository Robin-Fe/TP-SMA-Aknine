package com.example.tp_sma_aknine;

public class Message {
    private final Coordinate coordinate;

    public Message(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return this.coordinate;
    }
}
