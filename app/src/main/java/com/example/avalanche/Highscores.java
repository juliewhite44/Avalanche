package com.example.avalanche;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.room.Room;

import java.util.List;

public class Highscores extends Activity {
    Button back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscores);

        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Highscores.this, MainActivity.class);
                startActivity(intent);
            }
        });

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "avalanche-database").allowMainThreadQueries().build();
        List<ScoreDb> list = db.userDao().getTopTen();
        TableLayout tableLayout = findViewById(R.id.table);
        for(int i=-1;i<list.size();i++) {
            TableRow tr = new TableRow(Highscores.this);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            if(i==-1) {

                TextView b3=new TextView(Highscores.this);
                b3.setText("");
                b3.setTextColor(Color.BLUE);
                b3.setTextSize(15);
                tr.addView(b3);

                TextView b4=new TextView(Highscores.this);
                b4.setPadding(10, 0, 0, 0);
                b4.setTextSize(15);
                b4.setText("Name");
                b4.setTextColor(Color.BLUE);
                tr.addView(b4);

                TextView b5=new TextView(Highscores.this);
                b5.setPadding(10, 0, 0, 0);
                b5.setText("Score");
                b5.setTextColor(Color.BLUE);
                b5.setTextSize(15);
                tr.addView(b5);
                tableLayout.addView(tr);

                final View vline = new View(Highscores.this);
                vline.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 2));
                vline.setBackgroundColor(Color.BLUE);
                tableLayout.addView(vline); // add line below heading
            }
            else {
                TextView b = new TextView(Highscores.this);
                String str = String.valueOf(i + 1);
                b.setText(str);
                b.setTextColor(Color.RED);
                b.setTextSize(15);
                tr.addView(b);

                TextView b1 = new TextView(Highscores.this);
                b1.setPadding(10, 0, 0, 0);
                b1.setTextSize(15);
                String str1 = list.get(i).name;
                b1.setText(str1);
                b1.setTextColor(Color.RED);
                tr.addView(b1);

                TextView b2 = new TextView(Highscores.this);
                b2.setPadding(10, 0, 0, 0);
                String str2 = String.valueOf(list.get(i).score);
                b2.setText(str2);
                b2.setTextColor(Color.RED);
                b2.setTextSize(15);
                tr.addView(b2);
                tableLayout.addView(tr);
                final View vline1 = new View(Highscores.this);
                vline1.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
                vline1.setBackgroundColor(Color.BLUE);
                tableLayout.addView(vline1);  // add line below each row
            }
        }
    }
}
