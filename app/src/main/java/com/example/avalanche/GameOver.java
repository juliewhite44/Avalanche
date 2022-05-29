package com.example.avalanche;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class GameOver extends Activity {
    Button back;
    Button playAgain;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        TextView gameOverText = findViewById(R.id.game_over_text);
        gameOverText.setText("Game Over\nScore:" + getIntent().getExtras().getLong("score"));

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
