package com.example.tp_sma_aknine;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Agent {

    private final Image image;
    private int x;
    private int y;
    private final int xGoal;
    private final int yGoal;
    private final Environment environment;
    private MailBox mailBox;


    public Agent(Image image, int x, int y, int xGoal, int yGoal, Environment environment) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.xGoal = xGoal;
        this.yGoal = yGoal;
        this.environment = environment;
    }

    public void perception() {


    }

    public void action(){
        boolean hasToMove = checkMailBox();
        if (hasToMove) {
            List<Coordinate> directions = getFreeDirections();
            if (directions.isEmpty()){
                // ToDo : send message to everyone around
            }
            else {
                Random rand = new Random();
                Coordinate coordinate = directions.get(rand.nextInt(directions.size()));
                move(coordinate.getX(), coordinate.getY());
            }
        }
        else {
            Coordinate coordinate = new Coordinate(0, 0); // ToDo : select the good coordinate to go
            Agent agent = this.environment.getContent(coordinate.getX(), coordinate.getY());
            if (agent == null)
                move(coordinate.getX(), coordinate.getY());
            else
                sendMessage(agent, coordinate.getX(), coordinate.getY());
        }
    }


    public void move(int x, int y) {
        assert this.environment.getContent(x, y) == null ;
        this.environment.updateMap(this, this.x, this.y, x, y);
        this.x = x;
        this.y = y;
    }

    public void sendMessage(Agent agent, int x, int y){
        this.mailBox.addMessage(agent, x, y);
    }

    public boolean checkMailBox() {
        for (Message message : mailBox.deliverMessages(this)) {
            if (message.getFreeX() == this.x && message.getFreeY() == this.y) {
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<Coordinate> getFreeDirections() {
        List<Coordinate> freeDirections = new ArrayList<>();
        boolean up = false;
        boolean down = false;
        boolean left = false;
        boolean right = false;
        if (y + 1 < environment.getYLength())
            up = this.environment.getContent(x, y + 1) == null;
        if (y - 1 >= 0)
            down = this.environment.getContent(x, y - 1) == null;
        if (x + 1 < environment.getXLength())
            right = this.environment.getContent(x + 1, y) == null;
        if (x - 1 >= 0)
            left = this.environment.getContent(x - 1, y) == null;
        if (up)
            freeDirections.add(new Coordinate(x, y+1));
        if (down)
            freeDirections.add(new Coordinate(x, y-1));
        if (left)
            freeDirections.add(new Coordinate(x-1, y));
        if (right)
            freeDirections.add(new Coordinate(x+1, y));
        return freeDirections;
    }
}