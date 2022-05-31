package com.example.avalanche;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.room.Room;

import java.util.List;

public class Highscores extends Activity {
    private void generateTable() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                "avalanche-database").allowMainThreadQueries().build();
        List<ScoreDb> list = db.scoreDbDao().getTopTen();
        TableLayout tableLayout = findViewById(R.id.table);
        // -1 because of headers
        for(int i = -1; i < list.size(); i++) {
            TableRow tr = new TableRow(Highscores.this);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            TextView textView = new TextView(Highscores.this);

            if(i == -1) {
                textView.setText("");
                textView.setTextColor(Color.BLUE);
            }
            else {
                textView.setText(String.valueOf(i + 1));
                textView.setTextColor(Color.RED);
            }

            textView.setTextSize(15);
            textView.setPadding(0, 0, 0, 0);
            tr.addView(textView);

            textView = new TextView(Highscores.this);
            textView.setTextSize(15);
            textView.setPadding(10, 0, 0, 0);

            if(i == -1) {
                textView.setText(Constant.HIGHSCORE_COLUMN_NAME_TEXT);
                textView.setTextColor(Color.BLUE);
            }
            else {
                textView.setText(list.get(i).name);
                textView.setTextColor(Color.RED);
            }

            tr.addView(textView);
            textView = new TextView(Highscores.this);
            textView.setTextSize(15);
            textView.setPadding(10, 0, 0, 0);
            if(i == -1) {
                textView.setText(Constant.HIGHSCORE_COLUMN_SCORE_TEXT);
                textView.setTextColor(Color.BLUE);
            }
            else {
                textView.setText(String.valueOf(list.get(i).score));
                textView.setTextColor(Color.RED);
            }

            tr.addView(textView);
            tableLayout.addView(tr);

            View verticalLine = new View(Highscores.this);
            verticalLine.setLayoutParams(new
                    TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 2));
            verticalLine.setBackgroundColor(Color.BLUE);
            tableLayout.addView(verticalLine); // add line below heading
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscores);

        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Highscores.this, MainActivity.class);
                startActivity(intent);
            }
        });

        generateTable();
    }
}
