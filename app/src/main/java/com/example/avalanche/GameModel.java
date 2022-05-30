package com.example.avalanche;

import android.content.Intent;
import android.graphics.Point;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.LinkedList;
import java.util.Random;

public class GameModel {

    private final Ball ball;
    private final int height;
    private final int width;
    private final Point upper_left_point1;
    private final Point upper_left_point2;
    private LinkedList<Obstacle> Obstacles;
    private int updateCounter;
    private double speedMultiplication;
    private long score;
    private boolean isCollisionNow;

    GameModel(int width, int height) {
        this.width = width;
        this.height = height;
        ball = new Ball((int)(Constant.BALL_PLACEMENT_X*width), (int)(Constant.BALL_PLACEMENT_Y*height), Constant.BALL_DIAMETER);
        upper_left_point1 = new Point(0, 0);
        upper_left_point2 = new Point(0, -height);
        updateCounter = Constant.FRAME_DELAY;
        Obstacles = new LinkedList<>();
        score = 0;
    }

    private Obstacle getNewObstacle() {
        Random random = new Random();
        int randomNumber = random.nextInt(5);
        Obstacle obstacle;
        if(randomNumber == 0) obstacle = new Obstacle(random.nextInt(width), 0, Constant.OBSTACLE_DIAMETER, true);
        else obstacle = new Obstacle(random.nextInt(width), 0, Constant.OBSTACLE_DIAMETER, false);
        return obstacle;
    }

    private void moveBackground() {
        if(upper_left_point1.y >= 0) {
            upper_left_point1.y += height*speedMultiplication;
            upper_left_point2.y = upper_left_point1.y - height;
            if(upper_left_point1.y > height) {
                upper_left_point1.y = -height;
            }
        }
        else {
            upper_left_point2.y += height*speedMultiplication;
            upper_left_point1.y = upper_left_point2.y - height;
            if(upper_left_point2.y > height) {
                upper_left_point2.y = -height;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean update() {
        isCollisionNow = false;
        if(updateCounter < 0) {
            updateCounter++;
            return true;
        }
        if(updateCounter == Constant.FRAMES_BETWEEN_OBJECTS) {
            updateCounter = 0;
            Obstacles.add(getNewObstacle());
        }
        updateCounter++;
        ball.increaseSize();

        speedMultiplication = Constant.SPEED_MULTIPLIER*Math.sqrt(ball.diameter);

        moveBackground();


        for(Obstacle obstacle: Obstacles) {
            obstacle.updateObstacle(height, speedMultiplication);
            if(ball.collision(obstacle)) {
                isCollisionNow = true;
                if(obstacle.isRed()) score+= (long) ball.getDiameter() * ball.getDiameter();
                obstacle.setDeletionFlag(true);
                ball.decreaseSize();
            }
        }
        Obstacles.removeIf(Obstacle::isDeletionFlag);
        if(Settings.isChillMode()) return true;
        return !ball.isBallTooSmall();
    }

    public Point getUpper_left_point1() {
        return upper_left_point1;
    }

    public Point getUpper_left_point2() {
        return upper_left_point2;
    }

    public Ball getBall() {
        return ball;
    }

    public LinkedList<Obstacle> getObstacles() {
        return Obstacles;
    }

    public long getScore() {
        return score;
    }

    public boolean isCollisionNow() {
        return isCollisionNow;
    }
}
