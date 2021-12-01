package com.example.tp_sma_aknine;

import java.util.List;
import java.util.Observable;

public class Environment extends Observable {
    private List<Agent> listeAgents;
    private Agent[][] map;
    private boolean semaphore = true;

    public Environment(int tailleMapLong, int tailleMapLarge) {
        this.map = new Agent[tailleMapLong][tailleMapLarge];
    }

    public Agent getContent(int x, int y) {
        return map[x][y];
    }

    public int getNbAgents() {
        return this.listeAgents.size();
    }

    public int getLongMap() {
        return this.map.length;
    }

    public int getLargeMap() {
        return this.map[0].length;
    }

    public List<Agent> getListeAgents() { return listeAgents; }

    public void setListeAgents(List<Agent> listeAgents) {
        this.listeAgents = listeAgents;
        for (Agent agent : listeAgents) {
            map[agent.getX()][agent.getY()] = agent;
        }
        setChanged();
        notifyObservers();
    }

    public void updateMap(Agent agent, int oldX, int oldY, int newX, int newY){
        semaphore = true;
        setChanged();
        notifyObservers();
        assert this.map[oldX][oldY] == agent && this.map[newX][newY] == null;
        this.map[oldX][oldY] = null;
        this.map[newX][newY] = agent;
        setChanged();
        notifyObservers();
    }

    public boolean getSemaphore () { return semaphore; }

    public boolean pickSemaphore() {
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
