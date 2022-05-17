package com.example.avalanche;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.SurfaceView;

import java.util.Date;
import java.util.LinkedList;

public class GameView extends SurfaceView {
    private final int height, width;
    private final Paint paint;
    private Bitmap backgroundBitmap;
    private final Bitmap ballBitmap;
    private Bitmap obstacleGreenBitmap;
    private Bitmap obstacleRedBitmap;
    private long startTime;


    public GameView(Context context, int width, int height) {
        super(context);
        this.height = height;
        this.width = width;
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        backgroundBitmap = Bitmap.createScaledBitmap(backgroundBitmap, width, height, false);
        ballBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.snowball);
        obstacleGreenBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tree);
        obstacleRedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.red_tree);
        Date date = new Date();
        startTime = date.getTime();
    }

    public void draw(Point backgroundPoint1, Point backgroundPoint2, Ball ball, LinkedList<Obstacle> Obstacles, long score) {
        if(getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(backgroundBitmap, backgroundPoint1.x, backgroundPoint1.y, paint);
            canvas.drawBitmap(backgroundBitmap, backgroundPoint2.x, backgroundPoint2.y, paint);

            Bitmap newBallBitmap = Bitmap.createScaledBitmap(ballBitmap, ball.getDiameter(), ball.getDiameter(), false);

            canvas.drawBitmap(newBallBitmap, ball.getCoordinates().x, ball.getCoordinates().y, paint);

            for(Obstacle obstacle : Obstacles) {
                Bitmap newObstacleBitmap;
                if(obstacle.isRed()) newObstacleBitmap = Bitmap.createScaledBitmap(obstacleRedBitmap, obstacle.getDiameter(), obstacle.getDiameter(), false);
                else newObstacleBitmap = Bitmap.createScaledBitmap(obstacleGreenBitmap, obstacle.getDiameter(), obstacle.getDiameter(), false);
                canvas.drawBitmap(newObstacleBitmap, obstacle.getCoordinates().x, obstacle.getCoordinates().y, paint);
            }

            Date date = new Date();
            long milisFromGameStart = date.getTime() - startTime;
            int seconds = (int) (milisFromGameStart / 1000) % 60 ;
            int minutes = (int) ((milisFromGameStart / (1000*60)) % 60);
            int hours   = (int) ((milisFromGameStart / (1000*60*60)) % 24);
            @SuppressLint("DefaultLocale") String displayTime = String.format("%01d:%02d:%02d", hours, minutes, seconds);

            canvas.drawText(displayTime, 10, 50, paint);

            canvas.drawText(String.valueOf(score), 200, 50, paint);

            getHolder().unlockCanvasAndPost(canvas);
        }
    }
}
