package com.example.avalanche;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button play;
    Button highsc;
    Button settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        play = findViewById(R.id.play);
        play.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(intent);
        });

        highsc = findViewById(R.id.highscores);
        highsc.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Highscores.class);
            startActivity(intent);
        });

        settings = findViewById(R.id.settings);
        settings.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);
        });
    }
}