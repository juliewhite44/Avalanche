package com.example.avalanche;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

public class GameOver extends Activity {
    Button back;
    Button playAgain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        back = findViewById(R.id.back);
        back.setOnClickListener(view -> {
            Intent intent = new Intent(GameOver.this, MainActivity.class);
            startActivity(intent);
        });

        playAgain = findViewById(R.id.play_again);
        playAgain.setOnClickListener(view -> {
            Intent intent = new Intent(GameOver.this, GameActivity.class);
            startActivity(intent);
        });
    }
}
