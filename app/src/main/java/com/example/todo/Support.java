package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class Support extends AppCompatActivity {
    SeekBar seekBar;
    Button play, pause,button2;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        seekBar=findViewById(R.id.seekBar);
        button2=findViewById(R.id.button2);
        mediaPlayer=MediaPlayer.create(this,R.raw.record);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    button2.setText("Play");
                    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                            if(b){
                                mediaPlayer.seekTo(i);

                            }
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });
                }
                else{
                    mediaPlayer.start();
                    seekBar.setMax(mediaPlayer.getDuration());
                    button2.setText("Pause");
                    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                            if(b){
                                mediaPlayer.seekTo(i);

                            }
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });
                    mediaPlayer.setLooping(true);
                }
            }
        });

    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
        startActivity(new Intent(Support.this, Categories.class));
        finish();

    }
}