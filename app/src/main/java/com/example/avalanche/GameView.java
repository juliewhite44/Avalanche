package com.example.avalanche;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceView;

import java.util.LinkedList;

public class GameView extends SurfaceView {
    private final int height, width;
    private final Paint paint;
    private Bitmap backgroundBitmap;
    private final Bitmap ballBitmap;
    private Bitmap obstacleBitmap;


    public GameView(Context context, int width, int height) {
        super(context);
        this.height = height;
        this.width = width;
        paint = new Paint();
        backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        backgroundBitmap = Bitmap.createScaledBitmap(backgroundBitmap, width, height, false);
        ballBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.snowball);
        obstacleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tree);
    }

    public void draw(Point backgroundPoint1, Point backgroundPoint2, Ball ball, LinkedList<Obstacle> Obstacles) {
        if(getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(backgroundBitmap, backgroundPoint1.x, backgroundPoint1.y, paint);
            canvas.drawBitmap(backgroundBitmap, backgroundPoint2.x, backgroundPoint2.y, paint);

            Bitmap newBallBitmap = Bitmap.createScaledBitmap(ballBitmap, ball.getDiameter(), ball.getDiameter(), false);

            canvas.drawBitmap(newBallBitmap, ball.getCoordinates().x, ball.getCoordinates().y, paint);

            for(Obstacle obstacle : Obstacles) {
                Bitmap newObstacleBitmap = Bitmap.createScaledBitmap(obstacleBitmap, obstacle.getDiameter(), obstacle.getDiameter(), false);
                canvas.drawBitmap(newObstacleBitmap, obstacle.getCoordinates().x, obstacle.getCoordinates().y, paint);
            }

            getHolder().unlockCanvasAndPost(canvas);
        }
    }
}
