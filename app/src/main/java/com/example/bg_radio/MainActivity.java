package com.example.bg_radio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MediaMetadata;
import androidx.media3.exoplayer.ExoPlayer;

import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    String linkBg = "http://play.global.audio/bgradio.ogg";
    String link1rock = "http://149.13.0.81/radio1rock.ogg";
    TextView viewSong;
    TextView viewArtist;
    TextClock clock;
    ExoPlayer player;
    Button btnBgRadio;
    Button btn_1rock;
    List<MediaItem> stations = new ArrayList<>();
    MediaMetadataRetriever retriever;
    String title;
    String[] titleSplit = {"No data", "No data"};
    Timer timer = new Timer();
    String currentUrl = link1rock;

        @Override
                protected void onResume(){
            super.onResume();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    String[] data = getData(currentUrl);
                    displayData(data);
                }
            };
            timer.scheduleAtFixedRate(timerTask, 0, 5000);

        }

        public void displayData(String[] data){
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    viewSong.setText(data[1]);
                    viewArtist.setText(data[0]);
                }
            });


        }
    public String[] getData(String link) {
        retriever = new MediaMetadataRetriever();
        retriever.setDataSource(link, new HashMap<>());
        title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        if (title != null) titleSplit = title.split(" - ", 2);

        try {
            retriever.release();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return titleSplit;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        viewSong = findViewById(R.id.song);
        viewArtist = findViewById(R.id.artist);
        clock = findViewById(R.id.textClock);
        btnBgRadio = findViewById(R.id.btnbgradio);
        btn_1rock = findViewById(R.id.btn1rock);

        player = new ExoPlayer.Builder(this).build();

        MediaMetadata mediaMetadata = new MediaMetadata.Builder().build();

        MediaItem mediaItem = new MediaItem.Builder().
                setMediaMetadata(mediaMetadata)
                .setUri(link1rock)
                .build();

        stations.add(mediaItem);

        mediaMetadata = new MediaMetadata.Builder().build();

        mediaItem = new MediaItem.Builder().
                setMediaMetadata(mediaMetadata)
                .setUri(linkBg)
                .build();

        stations.add(mediaItem);

        player.setMediaItems(stations);
        player.prepare();
        player.setPlayWhenReady(true);

        btn_1rock.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                player.seekTo(0, 0);
                currentUrl = link1rock;
                titleSplit = getData(currentUrl);
                displayData(titleSplit);
            }
        });

        btnBgRadio.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                player.seekTo(1, 0);
                currentUrl = linkBg;
                titleSplit = getData(currentUrl);
                displayData(titleSplit);
            }
        });

    }

    @Override
    protected void onDestroy() {
        player.release();
        super.onDestroy();
    }
}



