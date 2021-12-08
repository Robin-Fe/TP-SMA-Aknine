package com.example.tp_sma_aknine;

import javafx.scene.image.Image;

import java.util.*;


public class Agent extends Observable implements Runnable {
    public Thread worker;
    private final Image image;
    private Coordinate position;
    private final Coordinate goal;
    private final Environment environment;
    private final MailBox mailBox;
    private final double e;
    private final Politique politique;

    public Agent(Image image, Coordinate position, Coordinate goal, Environment environment, MailBox mailBox, double e, Politique politique) {
        this.worker = new Thread(this);
        this.image = image;
        this.position = position;
        this.goal = goal;
        this.environment = environment;
        this.mailBox = mailBox;
        this.e = e;
        this.politique = politique;
    }

    @Override
    public void run() {
        int timeTime = 500;
        while (!environment.getListeAgents().get(0).checkGlobalGoal()) {
            if (politique.isPriority(this)) {
                politique.action(this);
                setChanged();
                notifyObservers();
            }
            try {
                Thread.sleep(timeTime + new Random().nextInt(300));
            } catch (InterruptedException ignored) {

            }
        }
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public MailBox getMailBox() {
        return this.mailBox;
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
            try {
                Thread.sleep(new Random().nextInt(500));
            } catch (InterruptedException ignored) {

            }
        }
    }


    public void sendMessage(Agent agent, Coordinate coordinate) {
        if (mailBox.pickSemaphore()) {
            this.mailBox.addMessage(agent, coordinate);
            mailBox.letSemaphore();
        } else {
            try {
                Thread.sleep(new Random().nextInt(100));
            } catch (InterruptedException ignored) {

            }
        }
    }

    public boolean checkMailBox() {
        if (mailBox.pickSemaphore()) {
            LinkedList<Message> messages = mailBox.deliverMessages(this);
            for (Message message : messages) {
                if (message.getCoordinate().equals(this.position)) {
                    mailBox.letSemaphore();
                    return true;
                }
            }
            mailBox.letSemaphore();
        } else {
            try {
                Thread.sleep(new Random().nextInt(100));
            } catch (InterruptedException ignored) {

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
        List<Coordinate> objectiveDirections = this.position.getDirection(this.goal, environment.getXLength(), environment.getYLength());
        List<Coordinate> freeDirections = getFreeDirections();
        List<Coordinate> bestDirections = new ArrayList<>();
        if (Math.random() <= e) {
            for (Coordinate coordinate : objectiveDirections) {
                bestDirections.add(coordinate);
            }
            for (Coordinate coordinate : objectiveDirections) {
                if (!(freeDirections.contains(coordinate))) {
                    bestDirections.remove(coordinate);
                }
            }
            if (bestDirections.isEmpty()) {
                Random rand = new Random();
                Coordinate coord = objectiveDirections.get(rand.nextInt(objectiveDirections.size()));
                return coord;
            } else {
                Random rand = new Random();
                return bestDirections.get(rand.nextInt(bestDirections.size()));
            }
        } else {
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

    public boolean isGoalOnBorder(int borderLvl) {
        return this.goal.getX() == borderLvl ||
                this.goal.getY() == borderLvl ||
                this.goal.getX() == environment.getXLength() - 1 - borderLvl ||
                this.goal.getY() == environment.getYLength() - 1 - borderLvl;
    }
}