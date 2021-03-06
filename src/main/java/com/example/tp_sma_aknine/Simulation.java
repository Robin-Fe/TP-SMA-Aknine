package com.example.tp_sma_aknine;


import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Simulation extends Observable implements Runnable {

    public Thread worker;
    private final List<Agent> listeAgents;
    private final Environment environment;
    private int score;

    public Simulation(int nbAgents, int xLength, int yLength) {
        double e = 0.1;
        Environment environment = new Environment(xLength, yLength);

        // ToDo : Change politique
        Politique politique = new CornerPolitique();

        MailBox mailBox = new MailBox();
        List<Agent> listeAgents = new ArrayList<>();
        Image imBlanc = new Image("https://www.marianne38.com/wp-content/uploads/cardstock-florence-white-1.jpg", 50, 50, false, false);
        Image imRouge = new Image("http://retraites-vipassana.fr/wp-content/uploads/2016/06/cropped-carre-rouge.jpg", 50, 50, false, false);
        Image imVert = new Image("https://www.ecopro-ascenseurs.fr/wp-content/uploads/2017/12/carre-vert-fonce.png", 50, 50, false, false);
        Image imGris = new Image("https://www.iecd.org/iecd2/wp-content/uploads/2018/09/carre-gris-Copie-2.jpg", 50, 50, false, false);
        Image imBleu = new Image("https://www.couleursetreliefs.fr/wp-content/uploads/2015/02/bleu-indien-n%C2%B069-schjerning-plomb.jpg", 50, 50, false, false);
        Image imCyan = new Image("https://www.png-gratuit.com/img/carre-cyan-fond-transparent.png", 50, 50, false, false);
        Image imJaune = new Image("https://web.static-rmg.be/if/c_fit,w_620,h_412/d7f1632532ac0913168ec989635c4cde.jpg", 50, 50, false, false);
        Image imMagenta = new Image("https://professionnels.tarkett.fr/media/img/M/TH_3912022_3914022_800_800.jpg", 50, 50, false, false);
        Image imOrange = new Image("https://shop.interpon.com/media/catalog/product/cache/3a8bb4233101e96550c567bf8ac59bb5/2/0/2004.png", 50, 50, false, false);
        Image imRose = new Image("https://www.directpapeterie.com/12813-large_default/ramette-papier-couleur-a3-clairefontaine-trophee-80g-500-feuilles-couleur-rose-fuchsia.jpg", 50, 50, false, false);
        Image imMarron = new Image("https://www.couleursetreliefs.fr/wp-content/uploads/2016/05/brun-marron-n%C2%B02043-schjerning-sans-plomb.jpg", 50, 50, false, false);
        Image imVertClair = new Image("https://fitostic.com/wp-content/uploads/2021/08/hqdefault-3.jpg", 50, 50, false, false);
        Image imBleuClair = new Image("https://www.couleursetreliefs.fr/wp-content/uploads/2015/01/bleu-clair-n%C2%B026-schjerning-plomb.jpg", 50, 50, false, false);
        Image imPoussin = new Image("https://www.ecla.net/wp-content/uploads/2019/05/Jaune.jpg", 50, 50, false, false);
        Image imBrun = new Image("https://www.ceradel.fr/6101-large_default/fond-couleur-marron-dim33x23cm.jpg", 50, 50, false, false);
        Image imChatain = new Image("https://shop.interpon.com/media/catalog/product/cache/3a8bb4233101e96550c567bf8ac59bb5/8/0/8003_1.png", 50, 50, false, false);
        Image imPink = new Image("https://pro.addictohug.ch/wp-content/uploads/2015/08/rose-3.png", 50, 50, false, false);
        Image imPinkMag = new Image("https://image.freepik.com/photos-gratuite/couleur-rose-electrique-abstraction_23-2147734201.jpg", 50, 50, false, false);
        Image imSaumon = new Image("https://lh3.googleusercontent.com/proxy/_CkNxp8vuo1eQptXSQ8PHvtupHtun21e3V7w8Wlftq9bssQ93QJCFFIbQFnT7wSZZSgmvbhlYoAmmBx2UKFT84Mugw4N6ZF7G9_dI2WC_WGX_7hfw2qfcsTL93bUJqJTOcJIkso-GdWGNQ", 50, 50, false, false);
        Image imJauneOrange = new Image("https://www.sico.ca/cms/getmedia/998b1c88-d8aa-42f6-9262-914093d4bc9d/swatch_orangeade__6507-44.png", 50, 50, false, false);
        Image imAubrun = new Image("https://res.cloudinary.com/ssenseweb/image/upload/w_0.1,q_40,f_auto,dpr_auto/v1548436129/zspqe9fpnqtky4x29zp1.jpg", 50, 50, false, false);
        Image imLGBT1 = new Image("https://thecolor.blog/wp-content/uploads/2021/02/Psicologia-del-color-naranja-930x620.png", 50, 50, false, false);
        Image imArcEnCiel = new Image("https://www.agence-akinai.com/wp-content/uploads/2019/11/couleur-signification-agence-akinai-2019-scaled-2560x1280.jpg?x36124", 50, 50, false, false);
        Image imOskour = new Image("https://fetv.cia-france.com/image/2018/11/5/1_3_colour_squares.gif%28%29%28333D85E1636DAE4AEFE4151D1E79545A%29.gif", 50, 50, false, false);
        listeAgents.add(new Agent(imBlanc, new Coordinate(0,2), new Coordinate(3,1), environment, mailBox, e, politique, "blanc"));
        listeAgents.add(new Agent(imRouge, new Coordinate(1,2), new Coordinate(1,3), environment, mailBox, e, politique, "rouge"));
        listeAgents.add(new Agent(imVert, new Coordinate(3,0), new Coordinate(2,3), environment, mailBox, e, politique, "vert fonc??"));
        listeAgents.add(new Agent(imGris, new Coordinate(4,2), new Coordinate(2,4), environment, mailBox, e, politique, "gris"));
        listeAgents.add(new Agent(imBleu, new Coordinate(2,2), new Coordinate(0,1), environment, mailBox, e, politique, "bleu fonc??"));
        listeAgents.add(new Agent(imCyan, new Coordinate(1,4), new Coordinate(4,0), environment, mailBox, e, politique, "cyan"));
        listeAgents.add(new Agent(imJaune, new Coordinate(4,0), new Coordinate(3,3), environment, mailBox, e, politique, "jaune"));
        listeAgents.add(new Agent(imMagenta, new Coordinate(3,1), new Coordinate(1,4), environment, mailBox, e, politique, "magenta"));
        listeAgents.add(new Agent(imOrange, new Coordinate(0,0), new Coordinate(3,2), environment, mailBox, e, politique, "orange"));
        listeAgents.add(new Agent(imRose, new Coordinate(1,1), new Coordinate(0,4), environment, mailBox, e, politique, "rose"));
        listeAgents.add(new Agent(imMarron, new Coordinate(3,3), new Coordinate(4,3), environment, mailBox, e, politique, "marron"));
        listeAgents.add(new Agent(imVertClair, new Coordinate(4,4), new Coordinate(0,3), environment, mailBox, e, politique, "vert clair"));
        listeAgents.add(new Agent(imBleuClair, new Coordinate(0,1), new Coordinate(4,1), environment, mailBox, e, politique, "bleu clair"));
        listeAgents.add(new Agent(imPoussin, new Coordinate(0,3), new Coordinate(3,4), environment, mailBox, e, politique, "poussin"));
        listeAgents.add(new Agent(imBrun, new Coordinate(1,0), new Coordinate(1,2), environment, mailBox, e, politique, "brun"));
        listeAgents.add(new Agent(imChatain, new Coordinate(3,2), new Coordinate(2,1), environment, mailBox, e, politique, "chatain"));
        listeAgents.add(new Agent(imPink, new Coordinate(4,1), new Coordinate(2,2), environment, mailBox, e, politique, "pink"));
        listeAgents.add(new Agent(imPinkMag, new Coordinate(2,0), new Coordinate(0,2), environment, mailBox, e, politique, "pinkMag"));
        listeAgents.add(new Agent(imSaumon, new Coordinate(2,1), new Coordinate(4,2), environment, mailBox, e, politique, "saumon"));
        listeAgents.add(new Agent(imJauneOrange, new Coordinate(1,3), new Coordinate(1,1), environment, mailBox, e, politique, "jaune orange"));
        listeAgents.add(new Agent(imAubrun, new Coordinate(0,4), new Coordinate(1,0), environment, mailBox, e, politique, "aubrun"));
        listeAgents.add(new Agent(imLGBT1, new Coordinate(2,4), new Coordinate(0,0), environment, mailBox, e, politique, "lgbt"));
        listeAgents.add(new Agent(imArcEnCiel, new Coordinate(3,4), new Coordinate(2,0), environment, mailBox, e, politique, "arc en ciel"));
        listeAgents.add(new Agent(imOskour, new Coordinate(2,3), new Coordinate(4,4), environment, mailBox, e, politique, "oskour"));
        listeAgents = listeAgents.subList(0, nbAgents);

        environment.setListeAgents(listeAgents);
        this.listeAgents = listeAgents;
        this.environment = environment;
        this.score = 0;
    }


    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        while (!this.listeAgents.get(0).checkGlobalGoal()) {
            this.score = Math.round((System.currentTimeMillis() - startTime) / 1000);
            setChanged();
            notifyObservers();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {

            }
        }
    }


    public void interrupt() {
        for (Agent agent : this.listeAgents) {
            agent.interrupt();
        }
        worker.interrupt();
        setChanged();
        notifyObservers();
    }

    public void start() {
        this.worker = new Thread(this);
        this.worker.start();
        setChanged();
        notifyObservers();
        for (Agent agent : this.listeAgents) {
            agent.start();
        }
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