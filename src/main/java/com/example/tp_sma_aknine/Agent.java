package com.example.tp_sma_aknine;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;


public class Agent extends Observable implements Runnable {
    public Thread worker;
    private final Image image;
    private Coordinate position;
    private Coordinate goal;
    private final Environment environment;
    private MailBox mailBox;


    public Agent(Image image, Coordinate position, Coordinate goal, Environment environment, MailBox mailBox) {
        this.worker = new Thread(this);
        this.image = image;
        this.position = position;
        this.goal = goal;
        this.environment = environment;
        this.mailBox = mailBox;
    }


    public void action() {
        boolean hasToMove = checkMailBox();
        if (!checkPersonalGoal() || hasToMove) {
            if (hasToMove) {
                List<Coordinate> directions = getFreeDirections();
                if (directions.isEmpty()) {
                    broadcastMessages();
                    // ToDo : send message to everyone around
                } else {
                    Random rand = new Random();
                    Coordinate coordinate = directions.get(rand.nextInt(directions.size()));
                    move(coordinate);
                }
            } else {
                Coordinate coordinate = getDirection();
                Agent agent = this.environment.getContent(coordinate);
                if (agent == null) {
                    move(coordinate);
                } else {
                    sendMessage(agent, coordinate);
                }
            }
        }
    }

    public void move(Coordinate newPosition) {
        if (environment.pickSemaphore() && mailBox.pickSemaphore()) {
            Agent agent = this.environment.getContent(newPosition);
            if (agent == null) {
                this.environment.updateMap(this, this.position, newPosition);
                this.position = newPosition;
            } else {
                this.mailBox.addMessage(agent, newPosition);
            }
            environment.letSemaphore();
            mailBox.letSemaphore();
        } else {
            long random = new Random().nextInt(2000);
            try {
                Thread.sleep(random);
            } catch (InterruptedException ignored) {

            }
        }
    }


    public void sendMessage(Agent agent, Coordinate coordinate) {
        this.mailBox.addMessage(agent, coordinate);
    }

    public boolean checkMailBox() {
        if (mailBox.deliverMessages(this) != null) {
            for (Message message : mailBox.deliverMessages(this)) {
                if (message.getCoordinate().equals(this.position)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkPersonalGoal() {
        return this.position.equals(this.goal);
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


    @Override
    public void run() {
        int tempspause = 700;
        while (!environment.getListeAgents().get(0).checkGlobalGoal()) {
            action();
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
        Coordinate up = this.position.getUp(environment.getYLength());
        Coordinate down = this.position.getDown();
        Coordinate right = this.position.getRight(environment.getXLength());
        Coordinate left = this.position.getLeft();
        if (up != null)
            freeDirections.add(up);
        if (down != null)
            freeDirections.add(down);
        if (right != null)
            freeDirections.add(right);
        if (left != null)
            freeDirections.add(left);
        return freeDirections;
    }

    public Coordinate getDirection() {
        // ToDo : probabilist in case where it blocks
        ArrayList<Coordinate> objectiveDirections = new ArrayList<>();
        if (goal.getX() > position.getX()) {
            objectiveDirections.add(position.getRight(environment.getXLength()));
        }
        if (goal.getX() < position.getX()) {
            objectiveDirections.add(position.getLeft());
        }
        if (goal.getY() > position.getY()) {
            objectiveDirections.add(position.getUp(environment.getYLength()));
        }
        if (goal.getY() < position.getY()) {
            objectiveDirections.add(position.getDown());
        }
        List<Coordinate> freeDirections = getFreeDirections();
        List<Coordinate> bestDirections = (List<Coordinate>) objectiveDirections.clone();
        for (Coordinate coordinate : objectiveDirections) {
            if (!(freeDirections.contains(coordinate))) {
                bestDirections.remove(coordinate);
            }
        }
        if (bestDirections.isEmpty()) {
            Random rand = new Random();
            return objectiveDirections.get(rand.nextInt(objectiveDirections.size()));
        } else {
            Random rand = new Random();
            return bestDirections.get(rand.nextInt(bestDirections.size()));
        }
    }

    public void broadcastMessages() {
        Coordinate up = this.position.getUp(environment.getYLength());
        Coordinate down = this.position.getDown();
        Coordinate right = this.position.getRight(environment.getXLength());
        Coordinate left = this.position.getLeft();
        if (up != null)
            sendMessage(environment.getContent(up), up);
        if (down != null)
            sendMessage(environment.getContent(down), down);
        if (left != null)
            sendMessage(environment.getContent(left), left);
        if (right != null)
            sendMessage(environment.getContent(right), right);
    }

    public Coordinate getPosition() {
        return this.position;
    }

    public Coordinate getGoal() {
        return this.goal;
    }
}