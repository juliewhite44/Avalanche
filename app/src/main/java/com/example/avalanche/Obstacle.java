package com.example.avalanche;

public class Obstacle extends Circle {
    private boolean deletionFlag;
    private boolean isRed;

    public Obstacle(int x, int y, int diameter, boolean isRed) {
        super(x, y, diameter);
        deletionFlag = false;
        this.isRed = isRed;
    }

    public void updateObstacle(int screenHeight, double speedMultiplication) {
        coordinates.y += screenHeight*speedMultiplication;
        if(coordinates.y > screenHeight) deletionFlag = true;
    }

    public boolean isDeletionFlag() {
        return deletionFlag;
    }

    public void setDeletionFlag(boolean deletionFlag) {
        this.deletionFlag = deletionFlag;
    }

    public boolean isRed() {
        return isRed;
    }
}
