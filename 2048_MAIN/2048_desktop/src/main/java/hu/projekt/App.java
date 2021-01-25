package hu.projekt;

import hu.projekt.dao.ScoreDAO;
import hu.projekt.dao.ScoreDAOImpl;
import hu.projekt.model.Score;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;


public class App extends Application {






    @Override
    public void start(Stage stage) throws Exception{

        HBox hbox = new HBox();

        Text text0 = new Text("4x4");
        Text text1 = new Text("5x5");
        Text text2 = new Text("6x6");
        Text text3 = new Text("7x7");
        Text text4 = new Text("8x8");


        text0.setOnMouseClicked(e -> {
            int size = 4;
            GameView game = new GameView(size);
        });
        text1.setOnMouseClicked(e -> {
            int size = 5;
            GameView game = new GameView(size);
        });
        text2.setOnMouseClicked(e -> {
            int size = 6;
            GameView game = new GameView(size);
        });
        text3.setOnMouseClicked(e -> {
            int size = 7;
            GameView game = new GameView(size);
        });
        text4.setOnMouseClicked(e -> {
            int size = 8;
            GameView game = new GameView(size);
        });


        hbox.getChildren().addAll(text0,text1,text2,text3,text4);

        Scene scene = new Scene(hbox,400,200);

        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);


    }
}
