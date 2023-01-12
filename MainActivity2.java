package com.example.johnssmarthomeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.Map;
import java.util.TreeMap;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{

    //Initialize Variables
    private String action, actionID;
    private TextView txtViewIntro;
    private VideoView videoVideo;
    private String videoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Set Buttons
        Button btnPractice = findViewById(R.id.btnPractice);
        btnPractice.setOnClickListener(this);

        //Get Data From Previous Screen
        Intent intent = getIntent();
        action = intent.getStringExtra("action");
        actionID = intent.getStringExtra("actionID");

        //Initialize
        txtViewIntro = findViewById(R.id.txtViewIntro2);
        videoVideo = findViewById(R.id.videoView);

        //Change Intro
        txtViewIntro.setText(action);

        //Change Video
        Uri uri = Uri.parse(VideoPath(actionID));
        videoVideo.setVideoURI(uri);

        //Add Media Controller
        MediaController mediaController = new MediaController(this);     //Creates Video Controller
        videoVideo.setMediaController(mediaController);                         //Set Media Controller to Video
        mediaController.setAnchorView(videoVideo);                              //Anchor Media Controller position to Video

        //Auto Play
        //videoVideo.seekTo(1);
        videoVideo.start();

        // Loop
        videoVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });

    }

    private String VideoPath(String ID) {

        Map<String, Integer> pathPair = new TreeMap<String,Integer>();
        pathPair.put("lighton",R.raw.lighton);
        pathPair.put("lightoff",R.raw.lightoff);
        pathPair.put("fanon",R.raw.fanon);
        pathPair.put("fanoff",R.raw.fanoff);
        pathPair.put("fanup",R.raw.fanup);
        pathPair.put("fandown",R.raw.fandown);
        pathPair.put("setthermo",R.raw.setthermo);
        pathPair.put("num0",R.raw.num0);
        pathPair.put("num1",R.raw.num1);
        pathPair.put("num2",R.raw.num2);
        pathPair.put("num3",R.raw.num3);
        pathPair.put("num4",R.raw.num4);
        pathPair.put("num5",R.raw.num5);
        pathPair.put("num6",R.raw.num6);
        pathPair.put("num7",R.raw.num7);
        pathPair.put("num8",R.raw.num8);
        pathPair.put("num9",R.raw.num9);

        String path = "android.resource://" + getPackageName() + "/" + pathPair.get(ID.toLowerCase());

        return path;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPractice:
                PracticeGesture();
                break;
            default:
                break;
        }
    }

    private void PracticeGesture(){
        // To Next Screen
        Intent myIntent = new Intent(MainActivity2.this,MainActivity3.class);
        myIntent.putExtra("actionID",actionID);
        myIntent.putExtra("action",action);
        startActivity(myIntent);
    }
}