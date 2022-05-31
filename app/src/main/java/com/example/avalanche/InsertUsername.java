package com.example.avalanche;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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
            //extras for GameOver
            intent.putExtra(Constant.INTENT_EXTRA_SCORE_KEY, getIntent().getExtras().getLong(Constant.INTENT_EXTRA_SCORE_KEY));
            intent.putExtra(Constant.INTENT_EXTRA_TIME_KEY, getIntent().getExtras().getString(Constant.INTENT_EXTRA_TIME_KEY));
            startActivity(intent);
        });
    }

    private void setUsernameAndInsertScore(String username) {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, Constant.DATABASE_NAME).allowMainThreadQueries().build();

        ScoreDb scoreDb = new ScoreDb();
        scoreDb.name = username;
        //extras from gameActivity
        scoreDb.score = getIntent().getExtras().getLong(Constant.INTENT_EXTRA_SCORE_KEY);
        db.scoreDbDao().insertAll(scoreDb);
    }
}