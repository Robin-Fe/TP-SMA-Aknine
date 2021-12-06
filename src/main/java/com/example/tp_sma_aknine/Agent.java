package com.example.tp_sma_aknine;

import javafx.scene.image.Image;

import java.util.*;


public class Agent extends Observable implements Runnable {
    public Thread worker;
    private final Image image;
    private Coordinate position;
    private Coordinate goal;
    private final Environment environment;
    private MailBox mailBox;
    private final double e;

    public Agent(Image image, Coordinate position, Coordinate goal, Environment environment, MailBox mailBox, double e) {
        this.worker = new Thread(this);
        this.image = image;
        this.position = position;
        this.goal = goal;
        this.environment = environment;
        this.mailBox = mailBox;
        this.e = e;
    }

    @Override
    public void run() {
        int timeTime = 700;
        while (!environment.getListeAgents().get(0).checkGlobalGoal()) {
            if (this.checkMailBox() || !this.checkPersonalGoal()){
                action();
                setChanged();
                notifyObservers();
            }
            try {
                Thread.sleep(timeTime + new Random().nextInt(300));
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
                    broadcastMessages();
                } else {
                    Random rand = new Random();
                    Coordinate coordinate = directions.get(rand.nextInt(directions.size()));
                    move(coordinate);
                    mailBox.emptyBox(this);
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
        if (environment.pickSemaphore()) {
            Agent agent = this.environment.getContent(newPosition);
            if (agent == null) {
                this.environment.updateMap(this, this.position, newPosition);
                this.position = newPosition;
            } else {
                this.mailBox.addMessage(agent, newPosition);
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


    public void sendMessage(Agent agent, Coordinate coordinate) {
        this.mailBox.addMessage(agent, coordinate);
    }

    public boolean checkMailBox() {
        LinkedList<Message> messages = mailBox.deliverMessages(this);
        for (Message message : messages) {
            if (message.getCoordinate().equals(this.position)) {
                return true;
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
        for (Coordinate coordinate : this.position.getAroundCoordinates(environment.getXLength(), environment.getYLength())) {
            if (coordinate != null) {
                freeDirections.add(coordinate);
            }
        }
        return freeDirections;
    }

    public Coordinate getDirection() {
        // ToDo : probabilist in case where it blocks
        List<Coordinate> objectiveDirections = this.position.getDirection(this.goal, environment.getXLength(), environment.getYLength());
        List<Coordinate> freeDirections = getFreeDirections();
        List<Coordinate> bestDirections = new ArrayList<>();
        if (Math.random() <= e){
            for (Coordinate coordinate : objectiveDirections){
                bestDirections.add(coordinate);
            }
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
        else {
            Random rand = new Random();
            return freeDirections.get(rand.nextInt(freeDirections.size()));
        }
    }

    public void broadcastMessages() {
        for (Coordinate coordinate : this.position.getAroundCoordinates(environment.getXLength(), environment.getYLength())) {
            if (coordinate != null) {
                sendMessage(environment.getContent(coordinate), coordinate);
            }
        }
    }

    public Coordinate getPosition() {
        return this.position;
    }

    public Coordinate getGoal() {
        return this.goal;
    }
}