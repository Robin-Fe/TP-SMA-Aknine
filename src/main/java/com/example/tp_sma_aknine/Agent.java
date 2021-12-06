package com.example.tp_sma_aknine;

import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;


public class Agent extends Observable implements Runnable {

    public Thread worker;
    private final Image image;
    //ToDo : replace by coordinate
    private int x;
    private int y;
    //ToDo : replace by coordinate
    private final int xGoal;
    private final int yGoal;
    private final Environment environment;
    private MailBox mailBox;


    public Agent(Image image, int x, int y, int xGoal, int yGoal, Environment environment, MailBox mailBox) {
        this.worker = new Thread(this);
        this.image = image;
        this.x = x;
        this.y = y;
        this.xGoal = xGoal;
        this.yGoal = yGoal;
        this.environment = environment;
        this.mailBox = mailBox;
    }


    public void move(int x, int y) {
        if (environment.pickSemaphore()) {
            Agent agent = this.environment.getContent(x, y);
            if (agent == null) {
                this.environment.updateMap(this, this.x, this.y, x, y);
                this.x = x;
                this.y = y;
            } else {
                this.mailBox.addMessage(agent, x, y);
            }
            environment.letSemaphore();
        } else {
            long random = new Random().nextInt(2000);
            try {
                Thread.sleep(random);
            } catch (InterruptedException ignored) {

            }
        }
    }

    public void action() {
        boolean hasToMove = checkMailBox();
        if (!checkPersonalGoal() || hasToMove) {
            if (hasToMove) {
                List<Coordinate> directions = getFreeDirections();
                if (directions.isEmpty()) {
                    // ToDo : send message to everyone around
                } else {
                    Random rand = new Random();
                    Coordinate coordinate = directions.get(rand.nextInt(directions.size()));
                    move(coordinate.getX(), coordinate.getY());

                }
            } else {
                Coordinate coordinate = getDirection();
                Agent agent = this.environment.getContent(coordinate.getX(), coordinate.getY());
                if (agent == null){
                    move(coordinate.getX(), coordinate.getY());
                }

                else{
                    sendMessage(agent, coordinate.getX(), coordinate.getY());
                }
            }
        }

    }


    public void sendMessage(Agent agent, int x, int y) {
        this.mailBox.addMessage(agent, x, y);
    }

    public boolean checkMailBox() {
        if (mailBox.deliverMessages(this) != null){
            for (Message message : mailBox.deliverMessages(this)) {
                if (message.getFreeX() == this.x && message.getFreeY() == this.y) {
                    return true;
                }
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

    public int getxGoal() {
        return xGoal;
    }

    public int getyGoal() {
        return yGoal;
    }

    @Override
    public void run() {
        int tempspause = 700;
        while (!environment.getListeAgents().get(0).checkGlobalGoal()) {
            action();
//            if (!checkPersonalGoal()) {
//                List<Coordinate> freeDirections = getFreeDirections();
//                if (freeDirections.size() > 0) {
//                    Coordinate nextMove = freeDirections.get(new Random().nextInt(freeDirections.size()));
//                    move(nextMove.getX(), nextMove.getY());
//                }
//                setChanged();
//                notifyObservers();
//            }
            setChanged();
            notifyObservers();
            try {
                Thread.sleep(tempspause + new Random().nextInt(300));
            } catch (InterruptedException ignored) {

            }
        }
    }

    public void interrupt() {
        worker.interrupt();
        setChanged();
        notifyObservers();
    }

    public void start() {
        this.worker = new Thread(this);
        worker.start();
        setChanged();
        notifyObservers();
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
            freeDirections.add(new Coordinate(x, y + 1));
        if (down)
            freeDirections.add(new Coordinate(x, y - 1));
        if (left)
            freeDirections.add(new Coordinate(x - 1, y));
        if (right)
            freeDirections.add(new Coordinate(x + 1, y));
        return freeDirections;
    }

    public Coordinate getDirection(){
        ArrayList<Coordinate> objectiveDirections = new ArrayList<>();
        Coordinate goal = new Coordinate(xGoal, yGoal);
        if (xGoal > x){
            objectiveDirections.add(new Coordinate(x+1, y)) ;
        }
        if (xGoal < x){
            objectiveDirections.add(new Coordinate(x-1, y)) ;
        }
        if (yGoal > y){
            objectiveDirections.add(new Coordinate(x, y+1)) ;
        }
        if (yGoal < y){
            objectiveDirections.add(new Coordinate(x, y-1)) ;
        }

        List<Coordinate> freeDirections = getFreeDirections();
        List<Coordinate> bestDirections = (List<Coordinate>) objectiveDirections.clone();
        for (Coordinate coordinate : objectiveDirections){
            if (!(freeDirections.contains(coordinate))){
                bestDirections.remove(coordinate);
            }
        }
        if (bestDirections.isEmpty()){
            Random rand = new Random();
            return objectiveDirections.get(rand.nextInt(objectiveDirections.size()));
        }
        else {
            Random rand = new Random();
            return bestDirections.get(rand.nextInt(bestDirections.size()));
        }
    }
}