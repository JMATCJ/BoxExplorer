package com.github.jmatcj.ld43;

import com.github.jmatcj.ld43.util.AssetLoader;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class LDJam43 extends Application {
    public static final int SCREEN_WIDTH = 768;
    public static final int SCREEN_HEIGHT = 768;

    private static Game game;
    private AnimationTimer gameLoop;

    /*
     * IF YOU EVER NEED AN INSTANCE OF GAME
     * AND YOU DON'T HAVE IT, ACQUIRE IT HERE!
     */
    public static Game getGame() {
        return game;
    }

    @Override
    public void init() throws Exception {
        AssetLoader.initialize(getParameters().getRaw().contains("-nomusic"));
        game = new Game();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        Scene scene = new Scene(new Group(canvas), SCREEN_WIDTH - 10, SCREEN_HEIGHT - 10);
        primaryStage.setScene(scene);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        scene.setOnMouseMoved(event -> game.queueEvent(event));
        scene.setOnMouseClicked(event -> game.queueEvent(event));
        scene.setOnKeyPressed(event -> game.addToSet(event));
        scene.setOnKeyReleased(event -> game.removeFromSet(event));

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (game.getNextArea()) {
                    game.setNextArea();
                    game.remakeEverything();
                }
                game.handleRoomTransition();
                game.update(now);

                gc.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                game.draw(gc);
            }
        };

        gameLoop.start();

        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        gameLoop.stop();
    }

    public static void main(String[] args) {
        launch(LDJam43.class, args);
    }
}
