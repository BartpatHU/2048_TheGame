package hu.projekt;

import hu.projekt.dao.ScoreDAO;
import hu.projekt.dao.ScoreDAOImpl;
import hu.projekt.model.Score;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GameView extends Stage {

    protected int GAME_SIZE;
    protected static int TILE_SIZE = 100;

    public GameView(int size) {
        this.GAME_SIZE = size;



        setTitle("2048_Kötelező_Program");

        FlowPane rootNode = new FlowPane();

        setResizable(false);
        setOnCloseRequest(event -> Platform.exit());


        Game game = new Game(GAME_SIZE);

        Scene scene = new Scene(rootNode, game.getWidth(), game.getHeight());
        setScene(scene);


        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (event.getCode() == KeyCode.ENTER) {
                    game.resetGame();
                }

                if (!game.canMove() || (!game.win && !game.canMove())) {
                    game.lose = true;
                }

                if (!game.win && !game.lose) {
                    switch (event.getCode()) {
                        case LEFT:
                            game.left();
                            break;
                        case RIGHT:
                            game.right();
                            break;
                        case DOWN:
                            game.down();
                            break;
                        case UP:
                            game.up();
                            break;
                    }
                }
                game.relocate(GAME_SIZE *100, GAME_SIZE *100);
            }
        });

        rootNode.getChildren().add(game);

        show();


        new AnimationTimer() {
            @Override
            public void handle(long now) {
                GraphicsContext design = game.getGraphicsContext2D();
                design.setFill(Color.rgb(55, 55, 55));
                design.fillRect(0, 0, game.getWidth(), game.getHeight());

                for(int y = 0; y < GAME_SIZE; y++) {
                    for(int x = 0; x < GAME_SIZE; x++){
                        Tile tile = game.getTiles()[x + y * GAME_SIZE];
                        int value = tile.number;
                        double xOffset = coordinates(x, GAME_SIZE);
                        double yOffset = coordinates(y, GAME_SIZE);

                        design.setFill(tile.getCell());
                        design.fillRoundRect(xOffset, yOffset, TILE_SIZE-10, TILE_SIZE-10, 10, 10);
                        design.setFill(tile.getForeground());

                        final int asd = value < 100 ? 32 : value < 1000 ? 28 : 24;
                        design.setFont(Font.font("Arial", FontWeight.BOLD, asd));
                        design.setTextAlign(TextAlignment.CENTER);


                        String num = String.valueOf(value);

                        if (value != 0){
                            design.fillText(num, xOffset + TILE_SIZE / 2-5, yOffset + TILE_SIZE / 2 +8);
                        }


                        design.setFont(Font.font("Arial", FontWeight.BOLD, 25));
                        design.setFill(Color.WHITE);
                        design.fillText("Score: ", TILE_SIZE* GAME_SIZE /2-30, (TILE_SIZE* GAME_SIZE +60));
                        design.setFill(Color.rgb(0,200,0));
                        design.fillText( "" + game.score, TILE_SIZE* GAME_SIZE /2+50, (TILE_SIZE* GAME_SIZE +60));

                        if(game.win || game.lose) {

                            if(game.lose) {
                                design.fillText("Game over!", TILE_SIZE* GAME_SIZE /2, TILE_SIZE* GAME_SIZE /10*3);
                            }
                            if(game.win){
                                design.fillText("You won!", TILE_SIZE* GAME_SIZE /2, TILE_SIZE* GAME_SIZE /10*3);
                            }
                            design.setFont(Font.font("Arial", FontWeight.LIGHT, 20));
                            design.setFill(Color.WHITE);
                            design.fillText("Press ENTER to restart the game", TILE_SIZE* GAME_SIZE /2, (TILE_SIZE* GAME_SIZE +100)/10*7);
                            design.setFill(Color.rgb(55, 55, 55));
                            design.fillRect(0, 0, game.getWidth(), game.getHeight());
                            design.setFill(Color.WHITE);
                            design.setFont(Font.font("Arial", FontWeight.BOLD, 40));
                        }
                    }
                }
            }
        }.start();





    }

    private static double coordinates(int input, int size) {
        return input * (TILE_SIZE) + 5;
    }
}
