package com.multimedia.controller.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.temp.mediacontroller.R;
import com.multimedia.controller.utils.Constants;
import com.multimedia.controller.utils.Media;

import java.io.File;

public class PlayerActivity extends AppCompatActivity {
    private SimpleExoPlayer player;
    private PlayerView playerView;
    private Context context;
    private Media media;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        context = this;

        if (getIntent() != null && getIntent().hasExtra(Constants.MEDIA_ITEM))
            media = getIntent().getParcelableExtra(Constants.MEDIA_ITEM);

        playerView = findViewById(R.id.player_view);
        progressBar = findViewById(R.id.pb);
    }


    private void initializePlayer() {
        if (player == null){
            player = ExoPlayerFactory.newSimpleInstance(context);

            playerView.setPlayer(player);

            DataSource.Factory dataSourceFactory =  new DefaultDataSourceFactory(context,
                    Util.getUserAgent(context, getString(R.string.app_name)));


            MediaSource mediaSource =  new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.fromFile(new File(media.getMediaPath())));
                    /*new DataSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(media.getUriString()));*/

            player.prepare(mediaSource);
            player.addListener(new PlayerEventListener());
            player.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (player != null)
            player.release();
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        releasePlayer();
        setIntent(intent);
    }


    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
            if (playerView != null) {
                playerView.onResume();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || player == null) {
            initializePlayer();
            if (playerView != null) {
                playerView.onResume();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            if (playerView != null) {
                playerView.onPause();
            }
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            if (playerView != null) {
                playerView.onPause();
            }
            releasePlayer();
        }
    }
    private static boolean isBehindLiveWindow(ExoPlaybackException e) {
        if (e.type != ExoPlaybackException.TYPE_SOURCE) {
            return false;
        }
        Throwable cause = e.getSourceException();
        while (cause != null) {
            if (cause instanceof BehindLiveWindowException) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
    }

    private class PlayerEventListener implements Player.EventListener {

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            if (playbackState == Player.STATE_BUFFERING) {
                progressBar.setVisibility(View.VISIBLE);
            }else if (playbackState == Player.STATE_READY) {
                progressBar.setVisibility(View.GONE);
            }


        }

        @Override
        public void onPlayerError(ExoPlaybackException e) {
            if (isBehindLiveWindow(e)) {
                initializePlayer();
            }
        }
    }
}
