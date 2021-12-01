package com.example.tp_sma_aknine;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

public class Simulation extends Observable implements Runnable {

    public Thread worker;
    private final List<Agent> listeAgents;
    private final Environment environment;
    private int score;

    public Simulation(int nbAgents, int tailleMapLong, int tailleMapLarge) {
        Environment environment = new Environment (tailleMapLong, tailleMapLarge);
        List<Agent> listeAgents = new ArrayList<>();
        Image imSoleil = new Image("https://www.marianne38.com/wp-content/uploads/cardstock-florence-white-1.jpg", 19, 18, false, false);
        Image imAObject = new Image("http://retraites-vipassana.fr/wp-content/uploads/2016/06/cropped-carre-rouge.jpg", 19, 18, false, false);
        Image imBObject = new Image("https://www.ecopro-ascenseurs.fr/wp-content/uploads/2017/12/carre-vert-fonce.png", 19, 18, false, false);
        Image imHolding = new Image("https://www.iecd.org/iecd2/wp-content/uploads/2018/09/carre-gris-Copie-2.jpg", 19, 18, false, false);
        Agent soleil = new Agent(imSoleil, 0, 2, 3, 1, environment);
        listeAgents.add(soleil);

        environment.setListeAgents(listeAgents);
        this.listeAgents = listeAgents;
        this.environment = environment;
        this.score = 0;
    }


    @Override
    public void run() {
        int nbTours = 0;
        long tempspause = 100;
        while (!this.listeAgents.get(0).checkGlobalGoal()) {
            nbTours++;

            for (Agent agent : this.listeAgents) {
                agent.perception();
                int r1 = new Random().nextInt(environment.getLongMap());
                int r2 = new Random().nextInt(environment.getLargeMap());
                agent.move(r1, r2);
                setChanged();
                notifyObservers();
                try {
                    Thread.sleep(tempspause);
                } catch (InterruptedException ignored) {

                }
            }
            this.score = nbTours;
            setChanged();
            notifyObservers();
            try {
                Thread.sleep(500);
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
        Thread worker = new Thread(this);
        worker.start();
        setChanged();
        notifyObservers();
    }

    public int getScore() {
        return this.score;
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public List<Agent> getListeAgents() {
        return listeAgents;
    }
}