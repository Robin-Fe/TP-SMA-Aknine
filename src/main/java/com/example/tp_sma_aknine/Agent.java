package com.example.tp_sma_aknine;

import javafx.scene.image.Image;
import java.util.Observable;
import java.util.Random;

public class Agent extends Observable implements Runnable {

    public Thread worker;
    private final Image image;
    private int x;
    private int y;
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

    public void perception() {

    }


    public void move (int x, int y) {
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void run() {
        int tempspause = 1000;
        while (!environment.getListeAgents().get(0).checkGlobalGoal()) {
            perception();
            int r1 = new Random().nextInt(environment.getLongMap());
            int r2 = new Random().nextInt(environment.getLargeMap());
            move(r1, r2);
            setChanged();
            notifyObservers();
            try {
                Thread.sleep(tempspause);
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
        worker.start();
        setChanged();
        notifyObservers();
    }
}