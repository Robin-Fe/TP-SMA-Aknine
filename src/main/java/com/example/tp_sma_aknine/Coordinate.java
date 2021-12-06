package com.example.tp_sma_aknine;

import java.util.Objects;

public class Coordinate {

    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinate getUp(int maxLength) {
        if (y + 1 < maxLength)
            return new Coordinate(x, y + 1);
        return null;
    }

    public Coordinate getDown() {
        if (y - 1 >= 0)
            return new Coordinate(x, y - 1);
        return null;
    }

    public Coordinate getRight(int maxLength) {
        if (x + 1 < maxLength)
            return new Coordinate(x + 1, y);
        return null;
    }
    public Coordinate getLeft() {
        if (x - 1 >= 0)
            return new Coordinate(x - 1, y);
        return null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


}