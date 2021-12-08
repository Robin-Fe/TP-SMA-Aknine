package com.example.tp_sma_aknine;

import java.util.List;
import java.util.Random;

public class NaivePolitique implements Politique {

    public void action(Agent agent) {
        boolean hasToMove = agent.checkMailBox();
        if (!agent.checkPersonalGoal() || hasToMove) {
            if (hasToMove) {
                List<Coordinate> directions = agent.getFreeDirections();
                if (directions.isEmpty()) {
                    agent.broadcastMessages();
                } else {
                    Random rand = new Random();
                    Coordinate coordinate = directions.get(rand.nextInt(directions.size()));
                    agent.move(coordinate);
                    agent.getMailBox().emptyBox(agent);
                }
            } else {
                Coordinate coordinate = agent.getDirection();
                Agent moveAgent = agent.getEnvironment().getContent(coordinate);
                if (moveAgent == null) {
                    agent.move(coordinate);
                } else {
                    agent.sendMessage(moveAgent, coordinate);
                }
            }
        }
    }

    public boolean isPriority(Agent agent) {
        return (agent.checkMailBox() || !agent.checkPersonalGoal());
    }
}
