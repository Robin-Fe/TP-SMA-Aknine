package com.example.tp_sma_aknine;

import java.util.*;

public class Environment extends Observable {
    private List<Agent> listeAgents;
    private Agent[][] map;
    private boolean[][] cornersMap;
    private boolean semaphore = true;
    private int bordelLvl = 0;

    public Environment(int xLength, int yLength) {
        this.map = new Agent[xLength][yLength];
        HashMap<Integer, Agent> lvlMap = new HashMap<>();
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
        List<Agent> borderAgents = new ArrayList<>();
        for (Agent[] agents : this.map) {
            for (Agent agent : agents) {
                if (agent != null){
                    if (agent.getGoal().getX() == bordelLvl
                            || agent.getGoal().getY() == bordelLvl
                            || agent.getGoal().getX() == getXLength() - 1 - bordelLvl
                            || agent.getGoal().getY() == getYLength() - 1 - bordelLvl) {
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

    public void updateCornerMap(){
        List<Agent> cornerAgents = new ArrayList<>();
        for (Agent[] agents : this.map) {
            for (Agent agent : agents) {
                if (agent != null){
                    if (agent.getGoal().getX() == bordelLvl
                            || agent.getGoal().getY() == bordelLvl
                            || agent.getGoal().getX() == getXLength() - 1 - bordelLvl
                            || agent.getGoal().getY() == getYLength() - 1 - bordelLvl) {
                        cornerAgents.add(agent);
                    }
                }

            }
        }
    }

    public boolean isCorner(Coordinate coordinate){
        if ((coordinate.getX()==0 && coordinate.getY()==0)
        ||(coordinate.getX()==0 && coordinate.getY() == getYLength()-1)
        ||(coordinate.getX()== getXLength()-1 && coordinate.getY()==0)
        ||(coordinate.getX() == getXLength()-1 && coordinate.getY() == getYLength()-1)){
            return true;
        }
        List<Coordinate> coordinates = coordinate.getAroundCoordinates(getXLength(), getYLength());
        List<Coordinate> removeList = new ArrayList<>();
        for (Coordinate neighbour : coordinates){
            if (cornersMap[neighbour.getX()][neighbour.getY()]){
                removeList.add(neighbour);
            }
        }
        coordinates.removeAll(removeList);
        return coordinates.size() == 2;
    }

}
