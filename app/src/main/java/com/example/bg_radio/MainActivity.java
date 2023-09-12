package com.example.bg_radio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MediaMetadata;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String linkBg = "http://play.global.audio/bgradio.ogg";
    String link1rock = "http://149.13.0.81/radio1rock.ogg";
    String link = linkBg;
    TextView viewSong;
    PlayerView viewPlayer;
    TextClock clock;
    ExoPlayer player;
    Button btnBgRadio;
    Button btn_1rock;
    List<MediaItem> stations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        viewSong = findViewById(R.id.song);
        viewPlayer = findViewById(R.id.player);
        clock = findViewById(R.id.textClock);
        btnBgRadio = findViewById(R.id.btnbgradio);
        btn_1rock = findViewById(R.id.btn1rock);

        ExoPlayer.Builder builder = new ExoPlayer.Builder(this);
        player = builder.build();
        viewPlayer.setPlayer(player);

        MediaMetadata mediaMetadata = new MediaMetadata.Builder().
                setDisplayTitle("1RockTitle").
                setArtist("1RockArtist").build();

        MediaItem mediaItem = new MediaItem.Builder().
                setMediaMetadata(mediaMetadata)
                .setUri("http://149.13.0.81/radio1rock.ogg")
                .build();

        stations.add(mediaItem);

        mediaMetadata = new MediaMetadata.Builder().
                setDisplayTitle("BRGadioTitle").
                setArtist("BGRadioArtist").build();

        mediaItem = new MediaItem.Builder().
                setMediaMetadata(mediaMetadata)
                .setUri("http://149.13.0.81/bgradio.ogg")
                .build();

        stations.add(mediaItem);

        player.addListener(new Player.Listener() {
                    @Override
                    public void onMediaMetadataChanged(MediaMetadata mediaMetadata) {
                        String title = mediaMetadata.displayTitle.toString();
                        viewSong.setText(title);
                    }
                }
        );
        player.setMediaItems(stations);

        btn_1rock.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaItem item = stations.get(0);
                player.setMediaItem(item);
                player.prepare();
                player.play();
            }
        });

        btnBgRadio.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaItem item = stations.get(1);
                player.setMediaItem(item);
                player.prepare();
                player.play();
            }
        });

    }

    @Override
    protected void onDestroy() {
        player.release();
        super.onDestroy();
    }
}