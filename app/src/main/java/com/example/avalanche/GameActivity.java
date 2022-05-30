package com.example.avalanche;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity implements SensorEventListener, Runnable {

    private GameView gameView;
    private GameModel gameModel;
    private SensorManager sensorManager = null;
    private boolean isPlaying;
    private Thread thread;
    private int height, width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_GAME);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        gameView = new GameView(this, width, height);
        gameModel = new GameModel(width, height);

        setContentView(gameView);
    }



    @Override
    protected void onPause() {
        super.onPause();
        pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
        resume();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            gameModel.getBall().updateBall(sensorEvent.values[2], width);
        }
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onStop() {
        sensorManager.unregisterListener(this);
        super.onStop();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void run() {
        long beginTime;
        long timeDiff;
        while(isPlaying) {
            beginTime = System.currentTimeMillis();
            if(!gameModel.update()) {
                Intent intent = new Intent(GameActivity.this, InsertUsername.class);
                intent.putExtra(Constant.INTENT_EXTRA_SCORE_KEY, gameModel.getScore());

                Date date = new Date();
                long milisFromGameStart = date.getTime() - gameView.getStartTime();
                int seconds = (int) (milisFromGameStart / 1000) % 60 ;
                int minutes = (int) ((milisFromGameStart / (1000*60)) % 60);
                int hours   = (int) ((milisFromGameStart / (1000*60*60)) % 24);
                @SuppressLint("DefaultLocale") String displayTime = String.format("%01d:%02d:%02d", hours, minutes, seconds);
                intent.putExtra(Constant.INTENT_EXTRA_TIME_KEY, displayTime);
                startActivity(intent);
            }
            if(gameModel.isCollisionNow()) startService(new Intent(GameActivity.this, SnowballSoundService.class));
            gameView.draw(gameModel.getUpper_left_point1(), gameModel.getUpper_left_point2(),
                    gameModel.getBall(), gameModel.getObstacles(), gameModel.getScore());
            timeDiff = System.currentTimeMillis() - beginTime;

            try {
                Thread.sleep((int) Math.max(0, Constant.FRAME_LENGTH-timeDiff));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        isPlaying = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
