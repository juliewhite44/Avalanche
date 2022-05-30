package com.example.avalanche;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startService(new Intent(MainActivity.this, ThemeSoundService.class));

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        Button play = findViewById(R.id.play);
        play.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(intent);
        });

        Button highscores = findViewById(R.id.highscores);
        highscores.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Highscores.class);
            startActivity(intent);
        });

        Button settings = findViewById(R.id.settings);
        settings.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(MainActivity.this, ThemeSoundService.class));
        super.onDestroy();
    }
}