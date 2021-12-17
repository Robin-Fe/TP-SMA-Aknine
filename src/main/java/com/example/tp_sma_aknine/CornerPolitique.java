package com.example.tp_sma_aknine;

import java.util.List;
import java.util.Random;

public class CornerPolitique implements Politique {
    // La priorité est donnée par diagonale. Une fois qu'une diagonale est bine complétée, elle ne changera plus
    private int cornerLvl = 0;
    public void action(Agent agent) {

        if (agent.checkMailBox()) {
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

    public boolean isPriority(Agent agent) {
        if (agent.getSleep()){
            return false;
        }
        this.cornerLvl = agent.getEnvironment().getCornerLvl();
        return (agent.checkMailBox() || (agent.isGoalOnCorner(cornerLvl) && !agent.checkPersonalGoal()) || (agent.isUnderCorner(cornerLvl)));
    }


}
