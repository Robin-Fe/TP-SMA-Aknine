package com.example.tp_sma_aknine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

public class Environment extends Observable {
    private List<Agent> listeAgents;
    private Agent[][] map;
    private boolean semaphore = true;
    private int bordelLvl = 0;

    public Environment(int xLength, int yLength) {
        this.map = new Agent[xLength][yLength];
    }

    public Agent getContent(Coordinate coordinate) {
        return map[coordinate.getX()][coordinate.getY()];
    }

    public int getXLength() {
        return this.map.length;
    }

    public int getYLength() {
        return this.map[0].length;
    }

    public List<Agent> getListeAgents() {
        return listeAgents;
    }

    public void setListeAgents(List<Agent> listeAgents) {
        this.listeAgents = listeAgents;
        for (Agent agent : listeAgents) {
            map[agent.getPosition().getX()][agent.getPosition().getY()] = agent;
        }
        setChanged();
        notifyObservers();
    }

    public void updateMap(Agent agent, Coordinate oldPosition, Coordinate newPosition) {
        this.map[oldPosition.getX()][oldPosition.getY()] = null;
        this.map[newPosition.getX()][newPosition.getY()] = agent;
        setChanged();
        notifyObservers();
    }

    public boolean getSemaphore() {
        return semaphore;
    }

    public boolean pickSemaphore() {
        // ToDo : increment score
        if (getSemaphore()) {
            semaphore = false;
            return true;
        }
        return false;
    }

    public void letSemaphore() {
        semaphore = true;
    }

    public boolean isABorder(Coordinate coordinate, int borderLvl) {
        return coordinate.getX() == borderLvl || coordinate.getY() == borderLvl || coordinate.getX() == getXLength() - borderLvl || coordinate.getY() == getYLength() - borderLvl;
    }

    public int getBorderLvl() {
        List<Agent> leftBorder = new ArrayList<>();
        List<Agent> upBorder = Arrays.asList(this.map[bordelLvl]);
        List<Agent> downBorder = Arrays.asList(this.map[bordelLvl]);
        List<Agent> rightBorder = new ArrayList<>();
        for (int column = 0; column < getYLength(); column++) {
            leftBorder.add(this.map[column][bordelLvl]);
            rightBorder.add(this.map[column][getYLength() - 1 - bordelLvl]);
        }
        List<Agent> borderAgents = new ArrayList<>();
        borderAgents.addAll(rightBorder);
        borderAgents.addAll(leftBorder);
        borderAgents.addAll(upBorder);
        borderAgents.addAll(downBorder);
        boolean increment = true;
        for (Agent agent : borderAgents) {
            if (agent != null) {
                if (!agent.checkPersonalGoal()) {
                    increment = false;
                }
            }
        }
        if (increment) {
            this.bordelLvl++;
        }
        return this.bordelLvl;
    }
}
