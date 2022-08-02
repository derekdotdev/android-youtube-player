package com.derekdileo.youtubeplayer;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YouTubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    static final String GOOGLE_API_KEY = "AIzaSyB12BlkiNLAiW51zxY-EWksH0RY9XZ3XjA";
    static final String YOUTUBE_VIDEO_ID = "tpWLeUt_7Cc";
    static final String YOUTUBE_PLAYLIST = "PLx65qkgCWNJIs3FPaj8JZhduXSpQ_ZfvL";
    private static final String TAG = "YouTubeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_youtube);
//        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.activity_youtube);

        // Avoid passing null as the view root (we are inflating the root layout for the activity <the root view>)
        ConstraintLayout layout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_youtube, null);
        setContentView(layout);

//        Button button1 = new Button(this);
//        button1.setLayoutParams(new ConstraintLayout.LayoutParams(300, 80));
//        button1.setText("Button added");
//        layout.addView(button1);
        YouTubePlayerView playerView = new YouTubePlayerView(this);
        playerView.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.addView(playerView);

        playerView.initialize(GOOGLE_API_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        Log.d(TAG, "onInitializationSuccess: provider is " +provider.getClass().toString());
        Toast.makeText(this, "Initialized YouTube Player successfully", Toast.LENGTH_LONG).show();

        youTubePlayer.setPlaybackEventListener(playbackEventListener);
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);

        if(!wasRestored) {
            youTubePlayer.cueVideo(YOUTUBE_VIDEO_ID);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        final int REQUEST_CODE = 1;
        if(youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, REQUEST_CODE).show();
        } else {
            String errorMessage = String.format("There was an error initializing the YouTube Player (%1$s)", youTubeInitializationResult.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {
            Toast.makeText(YouTubeActivity.this, "Good, video is playing ok", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onPaused() {
            Toast.makeText(YouTubeActivity.this, "Good, video is paused", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStopped() {
            Toast.makeText(YouTubeActivity.this, "Video is stopped", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onBuffering(boolean b) {
            Toast.makeText(YouTubeActivity.this, "Video is buffering", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onSeekTo(int i) {

        }
    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {
            Toast.makeText(YouTubeActivity.this, "Video is loading", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {
            Toast.makeText(YouTubeActivity.this, "Click Ad to make video creator rich!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVideoStarted() {
            Toast.makeText(YouTubeActivity.this, "Video has started", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVideoEnded() {
            Toast.makeText(YouTubeActivity.this, "Video has finished", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {
            Toast.makeText(YouTubeActivity.this, "Sorry. There was an error...", Toast.LENGTH_LONG).show();
        }
    };
}