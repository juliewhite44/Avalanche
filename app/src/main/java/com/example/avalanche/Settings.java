package com.example.avalanche;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

public class Settings extends Activity {
    private static boolean chillMode = false;
    public static boolean isChillMode() {
        return chillMode;
    }

    private static boolean sound = true;
    public static boolean isSound() {
        return sound;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button chillModeButton = (Button) findViewById(R.id.chill);
        if(chillMode) chillModeButton.setText(Constant.TURN_CHILL_OFF_TEXT);
        else chillModeButton.setText(Constant.TURN_CHILL_ON_TEXT);
        chillModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chillModeButton.getText().toString().equals(Constant.TURN_CHILL_ON_TEXT)) chillModeButton.setText(Constant.TURN_CHILL_OFF_TEXT);
                else chillModeButton.setText(Constant.TURN_CHILL_ON_TEXT);
                chillMode = !chillMode;
            }
        });

        Button soundButton = (Button) findViewById(R.id.sound);
        if(sound) soundButton.setText(Constant.TURN_SOUND_OFF_TEXT);
        else soundButton.setText(Constant.TURN_SOUND_ON_TEXT);
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(soundButton.getText().toString().equals(Constant.TURN_SOUND_ON_TEXT)) {
                    startService(new Intent(Settings.this, ThemeSoundService.class));
                    soundButton.setText(Constant.TURN_SOUND_OFF_TEXT);
                }
                else {
                    stopService(new Intent(Settings.this, ThemeSoundService.class));
                    soundButton.setText(Constant.TURN_SOUND_ON_TEXT);
                }
                sound = !sound;
            }
        });
    }
}
