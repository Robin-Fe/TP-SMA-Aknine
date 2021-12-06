package com.example.tp_sma_aknine;

import java.util.List;
import java.util.Observable;

public class Environment extends Observable {
    private List<Agent> listeAgents;
    private Agent[][] map;
    private boolean semaphore = true;

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
        semaphore = true;
        setChanged();
        notifyObservers();
        assert this.map[oldPosition.getX()][oldPosition.getY()] == agent && this.map[newPosition.getX()][newPosition.getY()] == null;
        this.map[oldPosition.getX()][oldPosition.getY()]= null;
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
}
