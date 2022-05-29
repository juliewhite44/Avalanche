package com.example.avalanche;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class InsertUsername extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_username);

        Button gameOver = findViewById(R.id.game_over);
        gameOver.setOnClickListener(view -> {
            EditText inputUsername = findViewById(R.id.editTextUsername);
            setUsernameAndInsertScore(inputUsername.getText().toString());

            Intent intent = new Intent(InsertUsername.this, GameOver.class);
            intent.putExtra("score", getIntent().getExtras().getLong("score"));
            startActivity(intent);
        });
    }

    private void setUsernameAndInsertScore(String username) {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "avalanche-database").allowMainThreadQueries().build();

        ScoreDb scoreDb = new ScoreDb();
        scoreDb.name = username;
        scoreDb.score = getIntent().getExtras().getLong("score");
        db.userDao().insertAll(scoreDb);
    }
}