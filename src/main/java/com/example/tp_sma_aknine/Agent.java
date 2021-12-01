package com.example.tp_sma_aknine;

import java.util.LinkedList;
import java.util.Random;

public class Agent {

    private String image; //path to the image
    private int x;
    private int y;
    private int xGoal;
    private int yGoal;
    private Environment environment;
    private MailBox mailBox;


    public Agent(String image, int x, int y, int xGoal, int yGoal, Environment environment) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.xGoal = xGoal;
        this.yGoal = yGoal;
        this.environment = environment;
    }

    public void perception(Environment environment) {

    }

    public void action(Environment environment) {

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

    public boolean move (int x, int y) {
        return true;
    }

    public boolean sendMessage(Agent target, int freeX, int freeY) {
        mailBox.addMessage(target, freeX, freeY);
        return true;
    }

    public boolean checkMailBox() {
        LinkedList<Message> messages = mailBox.deliverMessages(this);
        return true;
    }

    public boolean checkPersonalGoal() { return (this.x == this.xGoal && this.y == this.yGoal); }

    public boolean checkGlobalGoal() {
        for (Agent agent : environment.getListeAgents()) {
            if (!agent.checkPersonalGoal()) {
                return false;
            }
        }
        return true;
    }

    public String getImage() {
        return image;
    }
}