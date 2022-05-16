package com.example.avalanche;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity implements SensorEventListener, Runnable {

    //Button back;
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
        //setContentView(R.layout.play);

        //back = (Button) findViewById(R.id.back);
        //back.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(GameActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });

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
            gameModel.update();
            gameView.draw(gameModel.getBackgroundPoint1(), gameModel.getBackgroundPoint2(),
                    gameModel.getBall(), gameModel.getObstacles());
            timeDiff = System.currentTimeMillis() - beginTime;

            try {
                Thread.sleep((int) Math.max(0, (int)17-timeDiff));
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
