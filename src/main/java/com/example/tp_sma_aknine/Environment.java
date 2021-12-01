package com.example.tp_sma_aknine;

import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Random;

public class Environment extends Observable {
    private final List<Agent> listeAgents;
    private Agent[][] map;

    public Environment(int tailleMapLong, int tailleMapLarge, List<Agent> listeAgents) {
        this.map = new Agent[tailleMapLong][tailleMapLarge];
        HashMap<Agent, int[]> agentHashMap = new HashMap<>();
        int r1 = new Random().nextInt(tailleMapLong);
        int r2 = new Random().nextInt(tailleMapLarge);
        for (Agent agent : listeAgents) {
            while (!isFreeOfAgent(r1, r2)) {
                r1 = new Random().nextInt(tailleMapLong);
                r2 = new Random().nextInt(tailleMapLarge);
            }
            map[r1][r2] = agent;
            agentHashMap.put(agent, new int[]{r1, r2});
        }

        this.listeAgents = listeAgents;
    }

    public void moveAgent(Agent agent, int x, int y) {
        setChanged();
        notifyObservers();
    }

    public boolean isFreeOfAgent(int x, int y) {
        return map[x][y] == null;
    }

    public Agent getContent(int x, int y) {
        return (Agent) map[x][y];
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

}
