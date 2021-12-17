package com.example.tp_sma_aknine;

import java.util.*;

public class Environment extends Observable {
    private List<Agent> listeAgents;
    private Agent[][] map;
    private boolean semaphore = true;
    private int bordelLvl = 0;
    private int cornerLvl = 0;

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



    public int getBorderLvl() {
        List<Agent> borderAgents = new ArrayList<>();
        for (Agent[] agents : this.map) {
            for (Agent agent : agents) {
                if (agent != null){
                    if (agent.isGoalOnBorder(this.bordelLvl)) {
                        borderAgents.add(agent);
                    }
                }

            }
        }
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

    public int getCornerLvl() {
        boolean increment = true;
        List<Agent> cornerAgents = new ArrayList<>();
        for (Agent[] agents : this.map) {
            for (Agent agent : agents) {
                if (agent != null){
                    if (agent.isUnderCorner(this.cornerLvl))
                        increment = false;
                    if (agent.isGoalOnCorner(this.cornerLvl)) {
                        cornerAgents.add(agent);
                    }
                }
            }
        }
        if (increment){
            for (Agent agent : cornerAgents) {
                if (agent != null) {
                    if (!agent.checkPersonalGoal()) {
                        increment = false;
                    }
                }
            }
        }
        if (increment) {
            this.cornerLvl++;
            System.out.println(cornerLvl);
            for (Agent agent : cornerAgents){
                agent.setSleep(true);
            }
        }
        return this.cornerLvl;
    }
}
