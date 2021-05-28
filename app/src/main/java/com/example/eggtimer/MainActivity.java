package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.zip.CheckedOutputStream;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBar;
    TextView textView;
    CountDownTimer count;
    boolean isTimerOn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = findViewById(R.id.timerSeekBar);
        textView = findViewById(R.id.timerDisplay);
        basicSeekBar();
    }

    public void startTimer(View view){


        Button start = findViewById(R.id.goButton);
        String state = (String) start.getText();

        isTimerOn = true;
        if(state.equals("START")) {
            start.setText("Stop");
            count = new CountDownTimer(seekBar.getProgress() * 1000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    setTimerDisplay((int) millisUntilFinished / 1000);

                }

                @Override
                public void onFinish() {
                    isTimerOn = false;
                    MediaPlayer mediaPlayer = new MediaPlayer().create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();
                    start.setText("START");
                }
            }.start();
        }
        else{
            count.cancel();
            seekBar.setProgress(30);
            textView.setText("00:30");
            start.setText("START");
        }



    }
    public void setTimerDisplay(int progress){
        int minutes = secsToMinutes(progress);
        progress -= minutes * 60;
        if(progress < 10)
            textView.setText(Integer.toString(minutes)+ ":0" + Integer.toString(progress));
        else
            textView.setText(Integer.toString(minutes)+":"+ Integer.toString(progress));

    }




    public void basicSeekBar(){
        seekBar.setProgress(30);
        seekBar.setMax(600);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(isTimerOn)
                    count.cancel();
                setTimerDisplay(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public int secsToMinutes(int seconds){
       return seconds/60;
    }
}