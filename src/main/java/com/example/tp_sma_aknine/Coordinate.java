package com.example.tp_sma_aknine;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

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

    public List<Coordinate> getAroundCoordinates(int maxX, int maxY){
        List<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(this.getUp(maxY));
        coordinates.add(this.getDown());
        coordinates.add(this.getRight(maxX));
        coordinates.add(this.getLeft());
        return coordinates;
    }

    public List<Coordinate> getDirection(Coordinate goal, int maxX, int maxY){
        ArrayList<Coordinate> goalDirections = new ArrayList<>();
        if (goal.getX() > this.getX()) {
            goalDirections.add(this.getRight(maxX));
        }
        if (goal.getX() < this.getX()) {
            goalDirections.add(this.getLeft());
        }
        if (goal.getY() > this.getY()) {
            goalDirections.add(this.getUp(maxY));
        }
        if (goal.getY() < this.getY()) {
            goalDirections.add(this.getDown());
        }

        if (goalDirections.size() == 0){
            goalDirections.add(this.getUp(maxY));
            goalDirections.add(this.getDown());
            goalDirections.add(this.getRight(maxX));
            goalDirections.add(this.getLeft());
            goalDirections.removeAll(null);
        }

        return goalDirections;
    }
}