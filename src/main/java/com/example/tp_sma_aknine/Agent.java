package com.example.tp_sma_aknine;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Agent {


    public Agent() {

    }

    public void perception(Environnement environment) {

    }

    public void action(Environnement environment) {

        perception(environment);

        //MOVE
        int xMove = new Random().nextInt(2);
        int yMove = new Random().nextInt(2);
        while (xMove + yMove < 1) {
            xMove = new Random().nextInt(2);
            yMove = new Random().nextInt(2);
        }
        int xDirection = new Random().nextInt(2);
        int yDirection = new Random().nextInt(2);

        if (xDirection == 0) {
            if (environment.findAgent(this)[0] - xMove < 0) {
                return;
            }
            if (yDirection == 0) {
                if (environment.findAgent(this)[1] - yMove < 0) {
                    return;
                }
                if (environment.isFreeOfAgent(environment.findAgent(this)[0] - xMove, environment.findAgent(this)[1] - yMove)) {
                    environment.moveAgent(this, environment.findAgent(this)[0] - xMove, environment.findAgent(this)[1] - yMove);
                }
            } else {
                if (environment.findAgent(this)[1] + yMove >= environment.getLargeMap()) {
                    return;
                }
                if (environment.isFreeOfAgent(environment.findAgent(this)[0] - xMove, environment.findAgent(this)[1] + yMove)) {
                    environment.moveAgent(this, environment.findAgent(this)[0] - xMove, environment.findAgent(this)[1] + yMove);
                }
            }
        } else {
            if (environment.findAgent(this)[0] + xMove >= environment.getLongMap()) {
                return;
            }
            if (yDirection == 0) {
                if (environment.findAgent(this)[1] - yMove < 0) {
                    return;
                }
                if (environment.isFreeOfAgent(environment.findAgent(this)[0] + xMove, environment.findAgent(this)[1] - yMove)) {
                    environment.moveAgent(this, environment.findAgent(this)[0] + xMove, environment.findAgent(this)[1] - yMove);
                }
            } else {
                if (environment.findAgent(this)[1] + yMove >= environment.getLargeMap()) {
                    return;
                }
                if (environment.isFreeOfAgent(environment.findAgent(this)[0] + xMove, environment.findAgent(this)[1] + yMove)) {
                    environment.moveAgent(this, environment.findAgent(this)[0] + xMove, environment.findAgent(this)[1] + yMove);
                }
            }
        }
    }
}