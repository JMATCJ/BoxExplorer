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
    private AnimationTimer gameLoop;
    private Game game;

    @Override
    public void init() throws Exception {
        AssetLoader.initialize(getParameters().getRaw().contains("-nomusic"));
        game = new Game();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(1280, 720);
        Scene scene = new Scene(new Group(canvas), 1270, 710);
        primaryStage.setScene(scene);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        scene.setOnMouseMoved(event -> game.queueEvent(event));
        scene.setOnMouseClicked(event -> game.queueEvent(event));
        scene.setOnKeyPressed(event -> game.addToSet(event));
        scene.setOnKeyReleased(event -> game.removeFromSet(event));

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                game.update(now);

                gc.clearRect(0, 0, 1280, 720);
                game.draw(gc);
            }
        };

        gameLoop.start();

        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
//        game.saveData();
        gameLoop.stop();
    }

    public static void main(String[] args) {
        launch(LDJam43.class, args);
    }
}
