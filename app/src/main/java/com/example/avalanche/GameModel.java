package com.example.avalanche;

import android.graphics.Point;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.LinkedList;
import java.util.Random;

public class GameModel {

    private final Ball ball;
    private final int height;
    private final int width;
    private final Point backgroundPoint1;
    private final Point backgroundPoint2;
    private LinkedList<Obstacle> Obstacles;
    private int updateCounter;
    Random random = new Random();
    private double speedMultiplication;

    GameModel(int width, int height) {
        this.width = width;
        this.height = height;
        ball = new Ball(width/2, 3*height/5, 50);
        backgroundPoint1 = new Point(0, 0);
        backgroundPoint2 = new Point(0, -height);
        updateCounter = 0;
        Obstacles = new LinkedList<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void update() {
        if(updateCounter == 40) {
            updateCounter = 0;
            Obstacle obstacle = new Obstacle(random.nextInt(width), 0, 200);
            Obstacles.add(obstacle);
        }
        updateCounter++;
        ball.increaseSize();

        speedMultiplication = 0.00033*Math.sqrt(ball.diameter);

        if(backgroundPoint1.y >= 0) {
            backgroundPoint1.y += height*speedMultiplication;
            backgroundPoint2.y = backgroundPoint1.y - height;
            if(backgroundPoint1.y > height) {
                backgroundPoint1.y = -height;
            }
        }
        else {
            backgroundPoint2.y += height*speedMultiplication;
            backgroundPoint1.y = backgroundPoint2.y - height;
            if(backgroundPoint2.y > height) {
                backgroundPoint2.y = -height;
            }
        }

        for(Obstacle obstacle: Obstacles) {
            obstacle.updateObstacle(height, speedMultiplication);
            if(ball.collision(obstacle)) {
                obstacle.setDeletionFlag(true);
                ball.decreaseSize();
            }
        }
        Obstacles.removeIf(Obstacle::isDeletionFlag);
    }

    public Point getBackgroundPoint1() {
        return backgroundPoint1;
    }

    public Point getBackgroundPoint2() {
        return backgroundPoint2;
    }

    public Ball getBall() {
        return ball;
    }

    public LinkedList<Obstacle> getObstacles() {
        return Obstacles;
    }
}
