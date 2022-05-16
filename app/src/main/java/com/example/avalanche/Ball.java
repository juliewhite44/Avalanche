package com.example.avalanche;

public class Ball extends Circle {
    private int sizeCounter = 0;

    public Ball(int x, int y, int diameter) {
        super(x, y, diameter);
    }

    public void updateBall(float xAcceleration, int screenWidth) {
        coordinates.x -= xAcceleration*0.5;
        if (coordinates.x > screenWidth - diameter) {
            coordinates.x = screenWidth - diameter;
        }
        else if (coordinates.x < 0) {
            coordinates.x = 0;
        }
    }

    public void increaseSize() {
        sizeCounter++;
        if(sizeCounter == 4) {
            sizeCounter = 0;
            diameter++;
        }
    }
    public void decreaseSize() {
        diameter /= 2;
    }
}
