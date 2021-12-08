package com.example.tp_sma_aknine;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class RememberPolitique implements Politique {

    private Queue<Coordinate> forbiddenCoordinates = new LinkedList<>();
    private int queueSize = 3;

    public void action(Agent agent) {
        boolean hasToMove = agent.checkMailBox();
        if (!agent.checkPersonalGoal() || hasToMove) {
            if (hasToMove) {
                List<Coordinate> directions = agent.getFreeDirections();
                directions.removeIf(direction -> forbiddenCoordinates.contains(direction));
                if (directions.isEmpty()) {
                    if (new Random().nextDouble() > 0.3)
                        agent.broadcastMessages();
                } else {
                    Random rand = new Random();
                    Coordinate coordinate = directions.get(rand.nextInt(directions.size()));
                    agent.move(coordinate);
                    agent.getMailBox().emptyBox(agent);
                }
            } else {
                Coordinate coordinate = agent.getDirection();
                if (!forbiddenCoordinates.contains(coordinate)) {
                    Agent moveAgent = agent.getEnvironment().getContent(coordinate);
                    if (moveAgent == null) {
                        agent.move(coordinate);
                    } else {
                        if (new Random().nextDouble() > 0.2)
                            agent.sendMessage(moveAgent, coordinate);
                    }
                }
            }
        }
        if (new Random().nextDouble() > 0.4) {
            forbiddenCoordinates.clear();
        }
    }

    public boolean isPriority(Agent agent) {
        return (agent.checkMailBox() || !agent.checkPersonalGoal());
    }

    public void addForbiddenCoordinates(LinkedList<Message> messages) {
        for (Message message : messages) {
            forbiddenCoordinates.add(message.getCoordinate());
            if (forbiddenCoordinates.size() > queueSize) {
                forbiddenCoordinates.remove();
            }
        }
    }
}
