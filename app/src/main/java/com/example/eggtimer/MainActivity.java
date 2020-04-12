package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    TextView timeTextView;
    SeekBar seekBar;
    Button button;
    Button button1;
    Boolean timerIsActive=false;
    CountDownTimer countDownTimer;

    public void resetTimer(){
        textView.setText("0:30");
        seekBar.setProgress(30);
        seekBar.setEnabled(true);
        countDownTimer.cancel();
        button.setText("Start");
        button.setBackgroundColor(Color.parseColor("#4CAF50"));
        timerIsActive=false;
        timeTextView.setVisibility(View.INVISIBLE);
    }


    public void button(View view) {
        if (timerIsActive) {
            resetTimer();

        } else {


            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    updateTimer((int) millisUntilFinished / 1000);


                }

                @Override
                public void onFinish() {

                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.air_horn);
                    mediaPlayer.start();

                    resetTimer();
                }
            }.start();


            timerIsActive = true;
            seekBar.setEnabled(false);
            button.setText("Stop");
            button.setBackgroundColor(Color.parseColor("#DC143C"));

        }
    }

    public void resetButton(View view){
        resetTimer();
    }

    public void updateTimer(int secondsLeft){
        int minutes=secondsLeft/60;
        int seconds=secondsLeft - (minutes * 60);;

        String secondString=Integer.toString(seconds);


        if(seconds<=5 && seconds!=0 && minutes<1){

            secondString="0"+secondString;
            timeTextView.setVisibility(View.VISIBLE);
            timeTextView.setText(secondString+" Seconds left");

        }
        else if(seconds==0 && minutes<=1){
            timeTextView.setText("TIME UP!");
        }
        else if(seconds<=9){
            secondString="0"+secondString;
        }

        textView.setText(Integer.toString(minutes) + ":" + secondString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar=findViewById(R.id.seekBar);
        textView=findViewById(R.id.textView);
        timeTextView=findViewById(R.id.timeTextView);
        button=findViewById(R.id.button);
        button1=findViewById(R.id.button);

        seekBar.setMax(600);
        seekBar.setProgress(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
                timeTextView.setVisibility(View.INVISIBLE);



            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                timeTextView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                timeTextView.setVisibility(View.INVISIBLE);

            }
        });


    }
}
