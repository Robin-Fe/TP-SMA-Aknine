package com.example.tp_sma_aknine;

import java.util.List;
import java.util.Observable;

public class Environment extends Observable {
    private List<Agent> listeAgents;
    private Agent[][] map;

    public Environment(int xLength, int yLength) {
        this.map = new Agent[xLength][yLength];
    }

    public Agent getContent(int x, int y) {
        return map[x][y];
    }

    public int getNbAgents() {
        return this.listeAgents.size();
    }

    public int getXLength() {
        return this.map.length;
    }

    public int getYLength() {
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
        assert this.map[oldX][oldY] == agent && this.map[newX][newY] == null;
        this.map[oldX][oldY] = null;
        this.map[newX][newY] = agent;
        setChanged();
        notifyObservers();
    }
}
