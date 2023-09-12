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

        btn_1rock.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaMetadata mediaMetadata = new MediaMetadata.Builder()
                        .setDisplayTitle("Radio1Rock title ...")
                        .setArtist("Radio1Rock artist ...")
                        .build();
                link = link1rock;
                MediaItem mediaItem = new MediaItem.Builder()
                        .setMediaMetadata(mediaMetadata)
                        .setUri(link)
                        .build();
                player.setMediaItem(mediaItem);
                player.prepare();
                player.play();
            }
        });

        btnBgRadio.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaMetadata mediaMetadata = new MediaMetadata.Builder()
                        .setDisplayTitle("BGRadio title ...")
                        .setArtist("BGRadio artist ...")
                        .build();
                link = linkBg;
                MediaItem mediaItem = new MediaItem.Builder()
                        .setMediaMetadata(mediaMetadata)
                        .setUri(link)
                        .build();
                player.setMediaItem(mediaItem);
                player.prepare();
                player.play();
            }
        });

        player.addListener(
                new Player.Listener() {
                    @Override
                    public void onMediaMetadataChanged(MediaMetadata mediaMetadata) {
                        String title = mediaMetadata.displayTitle.toString();
                        viewSong.setText(title);
                    }
                }
        );
    }

    @Override
    protected void onDestroy() {
        player.release();
        super.onDestroy();
    }
}