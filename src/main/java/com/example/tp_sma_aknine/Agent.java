package com.example.tp_sma_aknine;

import javafx.scene.image.Image;

import java.util.LinkedList;

public class Agent {

    private Image image;
    private int x;
    private int y;
    private final int xGoal;
    private final int yGoal;
    private Environment environment;
    private MailBox mailBox;


    public Agent(Image image, int x, int y, int xGoal, int yGoal, Environment environment) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.xGoal = xGoal;
        this.yGoal = yGoal;
        this.environment = environment;
    }

    public void perception(Environment environment) {

    }


    public void move (int x, int y) {
        Agent agent = this.environment.getContent(x,y);
        if (agent == null){
            this.environment.updateMap(this, this.x, this.y, x, y);
            this.x = x;
            this.y = y;
        }
        else {
            this.mailBox.addMessage(agent, x, y);
        }
    }


    public boolean checkMailBox() {
        for (Message message : mailBox.deliverMessages(this)){
            if (message.getFreeX() == this.x && message.getFreeY() == this.y){
                return true;
            }
        }
        return false;
    }

    public boolean checkPersonalGoal() {
        return (this.x == this.xGoal && this.y == this.yGoal);
    }

    public boolean checkGlobalGoal() {
        for (Agent agent : environment.getListeAgents()) {
            if (!agent.checkPersonalGoal()) {
                return false;
            }
        }
        return true;
    }

    public Image getImage() {
        return image;
    }
}