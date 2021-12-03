package com.example.tp_sma_aknine;


import java.util.Observer;
import javax.swing.JOptionPane;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {

        String buttonStyle = """
                -fx-background-color: \r  linear-gradient(#ffd65b, #e68400),\r  linear-gradient(#ffef84, #f2ba44),\r  linear-gradient(#ffea6a, #efaa22),\r\040
                linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),\r  linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));\r
                -fx-background-radius: 30; -fx-background-insets: 0,1,2,3,0; -fx-text-fill: #654b00; -fx-font-weight: bold; -fx-font-size: 30px; -fx-padding: 10 20 10 20;""";
        Font police = new Font("Impact", 30);
        Label topLabel = new Label();
        topLabel.setFont(police);
        topLabel.setStyle("-fx-alignment: center; -fx-background-color:MediumOrchid");
        topLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        topLabel.setMinHeight(100);
        topLabel.setMinWidth(400);

        Label LabelVide = new Label("");
        LabelVide.setFont(police);
        LabelVide.setStyle("-fx-alignment: center; -fx-background-color:MediumOrchid");
        LabelVide.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        LabelVide.setMinHeight(100);
        LabelVide.setMinWidth(400);

        Label LabelVideD = new Label("");
        LabelVideD.setFont(police);
        LabelVideD.setStyle("-fx-alignment: center; -fx-background-color:MediumOrchid");
        LabelVideD.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        LabelVideD.setMinHeight(100);
        LabelVideD.setMinWidth(400);

        Label LabelSim = new Label("Simulation");
        LabelVide.setFont(police);
        LabelVide.setStyle("-fx-alignment: center; -fx-background-color:MediumOrchid; -fx-font-weight: bold; -fx-font-size: 30px");
        LabelVide.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        LabelVide.setMinHeight(100);
        LabelVide.setMinWidth(400);

        Label LabelCible = new Label("Cible");
        LabelVide.setFont(police);
        LabelVide.setStyle("-fx-alignment: center; -fx-background-color:MediumOrchid; -fx-font-weight: bold; -fx-font-size: 30px");
        LabelVide.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        LabelVide.setMinHeight(100);
        LabelVide.setMinWidth(400);

        VBox labelsSIM = new VBox();
        labelsSIM.setMinWidth(300);
        labelsSIM.setStyle("-fx-alignment: center; -fx-background-color:MediumOrchid; -fx-font-weight: bold; -fx-font-size: 30px");
        labelsSIM.setSpacing(40);
        labelsSIM.setAlignment(Pos.CENTER);
        labelsSIM.setPadding(new Insets(10, 10, 100, 10));
        labelsSIM.getChildren().add(LabelSim);

        VBox labelsCIBLE = new VBox();
        labelsCIBLE.setMinWidth(300);
        labelsCIBLE.setStyle("-fx-alignment: center; -fx-background-color:MediumOrchid; -fx-font-weight: bold; -fx-font-size: 30px");
        labelsCIBLE.setSpacing(40);
        labelsCIBLE.setAlignment(Pos.CENTER);
        labelsCIBLE.setPadding(new Insets(10, 10, 100, 10));
        labelsCIBLE.getChildren().add(LabelCible);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.setTitle("Simulation de tri multi-agents");


        Simulation simulation = new Simulation(13, 5, 5);

        GridPane grid = new GridPane();
        GridPane grid3 = new GridPane();
        GridPane grid2 = new GridPane();

        BorderPane rootMenu = new BorderPane();

        Scene sceneMenu = new Scene(rootMenu, 400, 400);
        Scene sceneJeu;

        Image imVide = new Image("https://upload.wikimedia.org/wikipedia/commons/7/71/Black.png", 50, 50, false, false);

        grid2.setAlignment(Pos.CENTER);
        grid2.add(labelsSIM, 1, 1);
        grid2.add(grid, 1, 2);
        grid2.add(labelsCIBLE, 1, 3);
        grid2.add(grid3, 1, 4);
        grid.setPadding(new Insets(0, 0, 10, 0));

        BorderPane root = new BorderPane();

        VBox boutonJeu = new VBox();
        boutonJeu.setMinWidth(300);
        boutonJeu.setStyle("-fx-alignment: center; -fx-background-color:MediumOrchid");
        boutonJeu.setSpacing(40);
        boutonJeu.setAlignment(Pos.CENTER);
        boutonJeu.setPadding(new Insets(10, 10, 100, 10));

        Button btnJeu1 = new Button("Restart");
        btnJeu1.setWrapText(true);
        btnJeu1.setMinHeight(45);
        btnJeu1.setStyle(buttonStyle);
        btnJeu1.setMinWidth(100);
        btnJeu1.setFont(police);
        btnJeu1.setOnMouseClicked((e) -> {
            simulation.interrupt();
            int restart = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment recommencer la simulation ?", "UNE SIMULATION EST EN COURS !", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (restart == JOptionPane.OK_OPTION) {
                try {
                    //simulation.reset();
                    simulation.start();
                    Platform.runLater(() -> root.setCenter(grid2));
                } catch (Exception ignored) {

                }
            } else {
                simulation.start();
            }

        });

        HBox boutonMenu = new HBox();
        boutonMenu.setStyle("-fx-alignment: center; -fx-background-color:MediumOrchid; ");
        boutonMenu.setSpacing(40);
        boutonMenu.setAlignment(Pos.CENTER);
        boutonMenu.setPadding(new Insets(10, 10, 100, 10));

        Button btnJeu2 = new Button("Quitter");
        btnJeu2.setWrapText(true);
        btnJeu2.setMinHeight(45);
        btnJeu2.setStyle(buttonStyle);
        btnJeu2.setMinWidth(100);
        btnJeu2.setFont(police);
        btnJeu2.setOnMouseClicked((e) -> {
            simulation.interrupt();
            int quitter = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment retourner au menu principal ?", "UNE SIMULATION EST EN COURS !", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (quitter == JOptionPane.OK_OPTION) {
                try {
                    topLabel.setText("");
                    rootMenu.setBottom(boutonMenu);
                    primaryStage.setScene(sceneMenu);
                    primaryStage.show();
                    simulation.interrupt();
                } catch (Exception ignored) {

                }
            } else
                simulation.start();

        });

        boutonJeu.getChildren().add(btnJeu1);
        boutonJeu.getChildren().add(btnJeu2);

        Label labelScore = new Label("Tour n° : " + simulation.getScore());
        labelScore.setFont(police);
        labelScore.setMinWidth(300);
        labelScore.setPadding(new Insets(10, 10, 100, 10));
        labelScore.setStyle("-fx-alignment: center; -fx-background-color:MediumOrchid");
        labelScore.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        labelScore.setMinHeight(50);


        VBox labelsJeu = new VBox();
        labelsJeu.setMinWidth(300);
        labelsJeu.setStyle("-fx-alignment: center; -fx-background-color:MediumOrchid");
        labelsJeu.setSpacing(40);
        labelsJeu.setAlignment(Pos.CENTER);
        labelsJeu.setPadding(new Insets(10, 10, 100, 10));
        labelsJeu.getChildren().add(labelScore);


        root.setTop(topLabel);
        root.setBackground(new Background(new BackgroundFill(Color.INDIGO, CornerRadii.EMPTY, Insets.EMPTY)));
        root.setCenter(grid2);
        root.setLeft(boutonJeu);
        root.setRight(labelsJeu);
        sceneJeu = new Scene(root, 300, 300);


        HBox boutonCarte = new HBox();
        boutonCarte.setStyle("-fx-alignment: center; -fx-background-color:MediumOrchid");
        boutonCarte.setSpacing(40);
        boutonCarte.setAlignment(Pos.CENTER);
        boutonCarte.setPadding(new Insets(10, 10, 100, 10));

        HBox boutonNiveau = new HBox();
        boutonNiveau.setStyle("-fx-alignment: center; -fx-background-color:MediumOrchid");
        boutonNiveau.setSpacing(40);
        boutonNiveau.setAlignment(Pos.CENTER);
        boutonNiveau.setPadding(new Insets(10, 10, 100, 10));

        Button btnQuitter = new Button();
        Image imQuit = new Image("http://anpetogo.org/projetprogramme/projet/programmeadmin/img/deco.png", 80, 80, false, false);
        ImageView iconQuitter = new ImageView(imQuit);
        iconQuitter.setPreserveRatio(true);
        btnQuitter.setStyle(buttonStyle);
        btnQuitter.setGraphic(iconQuitter);
        btnQuitter.setOnMouseClicked((e) -> Platform.exit());
        Button btnJouer = new Button();
        Image imJouer = new Image("https://static.vecteezy.com/system/resources/previews/001/186/943/non_2x/green-play-button-png.png", 80, 80, false, false);
        ImageView iconJouer = new ImageView(imJouer);
        btnJouer.setStyle(buttonStyle);
        iconJouer.setPreserveRatio(true);
        btnJouer.setGraphic(iconJouer);
        btnJouer.setOnMouseClicked((e) -> {
            primaryStage.setScene(sceneJeu);
            primaryStage.show();
            simulation.start();
            grid.requestFocus();
        });

        boutonMenu.getChildren().add(btnJouer);
        boutonMenu.getChildren().add(btnQuitter);

        ImageView imgMenu = new ImageView();
        Image iconPM = new Image("https://static.vecteezy.com/system/resources/previews/001/186/943/non_2x/green-play-button-png.png", 200, 200, false, false);
        imgMenu.setImage(iconPM);
        imgMenu.setPreserveRatio(true);
        ImageView bravo = new ImageView();
        Image imBravo = new Image("https://toppng.com/uploads/preview/success-stamp-png-denied-11562900035oyswsfdmg9.png");
        bravo.setImage(imBravo);

        rootMenu.setTop(topLabel);
        rootMenu.setLeft(LabelVide);
        rootMenu.setRight(LabelVideD);
        rootMenu.setBackground(new Background(new BackgroundFill(Color.INDIGO, CornerRadii.EMPTY, Insets.EMPTY)));
        rootMenu.setCenter(imgMenu);
        rootMenu.setBottom(boutonMenu);

        ImageView[][] tab = new ImageView[simulation.getEnvironment().getXLength()][simulation.getEnvironment().getYLength()];

        for (int i = 0; i < simulation.getEnvironment().getXLength(); i++) {
            for (int j = 0; j < simulation.getEnvironment().getYLength(); j++) {
                ImageView img = new ImageView();
                tab[i][j] = img;
                grid.add(img, i, j);
            }
        }

        ImageView[][] tab2 = new ImageView[simulation.getEnvironment().getXLength()][simulation.getEnvironment().getYLength()];

        for (int i = 0; i < simulation.getEnvironment().getXLength(); i++) {
            for (int j = 0; j < simulation.getEnvironment().getYLength(); j++) {
                ImageView img = new ImageView();
                tab2[i][j] = img;
                grid3.add(img, i, j);
            }
        }
        final int SIZE_X = simulation.getEnvironment().getXLength();
        final int SIZE_Y = simulation.getEnvironment().getYLength();
        for (int i = 0; i < SIZE_X; i++) {
            for (int j = 0; j < SIZE_Y; j++) {
                tab2[i][j].setImage(imVide);
            }
        }
        for (Agent agent : simulation.getListeAgents()) {
            tab2[agent.getxGoal()][agent.getyGoal()].setImage(agent.getImage());
        }

        Observer o = (o1, arg) -> {
            Platform.runLater(() -> root.setCenter(grid2));

            for (int i = 0; i < SIZE_X; i++) {
                for (int j = 0; j < SIZE_Y; j++) {
                    if (simulation.getEnvironment().getContent(i, j) == null) {
                        tab[i][j].setImage(imVide);
                    } else {
                        tab[i][j].setImage(simulation.getEnvironment().getContent(i, j).getImage());
                    }
                }
            }

            Platform.runLater(() -> labelScore.setText("Temps écoulé : " + simulation.getScore() + " s"));

            if (simulation.getListeAgents().get(0).checkGlobalGoal()) {
                Platform.runLater(() -> {
                    root.setCenter(bravo);
                    primaryStage.show();
                    simulation.interrupt();
                });
            }
        };

        simulation.addObserver(o);
        simulation.getEnvironment().addObserver(o);
        primaryStage.setScene(sceneMenu);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
