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

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.setTitle("Simulation de tri multi-agents");


        Simulation simulation = new Simulation(5, 5, 5);

        GridPane grid = new GridPane();
        GridPane grid2 = new GridPane();

        BorderPane rootMenu = new BorderPane();

        Scene sceneMenu = new Scene(rootMenu, 400, 400);
        Scene sceneJeu;
/*
        Image imAgent = new Image("https://media.istockphoto.com/vectors/businessman-icon-on-a-black-background-white-series-vector-id490019374?k=20&m=490019374&s=170667a&w=0&h=1_NqU8YLKYRIlFz9LbOjUgmQu-bBQQ2__unvHmLJeeM=", 19, 18, false, false);
        Image imVide = new Image("https://upload.wikimedia.org/wikipedia/commons/7/71/Black.png", 19, 18, false, false);
        Image imHolding = new Image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6D1ZDKRaNaXRQUOFtXKRlTjDDXlNhbYEiuOm2zCRiwmwTf4-tkgflcjlzpe0yA2lc3XA&usqp=CAU", 19, 18, false, false);
        Image imAgentObject = new Image("https://cdn3.iconfinder.com/data/icons/random-04/100/Artboard_1372x-512.png", 19, 18, false, false);
        Image imAObject = new Image("https://i.pinimg.com/originals/8a/71/33/8a71336be732044a9a7cfbb92ee23eb6.jpg", 19, 18, false, false);
        Image imBObject = new Image("https://ak.picdn.net/shutterstock/videos/15506929/thumb/1.jpg", 19, 18, false, false);
*/

        Image imVide = new Image("https://upload.wikimedia.org/wikipedia/commons/7/71/Black.png", 19, 18, false, false);
        Image imAObject = new Image("http://retraites-vipassana.fr/wp-content/uploads/2016/06/cropped-carre-rouge.jpg", 19, 18, false, false);
        Image imBObject = new Image("https://www.ecopro-ascenseurs.fr/wp-content/uploads/2017/12/carre-vert-fonce.png", 19, 18, false, false);
        Image imHolding = new Image("https://www.iecd.org/iecd2/wp-content/uploads/2018/09/carre-gris-Copie-2.jpg", 19, 18, false, false);


        grid2.setAlignment(Pos.CENTER);
        grid2.add(grid, 1, 1);

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
            Simulation simulation2 = new Simulation(simulation.getEnvironment().getNbAgents(), simulation.getEnvironment().getLongMap(), simulation.getEnvironment().getLargeMap());
            int restart = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment recommencer la simulation ?", "UNE SIMULATION EST EN COURS !", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (restart == JOptionPane.OK_OPTION) {
                try {
                    simulation2.start();
                    Platform.runLater(() -> root.setCenter(grid2));
                } catch (Exception ignored) {

                }
            } else
                simulation.start();

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

        rootMenu.setTop(topLabel);
        rootMenu.setLeft(LabelVide);
        rootMenu.setRight(LabelVideD);
        rootMenu.setBackground(new Background(new BackgroundFill(Color.INDIGO, CornerRadii.EMPTY, Insets.EMPTY)));
        rootMenu.setCenter(imgMenu);
        rootMenu.setBottom(boutonMenu);

        ImageView[][] tab = new ImageView[simulation.getEnvironment().getLongMap()][simulation.getEnvironment().getLargeMap()];

        for (int i = 0; i < simulation.getEnvironment().getLongMap(); i++) {
            for (int j = 0; j < simulation.getEnvironment().getLargeMap(); j++) {
                ImageView img = new ImageView();
                tab[i][j] = img;
                grid.add(img, i, j);
            }
        }

        Observer o = (o1, arg) -> {
            Platform.runLater(() -> root.setCenter(grid2));

            final int SIZE_X = simulation.getEnvironment().getLongMap();
            final int SIZE_Y = simulation.getEnvironment().getLargeMap();
            for (int i = 0; i < SIZE_X; i++) {
                for (int j = 0; j < SIZE_Y; j++) {
                    if (simulation.getEnvironment().getContent(i, j) == null) {
                        tab[i][j].setImage(imVide);
                    } else {
                        tab[i][j].setImage(simulation.getEnvironment().getContent(i, j).getImage());
                    }
                }
            }

            Platform.runLater(() -> labelScore.setText("Score  : " + simulation.getScore() + " Points"));
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
