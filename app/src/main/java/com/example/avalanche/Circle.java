package com.example.avalanche;

import android.graphics.Point;

public abstract class Circle {
    protected int diameter;
    protected Point coordinates;

    public Circle (int x, int y, int diameter) {
        this.diameter = diameter;
        coordinates = new Point(x-diameter/2, y-diameter/2);
        this.coordinates.x = x-diameter/2;
        this.coordinates.y = y-diameter/2;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public int getDiameter() {
        return diameter;
    }

    public boolean collision(Circle circle) {
        // (r1+r2)^2>=(x1-x2)^2+(y1-y2)^2, x1, y1 - wspolrzedne srodka okregu
        return (diameter/2 + circle.diameter/2) * (diameter/2 + circle.diameter/2) >=
                (coordinates.x + diameter/2 - circle.coordinates.x - circle.diameter/2) * (coordinates.x + diameter/2 - circle.coordinates.x - circle.diameter/2) +
                        (coordinates.y + diameter/2 - circle.coordinates.y - circle.diameter/2) * (coordinates.y + diameter/2 - circle.coordinates.y - circle.diameter/2);
    }
}
