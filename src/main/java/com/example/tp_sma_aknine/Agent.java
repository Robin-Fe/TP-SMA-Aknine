package com.example.tp_sma_aknine;

import javafx.scene.image.Image;

import java.util.LinkedList;

public class Agent {

    private Image image;
    private int x;
    private int y;
    private int xGoal;
    private int yGoal;
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

    public Image getImage() {
        return image;
    }
}